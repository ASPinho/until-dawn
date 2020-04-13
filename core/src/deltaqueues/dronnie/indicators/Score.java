package deltaqueues.dronnie.indicators;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deltaqueues.dronnie.elements.Dronnie;

import static deltaqueues.dronnie.Utilities.VIEWPORT_HEIGHT;
import static deltaqueues.dronnie.Utilities.VIEWPORT_WIDTH;

public class Score extends AbstractIndicators{

    public Score(Dronnie dronnie) {
        this.dronnie = dronnie;
        quality = "SCORE: ";
        quantity = dronnie.getScore();
        writeIndicator = new BitmapFont();
        writeIndicator.setColor(Color.GREEN);
    }

    public void drawScore(SpriteBatch batch, OrthographicCamera camera) {
        writeIndicator.draw(batch, quality + dronnie.getScore(), camera.position.x - VIEWPORT_WIDTH / 2 + 20, camera.position.y + VIEWPORT_HEIGHT / 2 - 20);
    }

}
