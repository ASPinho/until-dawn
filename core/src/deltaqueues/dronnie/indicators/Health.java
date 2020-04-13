package deltaqueues.dronnie.indicators;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deltaqueues.dronnie.elements.Dronnie;

import static deltaqueues.dronnie.Utilities.VIEWPORT_HEIGHT;
import static deltaqueues.dronnie.Utilities.VIEWPORT_WIDTH;

public class Health extends AbstractIndicators{

    public Health(Dronnie dronnie) {
        this.dronnie = dronnie;
        quality = "HEALTH: ";
        quantity = dronnie.getHp();
        writeIndicator = new BitmapFont();
        writeIndicator.setColor(Color.GREEN);
    }

    public void drawHealth(SpriteBatch batch, OrthographicCamera camera) {
        writeIndicator.draw(batch, quality + dronnie.getHp(), camera.position.x + VIEWPORT_WIDTH / 2 - 150, camera.position.y + VIEWPORT_HEIGHT / 2 - 20);
    }

}
