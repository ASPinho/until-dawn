package deltaqueues.dronnie.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import deltaqueues.dronnie.Directions;
import deltaqueues.dronnie.elements.allys.Dronnie;

public class SpeedBoost extends AbstractElements {

    private Sprite speedBoost;

    private float angle = 0;
    private float compensate = 0;

    public SpeedBoost(Rectangle player) {

        bodyPic = new Texture(Gdx.files.internal("boostSpeed3.png"));

        this.speedBoost = new Sprite(bodyPic);
        this.player = player;


        body = new Rectangle();
        body.x = 20;
        body.y = 20;
    }

    public void rotate(SpriteBatch batch, Dronnie dronnie) {


        angle = dronnie.getX() - dronnie.getPreviousX();

        if (dronnie.getX() > dronnie.getPreviousX()) {
            compensate = -30;
        } else if (dronnie.getX() < dronnie.getPreviousX()) {
            compensate = 30;
        } else {
            compensate = 0;
        }

        if (dronnie.getDirection() == Directions.RIGHT) {
            batch.draw(speedBoost, dronnie.getX() - 60, dronnie.getY() - 20, speedBoost.getRegionWidth() / 2, speedBoost.getRegionHeight() / 2, speedBoost.getRegionWidth(), speedBoost.getRegionHeight(), 1f, 1f, 150, false);
        }

        if (dronnie.getDirection() == Directions.LEFT) {
            batch.draw(speedBoost, dronnie.getX() + 40, dronnie.getY() - 20, speedBoost.getRegionWidth() / 2, speedBoost.getRegionHeight() / 2, speedBoost.getRegionWidth(), speedBoost.getRegionHeight(), 1f, 1f, 325, false);
        }


        if (dronnie.getDirection() == Directions.UP) {
            batch.draw(speedBoost, dronnie.getX() - 10 + compensate, dronnie.getY() - 60, speedBoost.getRegionWidth() / 2, speedBoost.getRegionHeight() / 2, speedBoost.getRegionWidth(), speedBoost.getRegionHeight(), 1f, 1f, 240 - angle, false);
        }

        if (dronnie.getDirection() == Directions.DOWN) {
            batch.draw(speedBoost, dronnie.getX() - 10 + compensate, dronnie.getY() + 30, speedBoost.getRegionWidth() / 2, speedBoost.getRegionHeight() / 2, speedBoost.getRegionWidth(), speedBoost.getRegionHeight(), 1f, 1f, 60 + angle, false);
        }
    }


}
