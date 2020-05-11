package deltaqueues.dronnie.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import deltaqueues.dronnie.Utilities;

public class Battery extends AbstractElements {

    public Battery() {

        body = new Rectangle();
        body.x = MathUtils.random(Utilities.DRONE_BACKGROUND_WIDTH /2);
        body.y = MathUtils.random( Utilities.DRONE_BACKGROUND_HEIGHT /2);
        body.width = Utilities.PICTURE_SIZE;
        body.height = Utilities.PICTURE_SIZE;

        bodyPic = new Texture(Gdx.files.internal("battery2.png"));

    }


}
