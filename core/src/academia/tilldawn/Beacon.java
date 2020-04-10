package academia.tilldawn;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;

public class Beacon {

    private Rectangle position;

    public Beacon(){

        position = new Rectangle();
        position.x = 20;
        position.y = 20;
        position.width = Utilities.PICTURE_SIZE;
        position.height = Utilities.PICTURE_SIZE;
    }


    public Rectangle getPosition() {
        return position;
    }

    public float getX(){
        return position.x;
    }

    public float getY(){
        return position.y;
    }

}
