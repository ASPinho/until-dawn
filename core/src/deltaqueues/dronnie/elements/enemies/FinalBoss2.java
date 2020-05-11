package deltaqueues.dronnie.elements.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import deltaqueues.dronnie.elements.allys.Dronnie;

import static deltaqueues.dronnie.Utilities.DRONE_BACKGROUND_HEIGHT;
import static deltaqueues.dronnie.Utilities.PICTURE_SIZE;

public class FinalBoss2 extends AbstractEnemy {

    private int hp = 143;
    private int velocity;
    private boolean alive = true;
    private long currentTime;
    private long timeToMissile;
    private boolean missileDestroyed = true;
    private boolean missileLaunched = false;

    public boolean isMissileLaunched() {
        return missileLaunched;
    }

    public void setMissileLaunched(boolean missileLaunched) {
        this.missileLaunched = missileLaunched;
    }

    private Dronnie dronnie;


    private Sprite boss;
    private float dx;
    private float dy;
    private float len;

    public FinalBoss2(Rectangle player, Dronnie dronnie){

        body = new Rectangle(PICTURE_SIZE * 10, DRONE_BACKGROUND_HEIGHT /2 - PICTURE_SIZE/2, 200, 200);
        body.x = PICTURE_SIZE * 10; //Utilities.DRONE_BACKGROUND_WIDTH - 800;
        body.y = DRONE_BACKGROUND_HEIGHT / 2 - PICTURE_SIZE / 2; //Utilities.DRONE_BACKGROUND_HEIGHT - 800;
        body.width = 200;
        body.height = 200;
        this.player = player;
        this.dronnie = dronnie;

        bodyPic = new Texture(Gdx.files.internal("unnamed.png"));
        boss = new Sprite(bodyPic);

        boss.setPosition(body.x, body.y);
    }

    public void takeDamage(){
        hp -= 15;
    }

    public boolean isDead(){
        return hp <= 0;
    }

    public void speed() {
        dx = player.getX() - boss.getX();
        dy = player.getY() - boss.getY();

        len = (float) Math.sqrt(dx * dx + dy * dy);

        if(hp > 130) {
            velocity =1;
        } else if(hp > 115) {
            velocity =2;
        } else if(hp > 100) {
            velocity =3;
        } else if(hp > 80) {
            velocity =4;
        }
        boss.translateX( dx / len * velocity);
        boss.translateY( dy / len * velocity);

        body.x = boss.getX();
        body.y = boss.getY();

    }

    public Sprite getSprite() {
        return boss;
    }

    public int getHp() {
        return hp;
    }

    public void takeDamage(int i) {
        if((hp - i) <= 0 ){
            hp = 0;
            return;
        }
        hp -= i;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean checkTime() {

        if(!missileDestroyed){
            return false;
        }
        if(TimeUtils.timeSinceMillis(timeToMissile) > 50000) {

            timeToMissile = TimeUtils.millis();
            setMissileDestroyed(false);
            return false;
        }
        if(TimeUtils.timeSinceMillis(currentTime) > 100) {
            currentTime = TimeUtils.millis();
            return true;
        }
        return false;
    }

    public boolean isMissileDestroyed() {
        return missileDestroyed;
    }

    public void setMissileDestroyed(boolean missileDestroyed) {
        this.missileDestroyed = missileDestroyed;
    }
}
