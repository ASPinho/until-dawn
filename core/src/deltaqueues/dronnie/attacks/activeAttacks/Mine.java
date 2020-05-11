package deltaqueues.dronnie.attacks.activeAttacks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import deltaqueues.dronnie.attacks.AbstractProjectiles;

public class Mine extends AbstractProjectiles {

    private boolean intact = true;

    public Mine(float x, float y) {
        body = new Rectangle();
        body.x = x;
        body.y = y;
        body.width = 20;
        body.height = 20;
        bodyPic = new Texture(Gdx.files.internal("mine2.png"));
    }

    public void setIntact(boolean intact) {
        this.intact = intact;
    }

    public boolean isIntact() {
        return intact;
    }
}
