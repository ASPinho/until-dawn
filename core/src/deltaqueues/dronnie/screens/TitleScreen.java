package deltaqueues.dronnie.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TitleScreen implements Screen {

    private Stage stage;
    private Game game;

    private Texture picture;
    private SpriteBatch batch;

    public TitleScreen(final Game game, final Skin skin) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        picture = new Texture(Gdx.files.internal("load-screen-end.jpg"));
        batch = new SpriteBatch();

        TextButton instructionButton = new TextButton("Instructions", skin);
        instructionButton.setWidth(Gdx.graphics.getWidth() / 3);
        instructionButton.setPosition(Gdx.graphics.getWidth() / 2 - instructionButton.getWidth() / 2, Gdx.graphics.getHeight() / 3 - instructionButton.getHeight() / 2);


        TextButton playButton = new TextButton("Play!", skin);
        playButton.setWidth(Gdx.graphics.getWidth() / 3);
        playButton.setPosition(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2, Gdx.graphics.getHeight() / 12 - playButton.getHeight() / 12);

        instructionButton.addListener(new InputListener() {

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new Test(game));
               // dispose();
            }

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        playButton.addListener(new InputListener() {

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new FinalBattleScreen(game));
                dispose();
            }

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        stage.addActor(instructionButton);
        stage.addActor(playButton);


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
       // batch.draw(picture, 0, 0);
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
        stage.dispose();
        picture.dispose();
        batch.dispose();

    }
}
