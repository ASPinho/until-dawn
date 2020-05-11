package deltaqueues.dronnie.tileObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import deltaqueues.dronnie.screens.Test;

import static deltaqueues.dronnie.Utilities.PPM;

public abstract class Enemy extends Sprite {

    protected World world;
    protected Test screen;
    protected Body b2body;
    protected Rectangle rectangle;
    protected Vector2 velocity;
    protected PolygonShape shape;
    protected TiledMap map;
    protected Array<GunShot> shots;



    public Enemy(Test screen, float x, float y, TiledMap map, Rectangle rectangle) {

        this.map = map;
        this.rectangle = rectangle;
        this.world = screen.getWorld();
        this.screen = screen;
        shots = new Array<>();
        setPosition(x,y);
        shape = new PolygonShape();
        defineEnemy();
        velocity = new Vector2(1, 0);
    }

    public abstract void update(float delta);
    protected abstract void defineEnemy();
    public abstract void destroy();
    public abstract boolean getDestroyed();



    public void reverseVelocity(boolean x, boolean y) {

        if(x){
            velocity.x = -velocity.x;
        }
        if(y) {
            velocity.y = -velocity.y;
        }
    }


}
