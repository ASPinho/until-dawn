package deltaqueues.dronnie.elements.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import deltaqueues.dronnie.elements.Dronnie;

import static deltaqueues.dronnie.Utilities.BACKGROUND_HEIGHT;
import static deltaqueues.dronnie.Utilities.PICTURE_SIZE;

public class FinalBoss2 extends AbstractEnemy {

    private int hp = 143;
    private int velocity;
    private boolean distroyed;
    private int moves = 0;
    private Dronnie dronnie;
    private int turning = 4;
    private int firstMoves = 0;
    private int speed = 3;


    private Sprite boss;
    private float dx;
    private float dy;
    private float len;

    public FinalBoss2(Rectangle player, Dronnie dronnie){

        body = new Rectangle(PICTURE_SIZE * 10, BACKGROUND_HEIGHT/2 - PICTURE_SIZE/2, 200, 200);
        body.x = PICTURE_SIZE * 10; //Utilities.BACKGROUND_WIDTH - 800;
        body.y = BACKGROUND_HEIGHT / 2 - PICTURE_SIZE / 2; //Utilities.BACKGROUND_HEIGHT - 800;
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
        if((hp - i) < 0 ){
            distroyed = true;
            return;
        }
        hp -= i;
    }



}
