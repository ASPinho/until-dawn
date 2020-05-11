package deltaqueues.dronnie.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static deltaqueues.dronnie.Utilities.VIEWPORT_HEIGHT;
import static deltaqueues.dronnie.Utilities.VIEWPORT_WIDTH;

public class OverWorld extends AbsctractScreen {

    private Stage stage;
    private Game game;
    private Skin skin;

    private Screen streetScreen;
    private Screen forestScreen;
    private Screen mountainScreen;
    private Screen hillScreen;
    private Sprite sprite;

    private SpriteBatch batch;
    private TmxMapLoader mapLoader;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private int index = 1;


    public OverWorld(Game game, Screen firstLevel, Skin skin) {

        this.game = game;
        this.streetScreen = firstLevel;

        stage = new Stage(new ScreenViewport());

        sprite = new Sprite(new Texture(Gdx.files.internal("player/rpgStyle/down.png")));

        batch = new SpriteBatch();

        camera = new OrthographicCamera();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/overWorldMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        renderer.setView(camera);
        renderer.render();

        batch.begin();

        System.out.println(index);

        handleInput();

        chosingScreen();

        finalChoice();

        camera = (OrthographicCamera) stage.getViewport().getCamera();

        batch.end();
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
        renderer.dispose();
    }

    public int chosingScreen() {

        //sprite.setSize(100,100);

        switch (index) {
            case 1:
                sprite.setPosition(350, 275);
                sprite.draw(batch);
                System.out.println(streetScreen);
                return 1;
            case 2:
                sprite.setPosition(250, 375);
                sprite.draw(batch);
                return 2;
            case 3:
                sprite.setPosition(150, 475);
                sprite.draw(batch);
                return 3;
            case 4:
                sprite.setPosition(50, 575);
                sprite.draw(batch);
                return 4;
        }
        return -1;
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            index--;
            if (index < 1) {
                index = 4;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            index++;
            if (index > 4) {
                index = 1;
            }
        }
    }

    public void finalChoice() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            switch (chosingScreen()) {
                case 1:
                    setIndex(1);
                    game.setScreen(streetScreen);
                    break;
                case 2:
                    setIndex(2);
                    if(forestScreen == null) {
                        forestScreen = new ForestScreen(game, skin, this);
                    }
                    game.setScreen(forestScreen);
                    break;
                case 3:
                    setIndex(3);
                    if(mountainScreen == null) {
                        mountainScreen = new MountainScreen(game, skin, this);
                    }
                    game.setScreen(mountainScreen);
                    break;
                case 4:
                    setIndex(4);
                    if(hillScreen == null) {
                        hillScreen = new HillScreen(game,skin,this);
                    }
                    game.setScreen(hillScreen);
                    break;
            }
        }
    }

    public int getIndex() {
        return index;
    }
}
