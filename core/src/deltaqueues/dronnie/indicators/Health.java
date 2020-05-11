package deltaqueues.dronnie.indicators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deltaqueues.dronnie.elements.allys.Dronnie;

import static deltaqueues.dronnie.Utilities.VIEWPORT_HEIGHT;
import static deltaqueues.dronnie.Utilities.VIEWPORT_WIDTH;

public class Health extends AbstractIndicators{

    private Texture droneHp;
    private Texture batteyLvl;


    public Health(Dronnie dronnie) {
        this.dronnie = dronnie;
        quality = "HP ";
        quantity = dronnie.getHp();
        writeIndicator = new BitmapFont();
        writeIndicator.setColor(Color.TEAL);
        droneHp = new Texture(Gdx.files.internal("droneNegativ4.png"));
        batteyLvl = new Texture(Gdx.files.internal("negativeBattery.png"));
    }

    public void drawHealth(SpriteBatch batch, OrthographicCamera camera) {

       // writeIndicator.draw(batch, quality + dronnie.getHp(), camera.position.x + VIEWPORT_WIDTH / 2 - 185, camera.position.y + VIEWPORT_HEIGHT / 2 - 27);
        batch.setColor(Color.SALMON);
        batch.draw(droneHp,camera.position.x + VIEWPORT_WIDTH / 2 - 198, camera.position.y + VIEWPORT_HEIGHT / 2 - 49);
        batch.setColor(Color.WHITE);
        //batch.draw(energyBorder, camera.position.x+225, camera.position.y+205);
        batch.draw(batteyLvl, camera.position.x+210, camera.position.y+VIEWPORT_HEIGHT/2 -49);

    }


}
