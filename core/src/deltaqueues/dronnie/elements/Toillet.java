package deltaqueues.dronnie.elements;

import deltaqueues.dronnie.Utilities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Toillet extends AbstractElements {

    public Toillet() {

        body = new Rectangle();
        body.x = MathUtils.random(Utilities.BACKGROUND_WIDTH/2);
        body.y = MathUtils.random( Utilities.BACKGROUND_HEIGHT/2);
        body.width = Utilities.PICTURE_SIZE;
        body.height = Utilities.PICTURE_SIZE;

        bodyPic = new Texture(Gdx.files.internal("toillete.png"));

    }

}
