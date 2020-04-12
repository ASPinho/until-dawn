package deltaqueues.dronnie.attacks;

import deltaqueues.dronnie.Utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class PlayerProjectile extends AbstractProjectiles {

    public PlayerProjectile(Rectangle player){
        this.player = player;

        body = new Rectangle();
        body.x = player.x;
        body.y = player.y;
        body.width = Utilities.PICTURE_SIZE*4;
        body.height = Utilities.PICTURE_SIZE*4;

        bodyPic = new Texture(Gdx.files.internal("player-attack.png"));
    }

    public void shoot(){
        body.x = player.x;
        body.y = player.y;
    }


}
