package deltaqueues.dronnie.backgrounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

import static deltaqueues.dronnie.Utilities.PPM;

public class RoofTop extends AbstractBackground {


    public RoofTop(Array<Texture> textures) {
        super(textures);
    }

    @Override
    public void setSpeed(int newSpeed) {
        super.setSpeed(newSpeed);
    }


    public void draw(Batch batch, Camera camera) {


        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * Gdx.graphics.getDeltaTime()*200);

        scroll += speed;
        for (int i = 0; i < layers.size; i++) {
            srcX = scroll + i * this.speedDiference * scroll;
            if(i == 0) {
                y = camera.viewportHeight / 3;

            } else {
               y = 0;
            }
            batch.draw(layers.get(i), x, y, originX, originY, camera.viewportWidth, camera.viewportHeight, scaleX, scaleY, rotation, srcX, srcY, layers.get(i).getWidth(), layers.get(i).getHeight(), flipX, flipY);
        }
    }
}

