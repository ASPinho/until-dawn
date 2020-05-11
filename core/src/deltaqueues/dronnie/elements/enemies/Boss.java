package deltaqueues.dronnie.elements.enemies;

import deltaqueues.dronnie.Utilities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import deltaqueues.dronnie.elements.allys.Dronnie;

public class Boss extends AbstractEnemy {

    private long lastShootTime = 0;
    private int firstMoves = 30;
    private int turning = 4;
    private int moves = 0;
    private int speed = 3;
    private Dronnie dronnie;


    public Boss(Rectangle player, Dronnie dronnie) {

        body = new Rectangle();
        body.x = MathUtils.random(Utilities.DRONE_BACKGROUND_WIDTH /2);
        body.y = MathUtils.random( Utilities.DRONE_BACKGROUND_HEIGHT);
        body.width = Utilities.PICTURE_SIZE;
        body.height = Utilities.PICTURE_SIZE;

        bodyPic = new Texture(Gdx.files.internal("boris.png"));

        this.dronnie = dronnie;
        this.player = player;
    }

    public void dispose(){
        bodyPic.dispose();
    }

    public void shoot(){
        lastShootTime = TimeUtils.nanoTime();
    }

    public long getLastShootTime(){
        return lastShootTime;
    }
}
