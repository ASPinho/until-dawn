package deltaqueues.dronnie.backgrounds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Forest extends AbstractBackground {



    public Forest(Array<Texture> textures) {

        super(textures);
        speedDiference = 1;
    }

    @Override
    public void setSpeed(int newSpeed) {
        super.setSpeed(newSpeed);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);
    }
}
