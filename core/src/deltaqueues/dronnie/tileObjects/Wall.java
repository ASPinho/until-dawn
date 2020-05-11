package deltaqueues.dronnie.tileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import static deltaqueues.dronnie.Utilities.WALL_BIT;

public class Wall extends InteractiveTileObject {

    public Wall(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(WALL_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Wall", "");
    }

    @Override
    public void afterHit() {

    }
}
