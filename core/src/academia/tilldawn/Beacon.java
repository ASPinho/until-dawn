package academia.tilldawn;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;

public class Beacon {

    private Rectangle position;
    private Camera camera;

    public Beacon(Camera camera){
        this.camera = camera;

        position = new Rectangle();
        position.x = 20;
        position.y = 20;
        position.width = Utilities.PICTURE_SIZE;
        position.height = Utilities.PICTURE_SIZE;
    }

    public void draw(){
    }

    public float getX(){
        return position.x;
    }

    public float getY(){
        return position.y;
    }

}
