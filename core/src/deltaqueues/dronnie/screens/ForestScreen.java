package deltaqueues.dronnie.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import deltaqueues.dronnie.backgrounds.Forest;
import deltaqueues.dronnie.backgrounds.Street;
import deltaqueues.dronnie.streetGame.Player;
import deltaqueues.dronnie.streetGame.characters.Character1;
import deltaqueues.dronnie.streetGame.characters.Character2;

import static deltaqueues.dronnie.Utilities.*;

public class ForestScreen implements Screen {

    private Screen overWorld;

    private Stage stage;
    private Game game;
    private OrthographicCamera camera;

    private boolean returnToCity = false;

    private Player player;
    private Character1 character1;
    private Character2 character2;

    private SpriteBatch batch;
    private Forest parallaxBackground;

    private Skin skin;

    public ForestScreen(Game game, Skin skin, Screen overWorld) {
        this.overWorld = overWorld;
        this.game = game;
        this.skin = skin;

        stage = new Stage(new ScreenViewport());
        camera = (OrthographicCamera) stage.getViewport().getCamera();

        Array<Texture> textures = new Array<Texture>();
        for (int i = 10; i >= 0; i--) {
            if(i > 9) {
                textures.add(new Texture(Gdx.files.internal("parallax/forest/Layer_00" + i + ".png")));
            } else {
                textures.add(new Texture(Gdx.files.internal("parallax/forest/Layer_000" + i + ".png")));
            }
            textures.get(textures.size - 1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }

        parallaxBackground = new Forest(textures);
        parallaxBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(parallaxBackground);



        player = new Player();
        player.setPosition(0, 0);
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        camera = (OrthographicCamera) stage.getViewport().getCamera();

        stage.act();
        stage.draw();

        batch.begin();

        overWorld();

        movement();

        //enterHome(skin);

        batch.end();
        player.setPreviousX(player.getX());

        player.move(PRE_GAME_BACKGROUND_WIDTH, PRE_GAME_BACKGROUND_HEIGHT);

        player.getSprite().setPosition(player.getX(), player.getY());
        moveCamera();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void moveCamera() {

        if (player.getX() > player.getPreviousX()) {
            parallaxBackground.setSpeed(1);
        }
        if (player.getX() == player.getPreviousX()) {
            parallaxBackground.setSpeed(0);
        }
        if (player.getX() < player.getPreviousX()) {
            parallaxBackground.setSpeed(-1);
        }
    }

    public void movement() {
        if (player.getX() < player.getPreviousX()) {
            player.walk();
            player.flipPlayer();
            player.getSprite().draw(batch);
        } else if (player.getX() > player.getPreviousX()) {
            player.walk();
            player.unFlipPlayer();
            player.getSprite().draw(batch);
        } else {
            player.idle();
            player.getSprite().draw(batch);
        }
        if(returnToCity && player.getX() > PRE_GAME_BACKGROUND_WIDTH - PICTURE_SIZE) {
            player.setPosition(PRE_GAME_BACKGROUND_WIDTH - PICTURE_SIZE, player.getY());
        } else if(player.getX() < PRE_GAME_BACKGROUND_WIDTH -PICTURE_SIZE){
            returnToCity = false;
        }
    }

    public void overWorld() {
        if(returnToCity){
            return;
        }
        if(player.getX() >= PRE_GAME_BACKGROUND_WIDTH - PICTURE_SIZE) {
            game.setScreen(overWorld);
            returnToCity = true;
        }
    }
}
