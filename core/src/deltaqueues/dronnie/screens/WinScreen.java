package deltaqueues.dronnie.screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class WinScreen implements Screen {
    private Stage stage;
    private Game game;
    private Texture winPic;
    private SpriteBatch batch;


    public WinScreen(final Game game, final Skin skin) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        /*
        //comment if background has text
        Label title = new Label("VICTORY", skin);
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight()*2/3);
        title.setWidth(Gdx.graphics.getWidth());
        title.setFontScale(1.5f);
        stage.addActor(title);
        //end*/


        winPic = new Texture(Gdx.files.internal("win-screen.png"));
        batch = new SpriteBatch();


        TextButton playButton = new TextButton("Restart", skin);
        playButton.setWidth(Gdx.graphics.getWidth()/3);
        playButton.setPosition((Gdx.graphics.getWidth()/2-playButton.getWidth()/2) ,(Gdx.graphics.getHeight()/4-playButton.getHeight()/4)-50);

        playButton.addListener(new InputListener() {

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new FinalBattleScreen(game));
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
        batch.draw(winPic, 0, 0);
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
        batch.dispose();
    }
}
