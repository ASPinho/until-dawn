package deltaqueues.dronnie.elements.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import deltaqueues.dronnie.Utilities;
import deltaqueues.dronnie.elements.AbstractElements;

public class FinalBoss extends AbstractEnemy {

    private int hp = 200;

    public FinalBoss(Rectangle player){
        body = new Rectangle();
        body.x = Utilities.BACKGROUND_WIDTH - 800;
        body.y = Utilities.BACKGROUND_HEIGHT - 800;
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
