package academia.tilldawn.projectiles;

import academia.tilldawn.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class EvilProjectile extends AbstractProjectiles{

    private Rectangle bullet;
    private Texture bulletPic;
    private long lastShootTime;

    private Directions horizontally;
    private Directions vertically;

    private float aimX;
    private float aimY;


    public EvilProjectile(Boss boss, Rectangle player) {
        bullet = new Rectangle();
        bullet.x = boss.getRectangle().x - Utilities.PICTURE_SIZE*2;
        bullet.y = boss.getRectangle().y - Utilities.PICTURE_SIZE*2;
        bullet.width = Utilities.PICTURE_SIZE;
        bullet.height = Utilities.PICTURE_SIZE;
        aimX = player.x;
        aimY = player.y;
        bulletPic = new Texture(Gdx.files.internal("shot2.png"));

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
                bullet.x  -= Utilities.PROJECTILE_SPEED * Gdx.graphics.getDeltaTime();
                break;

            case RIGHT:
                bullet.x += Utilities.PROJECTILE_SPEED * Gdx.graphics.getDeltaTime();
                break;
        }

        switch (vertically){
            case DOWN:
                bullet.y -= Utilities.PROJECTILE_SPEED * Gdx.graphics.getDeltaTime();
                break;
            case UP:
                bullet.y += Utilities.PROJECTILE_SPEED * Gdx.graphics.getDeltaTime();
        }
    }

    private Directions checkHorizontalDirection(){

        int distance = (int) (bullet.x - aimX);

        if (distance == 0){
            return Directions.STAY;
        }

        return distance > 0 ? Directions.LEFT : Directions.RIGHT;
    }

    private Directions checkVerticalDirection(){



        int distance = (int) (bullet.y - aimY);

        if (distance == 0) {
            return Directions.STAY;
        }

        return distance > 0 ? Directions.DOWN : Directions.UP;
    }


    public boolean collision(){
        return (bullet.x == aimX) && (bullet.y == aimY);
    }

    public Texture getTexture(){
        return bulletPic;
    }

    public void dispose(){
        bulletPic.dispose();
    }

    public float getX(){
        return bullet.x;
    }

    public float getY(){
        return bullet.y;
    }

    public Rectangle getRectangle() {
        return bullet;
    }

}
