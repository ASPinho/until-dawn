package deltaqueues.dronnie.indicators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static deltaqueues.dronnie.Utilities.VIEWPORT_HEIGHT;
import static deltaqueues.dronnie.Utilities.VIEWPORT_WIDTH;

public class InfectedMessage extends AbstractIndicators{

    private Texture infected;

    public InfectedMessage() {
        quality = "  ***** DANGER *****\n  YOU ARE INFECTED  \n***** GET PAPER *****";
        writeIndicator = new BitmapFont();
        writeIndicator.setColor(Color.RED);
        infected = new Texture(Gdx.files.internal("toxic2.png"));
    }

    public void drawMessage(SpriteBatch batch, OrthographicCamera camera) {
       // writeIndicator.draw(batch, quality, camera.position.x + VIEWPORT_WIDTH / 2 - 700, camera.position.y + VIEWPORT_HEIGHT / 2 - 5);

        batch.draw(infected,camera.position.x - 50, camera.position.y + 275);
    }



}
