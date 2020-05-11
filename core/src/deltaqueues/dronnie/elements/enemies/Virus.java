package deltaqueues.dronnie.elements.enemies;

import deltaqueues.dronnie.Utilities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import deltaqueues.dronnie.elements.allys.Dronnie;

public class Virus extends AbstractEnemy {

    private int turning = 4;
    private int firstMoves = 30;
    private int moves = 0;
    private int speed = 1;
    private Dronnie dronnie;

    public Virus(Rectangle player, Dronnie dronnie) {

        this.player = player;
        this.bodyPic = new Texture(Gdx.files.internal("virus-32.png"));

        this.dronnie = dronnie;
        this.body = new Rectangle();
        body.x = MathUtils.random(Utilities.DRONE_BACKGROUND_WIDTH /2);
        body.y = MathUtils.random( Utilities.DRONE_BACKGROUND_HEIGHT);
        body.width = Utilities.PICTURE_SIZE;
        body.height = Utilities.PICTURE_SIZE;
    }

}
