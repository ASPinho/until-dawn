package deltaqueues.dronnie.backgrounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import deltaqueues.dronnie.streetGame.characters.Character1;
import deltaqueues.dronnie.streetGame.characters.Character2;
import deltaqueues.dronnie.streetGame.characters.Character3;

public class Street extends AbstractBackground {


    private Character1 character1;
    private Character2 character2;
    private Character3 character3;


    public Street(Array<Texture> textures) {
        super(textures);

        character1 = new Character1();
        character2 = new Character2();
        character3 = new Character3();
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

            if(i == 0) {

               speedDiference = 1;
                srcX = scroll + this.speedDiference *scroll;


                batch.draw(layers.get(i), x, y, originX, originY, width, heigth,scaleX,scaleY,rotation,srcX,srcY,layers.get(i).getWidth(),layers.get(i).getHeight(),flipX,flipY);
                continue;
            }
            if(i == 1) {

                speedDiference = 0;
                srcX =  scroll + i*this.speedDiference *scroll;


                batch.draw(layers.get(i), x, y, originX, originY, width, heigth,scaleX,scaleY,rotation,srcX,srcY,layers.get(i).getWidth(),layers.get(i).getHeight(),flipX,flipY);
                continue;
            }
            if(i == 2) {
                speedDiference = 0;
            }
            srcX = scroll + i*this.speedDiference *scroll;


            batch.draw(layers.get(i), x, y, originX, originY, width, heigth,scaleX,scaleY,rotation,srcX,srcY,layers.get(i).getWidth(),layers.get(i).getHeight(),flipX,flipY);
        }

        character1.getSprite().setPosition(character1.getX() - srcX * 3, character1.getY());
        character2.getSprite().setPosition(character2.getX() - srcX * 3, character2.getY());
        character3.getSprite().setPosition(character3.getX() - srcX * 3, character3.getY());
        character1.getSprite().draw(batch);
        character2.getSprite().draw(batch);
        character3.getSprite().draw(batch);
    }
}
