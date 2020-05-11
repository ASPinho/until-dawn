package deltaqueues.dronnie.backgrounds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

public class Mountains extends AbstractBackground {


    public Mountains(Array<Texture> textures) {
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
        scroll+=speed;
        for(int i = 0;i<layers.size;i++) {

            srcX = scroll + i*this.speedDiference *scroll;

            if(i == 0) {
                srcX = 0;
            }

            batch.draw(layers.get(i), x, y, originX, originY, width, heigth,scaleX,scaleY,rotation,srcX,srcY,layers.get(i).getWidth(),layers.get(i).getHeight(),flipX,flipY);
        }
    }
}
