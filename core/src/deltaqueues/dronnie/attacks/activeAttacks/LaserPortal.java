package deltaqueues.dronnie.attacks.activeAttacks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import deltaqueues.dronnie.attacks.AbstractProjectiles;

public class LaserPortal extends AbstractProjectiles {

    private boolean losted = false;

    private float aimX;
    private float aimY;
    private float dx;
    private float dy;
    private float len;
    private Sprite laser;

    public LaserPortal(Rectangle dst, float x, float y) {


        body = new Rectangle();
        body.x = dst.x;
        body.y = dst.y;
        body.width = 20;
        body.height = 20;
        aimX = x;
        aimY = y;

        bodyPic = new Texture(Gdx.files.internal("laser2.png"));
        laser = new Sprite(bodyPic);
    }

    public void fire(SpriteBatch batch) {
        move();
        rotate(batch);
    }




    public void rotate(SpriteBatch batch) {

        laser.setPosition(body.x, body.y);

        float xInput = aimX;
        float yInput = aimY;

        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(yInput - body.y, xInput - body.x);

        if (angle < 0) {
            angle += 360;
        }
        laser.setRotation(angle);

        laser.draw(batch);
    }




    public void move() {
        if (Math.round(aimX + aimY) > Math.round(body.x + body.y)) {
            direction2();
            return;
        }
        direction1();
    }

    public void direction1() {
        dx = aimX - body.x;
        dy = aimY - body.y;

        len = (float) Math.sqrt(dx * dx + dy * dy);

        if (Math.round(aimX + aimY) + 20 >= Math.round(body.x + body.y)) {

            setIsLosted(true);
            return;
        }

        body.x += dx / len * 10;
        body.y += dy / len * 10;

    }

    public void direction2() {
        dx = aimX - body.x;
        dy = aimY - body.y;

        len = (float) Math.sqrt(dx * dx + dy * dy);

        if (Math.round(aimX + aimY) + 20 <= Math.round(body.x + body.y)) {

            setIsLosted(true);
            return;
        }

        body.x += dx / len * 10;
        body.y += dy / len * 10;
    }

    public boolean isLosted() {
        return losted;
    }

    public void setIsLosted(boolean lost) {
        losted = lost;
    }

}
