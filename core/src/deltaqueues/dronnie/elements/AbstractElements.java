package deltaqueues.dronnie.elements;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class AbstractElements {

    protected Rectangle body;
    protected Rectangle player;
    protected Texture bodyPic;

    public float getX(){
        return body.x;
    }

    public float getY() { return body.y; }

    public Rectangle getBody() {
        return body;
    }

    public Texture getBodyPic() {
        return bodyPic;
    }


}
