package deltaqueues.dronnie.attacks;

import deltaqueues.dronnie.Directions;
import deltaqueues.dronnie.elements.enemies.Boss;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class SimpleShot extends AbstractProjectiles {

    private FireType fireType;

    private boolean losted = false;

    private float aimX;
    private float aimY;
    private float dx;
    private float dy;
    private float len;

    public SimpleShot(Boss boss, Rectangle player) {

        fireType = FireType.ENEMY_FIRE;

        this.player = player;

        body = new Rectangle();
        body.x = boss.getBody().x;
        body.y = boss.getBody().y;
        body.width = 20;
        body.height = 20;
        aimX = player.x;
        aimY = player.y;

        bodyPic = new Texture(Gdx.files.internal("tirinhos-02.png"));


    }

    public SimpleShot(Rectangle dst, float x, float y) {

        fireType = FireType.PLAYER_FIRE;

        body = new Rectangle();
        body.x = dst.x;
        body.y = dst.y;
        body.width = 20;
        body.height = 20;
        aimX = x;
        aimY = y;

        bodyPic = new Texture(Gdx.files.internal("tirinhos-02.png"));
    }

    public void move() {
        if (Math.round(aimX+aimY) > Math.round(body.x+body.y)) {
            direction2();
            return;
        }
        direction1();
    }

    public void direction1() {
        dx = aimX - body.x;
        dy = aimY - body.y;

        /*float*/ len = (float) Math.sqrt(dx * dx + dy * dy);

        if ((Math.round(body.x + body.y)) == Math.round(aimX + aimY)) {

            setIsLosted(true);
            return;
        }

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

        /*float*/ len = (float) Math.sqrt(dx * dx + dy * dy);


        if ((Math.round(body.x + body.y)) == Math.round(aimX + aimY)) {

            body.x = -1;
            body.y = -1;
        }

        if (Math.round(aimX + aimY) + 20 <= Math.round(body.x + body.y)) {

            body.x = -1;
            body.y = -1;
            setIsLosted(true);
            return;
        }

        body.x += dx / len * 20;
        body.y += dy / len * 20;
    }

    public FireType getFireType() {
        return fireType;
    }

    public boolean isLosted() {
        return losted;
    }

    public void setIsLosted(boolean lost) {
        losted = lost;
    }
}
