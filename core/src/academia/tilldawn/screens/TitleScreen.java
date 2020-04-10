package academia.tilldawn.screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TitleScreen implements Screen {

    private Stage stage;
    private Game game;

    private Texture picture;
    private SpriteBatch batch;

    public TitleScreen(final Game game, final Skin skin) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        picture = new Texture(Gdx.files.internal("load-screen-final.jpg"));
/*
        Label instructions = new Label("The blue arrow indicates the way.", skin);
        instructions.setAlignment(Align.center);
        instructions.setY((Gdx.graphics.getHeight()*2/3) -250);
        instructions.setWidth(Gdx.graphics.getWidth());
        instructions.setFontScale(1.5f);
        stage.addActor(instructions);*/

        TextButton playButton = new TextButton("Play!", skin);
        playButton.setWidth(Gdx.graphics.getWidth()/4);
        playButton.setPosition(Gdx.graphics.getWidth()/2-playButton.getWidth()/2,Gdx.graphics.getHeight()/5-playButton.getHeight()/5);

        playButton.addListener(new InputListener() {

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                dispose();
            }

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        stage.addActor(playButton);

//        TextButton optionsButton = new TextButton("Options",skin);
//        optionsButton.setWidth(Gdx.graphics.getWidth()/2);
//        optionsButton.setPosition(Gdx.graphics.getWidth()/2-optionsButton.getWidth()/2,Gdx.graphics.getHeight()/4-optionsButton.getHeight()/2);
//        optionsButton.addListener(new InputListener(){
//            @Override
//            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//                game.setScreen(new WinScreen(game, skin));
//            }
//            @Override
//            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//                return true;
//            }
//        });
//        stage.addActor(optionsButton);

        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(picture, 0, 0);
        batch.end();
        stage.act();
        stage.draw();
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
        picture.dispose();
        batch.dispose();

    }
}
