package academia.tilldawn.screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TitleScreen implements Screen {

    private Stage stage;
    private Game game;

    public TitleScreen(final Game game, final Skin skin) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        Label title = new Label("Until Dawn", skin);
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight()*2/3);
        title.setWidth(Gdx.graphics.getWidth());
        title.setFontScale(2.0f);
        stage.addActor(title);

        Label instructions = new Label("The blue arrow indicates the way.", skin);
        instructions.setAlignment(Align.center);
        instructions.setY((Gdx.graphics.getHeight()*2/3) -250);
        instructions.setWidth(Gdx.graphics.getWidth());
        instructions.setFontScale(1.5f);
        stage.addActor(instructions);

        Label instructions2 = new Label("Use Arrow Keys or WASD to move", skin);
        instructions2.setAlignment(Align.center);
        instructions2.setY((Gdx.graphics.getHeight()*2/3) -350);
        instructions2.setWidth(Gdx.graphics.getWidth());
        instructions2.setFontScale(1.5f);
        stage.addActor(instructions2);

        TextButton playButton = new TextButton("Play!", skin);
        playButton.setWidth(Gdx.graphics.getWidth()/2);
        playButton.setPosition(Gdx.graphics.getWidth()/2-playButton.getWidth()/2,Gdx.graphics.getHeight()/2-playButton.getHeight()/2);

        playButton.addListener(new InputListener() {

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
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

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

    }
}
