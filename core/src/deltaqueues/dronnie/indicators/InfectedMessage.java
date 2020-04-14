package deltaqueues.dronnie.indicators;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static deltaqueues.dronnie.Utilities.VIEWPORT_HEIGHT;
import static deltaqueues.dronnie.Utilities.VIEWPORT_WIDTH;

public class InfectedMessage extends AbstractIndicators{

    public InfectedMessage() {
        quality = "  ***** DANGER *****\n  YOU ARE INFECTED  \n***** GET PAPER *****";
        writeIndicator = new BitmapFont();
        writeIndicator.setColor(Color.RED);
    }

    public void drawMessage(SpriteBatch batch, OrthographicCamera camera) {
        writeIndicator.draw(batch, quality, camera.position.x + VIEWPORT_WIDTH / 2 - 700, camera.position.y + VIEWPORT_HEIGHT / 2 - 20);
    }

}
