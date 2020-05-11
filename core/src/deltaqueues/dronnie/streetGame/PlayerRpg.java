package deltaqueues.dronnie.streetGame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import deltaqueues.dronnie.screens.HomeScreen;
import static deltaqueues.dronnie.Utilities.*;

public class PlayerRpg extends Sprite {

    public World world;
    public Body b2body;


    public PlayerRpg(World world, HomeScreen screen) {
        this.world = world;
        definePlayer();
        this.setRegion(screen.getBodyPic().get(0));
        setSize(PICTURE_SIZE,PICTURE_SIZE);
    }

    public void update(float delta) {
        setPosition(b2body.getPosition().x - getWidth()/2 , b2body.getPosition().y - getHeight() /2 );
    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(400, 100);

        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5);
        fdef.filter.categoryBits = PLAYER_BIT;
        fdef.filter.maskBits = DEFAULT_BIT | WALL_BIT | EXIT_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);

        EdgeShape bottom = new EdgeShape();
        bottom.set(new Vector2(-20,b2body.getPosition().y/2 - 100), new Vector2(20,b2body.getPosition().y/2 - 100));
        fdef.shape = bottom;
        //fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("Bottom");
    }

    public Body getBody() {
        return b2body;
    }

}
