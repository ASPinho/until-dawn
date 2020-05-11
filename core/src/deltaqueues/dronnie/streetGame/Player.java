package deltaqueues.dronnie.streetGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import deltaqueues.dronnie.elements.allys.Dronnie;

import static deltaqueues.dronnie.Utilities.PICTURE_SIZE;

public class Player extends Dronnie {

    private Sprite player;
    private TextureAtlas textureAtlas;
    private int currentFrame = 1;
    private String currentAtlasKey = new String("idle-1");
    private boolean flip = false;
    private boolean walk = false;
    private int limit;




    public Player() {

        textureAtlas = new TextureAtlas(Gdx.files.internal("player/walk/walk.atlas"));
        TextureAtlas.AtlasRegion region = textureAtlas.findRegion("0001");
        player = new Sprite(region);
        animation();

        player.setSize(100, 100);
        body = new Rectangle();
        body.x = PICTURE_SIZE * 2;
        body.y = PICTURE_SIZE * 2;
        body.width = PICTURE_SIZE * 2;
        body.height = PICTURE_SIZE * 2;
        previousX = body.x;
    }


    public Sprite getSprite() {
        return player;
    }

    public void setPlayer(Sprite player) {
        this.player = player;
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


    public void animation() {
        Timer.schedule(new Timer.Task() {
                           @Override
                           public void run() {
        if(walk) {
            textureAtlas = new TextureAtlas(Gdx.files.internal("player/walk/walk.atlas"));
            TextureAtlas.AtlasRegion region = textureAtlas.findRegion("0001");
            limit = 16;
            player.setRegion(region);
        } else {
            textureAtlas = new TextureAtlas(Gdx.files.internal("player/idle/idle2.atlas"));
            TextureAtlas.AtlasRegion region = textureAtlas.findRegion("0001");
            limit = 4;
            player.setRegion(region);
        }
                               currentFrame++;
                               if (currentFrame > limit)
                                   currentFrame = 1;

                               // ATTENTION! String.format() doesnt work under GWT for god knows why...
                               currentAtlasKey = String.format("%04d", currentFrame);
                               player.setRegion(textureAtlas.findRegion(currentAtlasKey));
                               if(flip){
                                   player.flip(true,false);
                               }
                           }
                       }
                , 0, 1 / 10.0f);
    }
}
