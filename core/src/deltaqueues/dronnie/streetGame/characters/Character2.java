package deltaqueues.dronnie.streetGame.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import deltaqueues.dronnie.elements.allys.Dronnie;

import static deltaqueues.dronnie.Utilities.PICTURE_SIZE;

public class Character2 extends Dronnie{

        private Sprite sprite;
        private TextureAtlas textureAtlas;
        private int currentFrame = 1;
        private String currentAtlasKey = new String("idle-1");
        private boolean flip = false;
        private boolean walk = false;
        private int limit;

        public Character2() {

            textureAtlas = new TextureAtlas(Gdx.files.internal("characters/char2/char2.atlas"));
            TextureAtlas.AtlasRegion region = textureAtlas.findRegion("0001");
            sprite = new Sprite(region);
            animation();

            sprite.setSize(50, 45);
            //sprite.setPosition(100,100);
            body = new Rectangle();
            body.x = 1100;
            body.y = 50;
            body.width = PICTURE_SIZE * 2;
            body.height = PICTURE_SIZE * 2;
            previousX = body.x;
        }


        public void animation() {
            Timer.schedule(new Timer.Task() {
                               @Override
                               public void run() {
                                   currentFrame++;
                                   if (currentFrame > 4)
                                       currentFrame = 1;

                                   currentAtlasKey = String.format("%04d", currentFrame);
                                   sprite.setRegion(textureAtlas.findRegion(currentAtlasKey));
                                   if (flip) {
                                       sprite.flip(true, false);
                                   }
                               }
                           }
                    , 0, 1 / 05.0f);
        }


        public Sprite getSprite() {
            return sprite;
        }

        public void setSprite(Sprite sprite) {
            this.sprite = sprite;
        }

        public void flipPlayer() {
            flip = true;
        }

        public void unFlipPlayer() {
            flip = false;
        }

        public void walk() {
            this.walk = true;
        }

        public void idle() {
            this.walk = false;
        }

    }



