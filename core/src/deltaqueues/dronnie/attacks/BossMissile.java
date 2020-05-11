package deltaqueues.dronnie.attacks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import deltaqueues.dronnie.Utilities;
import deltaqueues.dronnie.elements.enemies.FinalBoss2;

import static deltaqueues.dronnie.Utilities.PICTURE_SIZE;

public class BossMissile extends AbstractProjectiles {

    private FireType fireType;
    private float speed = 3.5f;
    private int health = 100;

    private Sprite missile;

    private boolean destroyed = false;

    private float aimX;
    private float aimY;
    private float dx;
    private float dy;
    private float len;
    private float angle;

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public BossMissile(FinalBoss2 boss, Rectangle player) {

        fireType = FireType.ENEMY_FIRE;

        this.player = player;

        body = new Rectangle();
        body.x = boss.getBody().x;
        body.y = boss.getBody().y;
        body.width = 20;
        body.height = 20;
        aimX = player.x;
        aimY = player.y;

        bodyPic = new Texture(Gdx.files.internal("missile2.png"));
        missile = new Sprite(bodyPic);
    }

    public void chase(SpriteBatch batch) {
        // moveTowardsPlayer();
        move();
        rotate(batch);
    }

    public void rotate(SpriteBatch batch) {

        missile.setPosition(body.x, body.y);

        float xInput = player.getX();
        float yInput = player.getY();

        angle = MathUtils.radiansToDegrees * MathUtils.atan2(yInput - body.y, xInput - body.x);

        if (angle < 0) {
            angle += 360;
        }
        missile.setRotation(angle - 90);

        missile.draw(batch);
    }

    public void move() {
        aimX = player.getX();
        aimY = player.getY();

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

            //setIsLosted(true);
            return;
        }

        body.x += dx / len * speed;
        body.y += dy / len * speed;

    }

    public void direction2() {
        dx = aimX - body.x;
        dy = aimY - body.y;

        len = (float) Math.sqrt(dx * dx + dy * dy);

        if (Math.round(aimX + aimY) + 20 <= Math.round(body.x + body.y)) {

            //setIsLosted(true);
            return;
        }

        body.x += dx / len * speed;
        body.y += dy / len * speed;
    }

    public void takeDamage(int i) {
        if(health - i <= 0) {
            destroyed = true;
            health = 0;
            return;
        }
        health -= i;
    }

    public float getAngle() {
        return angle;
    }

    public int getHp() {
        return health;
    }

}
