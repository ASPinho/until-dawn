import academia.tilldawn.Utilities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class PlayerProjectile extends AbstractProjectiles{

    private Rectangle shockwave;
    private Texture shockwavePic;

    public PlayerProjectile(Rectangle player){
        shockwave = new Rectangle();
        shockwave.x = player.x;
        shockwave.y = player.y;
        shockwave.width = Utilities.PICTURE_SIZE*2;
        shockwave.height = Utilities.PICTURE_SIZE*2;

        shockwavePic = new Texture(Gdx.files.internal("player-attack.png"));
    }

    public Texture getShockwavePic() {
        return shockwavePic;
    }

    public float getX(){
        return shockwave.x;
    }

    public float getY(){
        return shockwave.y;
    }

}
