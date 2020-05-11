package deltaqueues.dronnie.attacks.activeAttacks;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import deltaqueues.dronnie.attacks.AbstractProjectiles;


import static deltaqueues.dronnie.Utilities.PICTURE_SIZE;

public class EnergyBall extends AbstractProjectiles {

    public EnergyBall(Rectangle player) {
        this.player = player;

        body = new Rectangle();
        body.x = player.x - PICTURE_SIZE;
        body.y = player.y - PICTURE_SIZE;
        body.width = PICTURE_SIZE * 4;
        body.height = PICTURE_SIZE * 4;

        bodyPic = new Texture(Gdx.files.internal("player-attack.png"));
    }

    public void shoot() {
        body.x = player.x - PICTURE_SIZE;
        body.y = player.y - PICTURE_SIZE;
    }
}
