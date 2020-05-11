package deltaqueues.dronnie.tileObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import deltaqueues.dronnie.screens.Test;
import deltaqueues.dronnie.streetGame.PlataformPlayer;

import static deltaqueues.dronnie.Utilities.*;
import static deltaqueues.dronnie.streetGame.PlataformPlayer.State.CROUCH;

public class EnemyShot extends Sprite {

    private Test screen;
    private PlataformPlayer player;
    private boolean playerIsRight;
    private World world;
    private Array<TextureRegion> frames;
    private Animation fireAnimation;
    private float stateTime;
    private boolean destroyed = false;
    private boolean setToDestroy;
    private boolean fireRight;
    private boolean hited;
    private CircleShape shape;

    private Body b2body;

    public EnemyShot(Test screen, float x, float y, PlataformPlayer player) {

        this.player = player;
        //fireRight = player.getRunningRight();
        this.screen = screen;
        this.world = screen.getWorld();
        frames = new Array<TextureRegion>();
        for (int i = 0; i <= 2; i++) {
            frames.add(new TextureRegion(screen.getAtlas3().findRegion("shot"), i * 15, 0, 15, 11));
        }
        fireAnimation = new Animation<TextureRegion>(0.2f, frames);
        setRegion((TextureRegion) fireAnimation.getKeyFrame(0));

        setBounds(x+1/ PPM, y+1/ PPM, 15/PPM, 11/PPM);

        defineGunShoot();
    }


    public void defineGunShoot() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(fireRight ? getX() : getX() - 12 / PPM, getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        if (!world.isLocked()) {
            b2body = world.createBody(bdef);
        }

        FixtureDef fdef = new FixtureDef();
        shape = new CircleShape();
        shape.setRadius(3 / PPM);
        fdef.filter.categoryBits = ENEMY_FIRE_BIT;
        fdef.filter.maskBits = PLAYER_BIT;


        fdef.shape = shape;
        fdef.restitution = 1;
        fdef.friction = 0;
        b2body.createFixture(fdef).setUserData(this);
        b2body.setLinearVelocity(new Vector2(rightOrLeft() ? 6f : -6f, 0f));
        b2body.setGravityScale(0);
    }


    public void update(float dt) {
        stateTime += dt;

        if (hited) {

            for (int i = 0; i <= 2; i++) {

                frames.add(new TextureRegion(screen.getAtlas3().findRegion("shotHit"), (i * 15) + 45, 0, 15, 11));
            }

            fireAnimation = new Animation<TextureRegion>(0.05f, frames);
            setRegion((TextureRegion) fireAnimation.getKeyFrame(stateTime));

        } else {

            setRegion((TextureRegion) fireAnimation.getKeyFrame(stateTime, true));
        }


        setPosition(b2body.getPosition().x, b2body.getPosition().y);

        if (stateTime > 2 && !destroyed) {
            //world.destroyBody(b2body);
            shape.dispose();
           destroyed = true;
            b2body.setLinearVelocity(0,0);
        }

        if ((rightOrLeft() && b2body.getLinearVelocity().x <= 5) || (!rightOrLeft() && b2body.getLinearVelocity().x >= -5 || b2body.getLinearVelocity().y > 0)) {

            b2body.setLinearVelocity(0,0);
            setHited(true);

        }
    }




    public void setHited(boolean input) {
        hited = input;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean input) {
        destroyed = input;
    }

    public boolean rightOrLeft() {
        if(player.getX() >= b2body.getPosition().x) {
            return playerIsRight = true;
        } else {
           return playerIsRight = false;
        }
    }
}
