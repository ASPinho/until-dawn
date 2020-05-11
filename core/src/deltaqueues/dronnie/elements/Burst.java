package deltaqueues.dronnie.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import deltaqueues.dronnie.Utilities;

import static deltaqueues.dronnie.Utilities.PICTURE_SIZE;

public class Burst extends AbstractElements{

    private long time;

    public Burst(float x, float y) {
        this.player = player;

        body = new Rectangle();
        body.x = x;
        body.y = y;
        body.width = PICTURE_SIZE * 4;
        body.height = PICTURE_SIZE * 4;

        bodyPic = new Texture(Gdx.files.internal("burst.png"));
        time = TimeUtils.millis();
    }

    public long getTime() {
        return time;
    }
}
