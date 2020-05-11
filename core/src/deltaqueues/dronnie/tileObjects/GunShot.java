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

public class GunShot extends Sprite {

    private Test screen;
    private PlataformPlayer.State state;
    private World world;
    private Array<TextureRegion> frames;
    private Animation fireAnimation;
    private float stateTime;
    private boolean destroyed;
    private boolean setToDestroy;
    private boolean fireRight;
    private boolean hited;


    public Body b2body;

    public GunShot(Test screen, float x, float y, boolean fireRight, PlataformPlayer.State state) {
        this.state = state;
        this.fireRight = fireRight;
        this.screen = screen;
        this.world = screen.getWorld();
        frames = new Array<TextureRegion>();
        for (int i = 0; i <= 2; i++) {
            frames.add(new TextureRegion(screen.getAtlas3().findRegion("shot"), i * 15, 0, 15, 11));
        }
        fireAnimation = new Animation<TextureRegion>(0.2f, frames);
        setRegion((TextureRegion) fireAnimation.getKeyFrame(0));
        if (state == CROUCH) {
            setBounds(fireRight ? x : x - 20 / PPM, y + 8 / PPM, 15 / PPM, 11 / PPM);
        } else {
            setBounds(x - 3 / PPM, y + 25 / PPM, 15 / PPM, 11 / PPM);
        }
        defineGunShoot();
    }

    public void defineGunShoot() {
        BodyDef bdef = new BodyDef();

        if (state == CROUCH) {
            bdef.position.set(fireRight ? getX() : getX() - 12 / PPM, getY() + 8 / PPM);
        } else {
            bdef.position.set(fireRight ? getX() : getX() - 12 / PPM, getY());
        }
        bdef.type = BodyDef.BodyType.DynamicBody;
        if (!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / PPM);
        fdef.filter.categoryBits = PLAYER_FIRE_BIT;
        fdef.filter.maskBits = ENEMY_BIT;


        fdef.shape = shape;
        fdef.restitution = 1;
        fdef.friction = 0;
        b2body.createFixture(fdef).setUserData(this);
        b2body.setLinearVelocity(new Vector2(fireRight ? 6 : -6, 0f));
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


        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if ((stateTime > 3 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }

        if ((fireRight && b2body.getLinearVelocity().x <= 0) || (!fireRight && b2body.getLinearVelocity().x >= 0 || b2body.getLinearVelocity().y > 0)) {

            setHited(true);
            b2body.setLinearVelocity(0,0);
            //setToDestroy();
        }
    }

    public void setToDestroy() {
        setToDestroy = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setHited(boolean hited) {
        this.hited = hited;
    }

}
