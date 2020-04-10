package academia.tilldawn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Toillet {

    private Texture toilletPic;
    private long lastToilletPapper;

    private Rectangle papper;

    public Toillet() {

        papper = new Rectangle();
        papper.x = MathUtils.random(Utilities.BACKGROUND_WIDTH/2);
        papper.y = MathUtils.random( Utilities.BACKGROUND_HEIGHT/2);
        papper.width = Utilities.PICTURE_SIZE;
        papper.height = Utilities.PICTURE_SIZE;

        toilletPic = new Texture(Gdx.files.internal("toillete.png"));

    }

    public Rectangle getPapper() {
        return papper;
    }

    public float getX() {
        return papper.x;
    }

    public float getY() {
        return papper.y;
    }

    public Texture getToilletPic(){
        return toilletPic;
    }

    public long getLastToilletPapper(){
        return lastToilletPapper;
    }
}
