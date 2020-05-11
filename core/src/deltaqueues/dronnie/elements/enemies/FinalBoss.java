package deltaqueues.dronnie.elements.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import static deltaqueues.dronnie.Utilities.DRONE_BACKGROUND_HEIGHT;
import static deltaqueues.dronnie.Utilities.PICTURE_SIZE;

public class FinalBoss extends AbstractEnemy {

    private int hp = 200;

    public FinalBoss(Rectangle player){
        body = new Rectangle();
        body.x = PICTURE_SIZE * 10; //Utilities.DRONE_BACKGROUND_WIDTH - 800;
        body.y = DRONE_BACKGROUND_HEIGHT / 2 - PICTURE_SIZE / 2; //Utilities.DRONE_BACKGROUND_HEIGHT - 800;
        body.width = 200;
        body.height = 200;
        this.player = player;

        bodyPic = new Texture(Gdx.files.internal("unnamed.png"));
    }

    public void takeDamage(){
        hp -= 15;
    }

    public boolean isDead(){
        return hp <= 0;
    }
}
