package deltaqueues.dronnie.tileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import deltaqueues.dronnie.streetGame.PlataformPlayer;

import static deltaqueues.dronnie.Utilities.*;

public class Ladder extends InteractiveTileObject {


    private PlataformPlayer player;

    private Body b2body;
    private Rectangle bounds;

    public Ladder(World world, TiledMap map, Rectangle bounds, PlataformPlayer plataformPlayer) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(LADDER_BIT);
        player = plataformPlayer;
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Ladder", "Collision");

        climb();
    }



    public void climb() {

        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            if(TimeUtils.timeSinceMillis(player.getTimeToHang()) > 500) {
                player.setOnLadder(true);
                player.getBody().setLinearVelocity(0, 0);
                player.getBody().setGravityScale(0);
            }
        }

    }


    public void jumpOffLadder() {
            player.setOnLadder(false);
            player.getBody().setGravityScale(1);
    }

    @Override
    public void afterHit() {

        jumpOffLadder();
    }

}