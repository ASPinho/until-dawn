package academia.tilldawn.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameOverScreen implements Screen {
    private Stage stage;
    private Game game;
    private Texture lose;
    private SpriteBatch batch;
    private Music corona;

    public GameOverScreen(final Game game, Skin skin) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        corona = Gdx.audio.newMusic(Gdx.files.internal("coronavirus.mp3"));
        corona.play();
/*
        Label title = new Label("GAME OVER", skin);
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight() * 2 / 3);
        title.setWidth(Gdx.graphics.getWidth());
        title.setFontScale(1.5f);
        stage.addActor(title);
*/
        lose = new Texture(Gdx.files.internal("lost-screen.jpg"));
        batch = new SpriteBatch();

        TextButton playButton = new TextButton("Try again", skin);
        playButton.setWidth(Gdx.graphics.getWidth()/3);
        playButton.setPosition((Gdx.graphics.getWidth()/2-playButton.getWidth()/2) ,(Gdx.graphics.getHeight()/5-playButton.getHeight()/5));

        playButton.addListener(new InputListener() {

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
            }

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        stage.addActor(playButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(lose, 0, 0);
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
        corona.dispose();
    }
}
