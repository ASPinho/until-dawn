package deltaqueues.dronnie.attacks;

import deltaqueues.dronnie.Directions;
import deltaqueues.dronnie.Utilities;
import deltaqueues.dronnie.elements.enemies.Boss;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class SimpleShot extends AbstractProjectiles{

    private long lastShootTime;

    private Directions horizontally;
    private Directions vertically;

    private float aimX;
    private float aimY;


    public SimpleShot(Boss boss, Rectangle player) {

        this.player = player;

        body = new Rectangle();
        body.x = boss.getBody().x - Utilities.PICTURE_SIZE*2;
        body.y = boss.getBody().y - Utilities.PICTURE_SIZE*2;
        body.width = 20;
        body.height = 20;
        aimX = player.x;
        aimY = player.y;

        bodyPic = new Texture(Gdx.files.internal("tirinhos-02.png"));

        horizontally = checkHorizontalDirection();
        vertically = checkVerticalDirection();

        lastShootTime = TimeUtils.nanoTime();
    }

    public long getLastShootTime(){
        return lastShootTime;
    }

    public void move() {

        switch (horizontally){
            case LEFT:
                body.x  -= Utilities.PROJECTILE_SPEED * Gdx.graphics.getDeltaTime();
                break;

            case RIGHT:
                body.x += Utilities.PROJECTILE_SPEED * Gdx.graphics.getDeltaTime();
                break;
        }

        switch (vertically){
            case DOWN:
                body.y -= Utilities.PROJECTILE_SPEED * Gdx.graphics.getDeltaTime();
                break;
            case UP:
                body.y += Utilities.PROJECTILE_SPEED * Gdx.graphics.getDeltaTime();
        }
    }

    private Directions checkHorizontalDirection(){

        int distance = (int) (body.x - aimX);

        if (distance == 0){
            return Directions.STAY;
        }

        return distance > 0 ? Directions.LEFT : Directions.RIGHT;
    }

    private Directions checkVerticalDirection(){



        int distance = (int) (body.y - aimY);

        if (distance == 0) {
            return Directions.STAY;
        }

        return distance > 0 ? Directions.DOWN : Directions.UP;
    }

}
