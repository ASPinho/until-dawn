package deltaqueues.dronnie.tileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import static deltaqueues.dronnie.Utilities.EXITED_BIT;
import static deltaqueues.dronnie.Utilities.EXIT_BIT;

public class ExitHouses extends InteractiveTileObject {

    public ExitHouses(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(EXIT_BIT);
    }


    @Override
    public void onHeadHit() {
        Gdx.app.log("Exit", "");
        setCategoryFilter(EXITED_BIT);
    }

    @Override
    public void afterHit() {

    }
}
