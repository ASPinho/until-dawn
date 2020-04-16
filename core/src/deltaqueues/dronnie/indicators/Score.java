package deltaqueues.dronnie.indicators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deltaqueues.dronnie.elements.Dronnie;

import static deltaqueues.dronnie.Utilities.VIEWPORT_HEIGHT;
import static deltaqueues.dronnie.Utilities.VIEWPORT_WIDTH;

public class Score extends AbstractIndicators{

    private Texture covid;
    private Texture enemyDrone;

    private BitmapFont covidScore;
    private BitmapFont dronesScore;

    private int killedCovids = 0;
    private int killedDrones = 0;

    public Score(Dronnie dronnie) {
        this.dronnie = dronnie;
        quality = "SCORE: ";
        quantity = dronnie.getScore();
        writeIndicator = new BitmapFont();
        covidScore = new BitmapFont();
        dronesScore = new BitmapFont();
        writeIndicator.setColor(Color.GREEN);
        covid = new Texture(Gdx.files.internal("covid.png"));
        enemyDrone = new Texture(Gdx.files.internal("negativedrone4.png"));
    }

    public void drawScore(SpriteBatch batch, OrthographicCamera camera) {
        writeIndicator.draw(batch, quality + dronnie.getScore(), camera.position.x - VIEWPORT_WIDTH / 2 + 20, camera.position.y + VIEWPORT_HEIGHT / 2 - 25);
        covidScore.setColor(Color.GREEN);
        covidScore.draw(batch, "" + killedCovids,camera.position.x - 380,camera.position.y + 305 );
        dronesScore.setColor(Color.GREEN);
        dronesScore.draw(batch, "" + killedDrones,camera.position.x - 200, camera.position.y + 305);

        batch.draw(covid,camera.position.x - 450, camera.position.y + 280);
        batch.setColor(Color.YELLOW);
        batch.draw(enemyDrone,camera.position.x - 250, camera.position.y + 285);
        batch.setColor(Color.WHITE);
    }

    public void covidKilled(int i) {
        killedCovids += i;
    }

    public void killedDrones(int i) {
        killedDrones += i;
    }

}
