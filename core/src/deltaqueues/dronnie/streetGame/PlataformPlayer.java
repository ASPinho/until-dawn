package deltaqueues.dronnie.streetGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import deltaqueues.dronnie.tileObjects.GunShot;
import deltaqueues.dronnie.screens.Test;


import static deltaqueues.dronnie.Utilities.*;
import static deltaqueues.dronnie.streetGame.PlataformPlayer.State.*;

public class PlataformPlayer extends Sprite {

    public enum State {CLIMBING, FALLING, SHOOTING, RUN_SHOOTING, JUMPING, IDLE, RUNNING, CROUCH,}

    private Test screen;
    public State currentState;
    public State previousState;
    private Animation run;
    private Animation jump;
    private Animation standing;
    private Animation climbing;
    private Animation runShooting;
    private TextureRegion crouch;
    private TextureRegion shooting;
    private TextureRegion falling;
    private TextureRegion climb;
    private float stateTimer;
    private long timeToJump;
    private long timeToHang;
    private long timeToClimb;
    private boolean runningRight;

    public Array<GunShot> shots;

    private World world;
    private Body b2body;
    private boolean onLadder;
    private boolean crouched;
    private boolean idle;
    private boolean shoot;
    private boolean runShoot;

    private FixtureDef fdef = new FixtureDef();

    private TextureRegion region;

    private float previousX;
    private double hp = 100;

    private boolean hanging;


    public PlataformPlayer(World world, Test screen) {

        super(screen.getAtlas2().findRegion("idle2"));
        this.screen = screen;
        this.world = world;
        currentState = State.IDLE;
        previousState = State.IDLE;
        stateTimer = 0;
        runningRight = true;

        shots = new Array<GunShot>();

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i <= 2; i++) {
            frames.add(new TextureRegion(getTexture(), i * 71, 213, 71, 67));

        }

        run = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i = 0; i <= 3; i++) {
            frames.add(new TextureRegion(getTexture(), i * 71, 71, 71, 67));
        }
        jump = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i = 2; i <= 5; i++) {
            frames.add(new TextureRegion(getTexture(), i * 71, 0, 71, 67));
        }
        standing = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i = 0; i <= 4; i++) {
            frames.add(new TextureRegion(getTexture(), i * 71, 142, 71, 67));
        }
        climbing = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i = 0; i <= 2; i++) {
            frames.add(new TextureRegion(getTexture(), (i * 71) + 213, 201, 71, 67));
        }
        runShooting = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        shooting = new TextureRegion(screen.getAtlas2().findRegion("shoot"), 0, 0, 71, 67);
        falling = new TextureRegion(screen.getAtlas2().findRegion("jump"), 3 * 71, 0, 71, 67);
        crouch = new TextureRegion(screen.getAtlas2().findRegion("crouch"), 0, 0, 71, 67);
        climb = new TextureRegion(screen.getAtlas2().findRegion("climb"), 0, 0, 71, 67);
        setBounds(8, 0, 71 / PPM, 67 / PPM);
        //setRegion(standing);
        definePlayer();
        previousX = getBody().getPosition().x;
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, (b2body.getPosition().y - getHeight() / 7));
        setRegion(getFrame(dt));
        shoot();

        for (GunShot shot : shots) {
            shot.update(dt);
            if (shot.isDestroyed())
                shots.removeValue(shot, true);
        }
    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(0 / PPM, 100 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / PPM);

        fdef.filter.categoryBits = PLAYER_BIT;
        fdef.filter.maskBits = DEFAULT_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);

        EdgeShape ladder = new EdgeShape();
        ladder.set(new Vector2(0 / PPM, -5 / PPM), new Vector2(0 / PPM, 40 / PPM));
        fdef.shape = ladder;
        //fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("ladder");

        EdgeShape bottom = new EdgeShape();

        bottom.set(new Vector2(-10 / PPM, -5 / PPM), new Vector2(10 / PPM, -5 / PPM));
        fdef.shape = bottom;
        b2body.createFixture(fdef).setUserData("bottom");

        fdef.filter.categoryBits = PLAYER_BIT;
        fdef.filter.maskBits = DEFAULT_BIT;

        PolygonShape bodyBullet = new PolygonShape();
        bodyBullet.setAsBox(1 / PPM, 20 / PPM, new Vector2(0, 20 / PPM), 0);
        fdef.shape = bodyBullet;
        b2body.createFixture(fdef).setUserData(this);

        fdef.filter.categoryBits = PLAYER_BIT;
        fdef.filter.maskBits = ENEMY_FIRE_BIT;
    }

    public Body getBody() {
        return b2body;
    }

    public void checkGroundCollision() {


        if (b2body.getFixtureList().size < 4) {
            if (!onLadder) {
                fdef = new FixtureDef();
                EdgeShape bottom = new EdgeShape();

                bottom.set(new Vector2(-10 / PPM, -5 / PPM), new Vector2(10 / PPM, -5 / PPM));
                fdef.shape = bottom;

                fdef.filter.categoryBits = PLAYER_BIT;
                fdef.filter.maskBits = DEFAULT_BIT | ENEMY_BIT;

                b2body.createFixture(fdef).setUserData("bottom");

                PolygonShape bodyBullet = new PolygonShape();
                bodyBullet.setAsBox(1 / PPM, 20 / PPM, new Vector2(0, 20 / PPM), 0);
                fdef.shape = bodyBullet;
                b2body.createFixture(fdef);

                fdef.filter.categoryBits = PLAYER_BIT;
                fdef.filter.maskBits = ENEMY_FIRE_BIT;


            }
            return;
        }
        if (onLadder) {
            b2body.destroyFixture(b2body.getFixtureList().get(2));
        }
    }

    public void move() {



        if (!onLadder) {

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && TimeUtils.timeSinceMillis(timeToJump) > 500 && getBody().getLinearVelocity().y <= 2 && !crouched) {
                if (currentState != JUMPING) {
                    getBody().applyLinearImpulse(new Vector2(0, 5f), getBody().getWorldCenter(), true);
                    timeToJump = TimeUtils.millis();
                    return;
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) && getBody().getLinearVelocity().x <= 2 && !crouched) {
                getBody().applyLinearImpulse(0.25f, 0f, getX(), getY(), true);

                return;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) && getBody().getLinearVelocity().x >= -2 && !crouched) {
                getBody().applyLinearImpulse(-0.25f, 0f, getX(), getY(), true);

                return;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S) && getBody().getLinearVelocity().y >= -2 && getState() != FALLING) {
                setCrouched(true);
                return;
            }

            if (!Gdx.input.isKeyPressed(Input.Keys.S)) {
                setCrouched(false);
            }
        }


        if (onLadder) {


            if (Gdx.input.isKeyPressed(Input.Keys.W) && getBody().getLinearVelocity().y <= 2) {
                getBody().applyLinearImpulse(new Vector2(0, 0.5f), getBody().getWorldCenter(), true);
                timeToClimb = TimeUtils.millis();
                return;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) && getBody().getLinearVelocity().x <= 2) {
                getBody().applyLinearImpulse(0.15f, 0f, getX(), getY(), true);
                // standing.setX(standing.getBody().getPosition().x);
                // standing.setRegion(bodyPic.get(1));
                return;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) && getBody().getLinearVelocity().x >= -2) {
                getBody().applyLinearImpulse(-0.15f, 0f, getX(), getY(), true);
                //standing.setRegion(bodyPic.get(2));
                return;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S) && getBody().getLinearVelocity().y >= -2) {
                getBody().applyLinearImpulse(new Vector2(0, -0.5f), getBody().getWorldCenter(), true);
                timeToClimb = TimeUtils.millis();
                return;
            }

            getBody().setLinearVelocity(0, 0);
        }
    }

    public boolean isOnLadder() {
        return onLadder;
    }

    public void setOnLadder(boolean onLadder) {
        this.onLadder = onLadder;
        timeToHang = TimeUtils.millis();
        if (!onLadder) {
            hanging = false;
        }
    }

    public boolean isHanging() {
        return hanging;
    }

    public void setHanging(boolean hanging) {
        this.hanging = hanging;
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();


        switch (currentState) {
            case FALLING:
                region = falling;
                break;
            case RUN_SHOOTING:
                region = (TextureRegion) runShooting.getKeyFrame(stateTimer, true);
                break;
            case SHOOTING:
                region = shooting;
                break;
            case CLIMBING:
                if (b2body.getLinearVelocity().y == 0 && TimeUtils.timeSinceMillis(timeToClimb) > 100) {
                    region = climb;
                    break;
                }
                region = (TextureRegion) climbing.getKeyFrame(stateTimer, true);
                break;
            case JUMPING:
                region = (TextureRegion) jump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) run.getKeyFrame(stateTimer, true);
                break;
            case CROUCH:
                region = crouch;
                break;
            default:
                region = (TextureRegion) standing.getKeyFrame(stateTimer, true);
                break;
        }

        if ((b2body.getLinearVelocity().x < 0f || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x >= 0.5 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {


        if (crouched && b2body.getLinearVelocity().y == 0 && !onLadder) {
            idle = false;
            return CROUCH;
        } else if (onLadder || ((onLadder) & ((Gdx.input.isKeyPressed(Input.Keys.W)) | (Gdx.input.isKeyPressed(Input.Keys.S))))) {
            idle = false;
            return CLIMBING;
        } else if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == JUMPING)) {
            idle = false;
            return JUMPING;
        } else if (b2body.getLinearVelocity().y < 0) {
            idle = false;
            return FALLING;
        } else if (runShoot) {
            idle = false;
            return RUN_SHOOTING;
        } else if (b2body.getLinearVelocity().x != 0) {
            idle = false;
            return State.RUNNING;
        } else if (shoot) {
            return SHOOTING;
        } else {
            idle = true;
            return State.IDLE;
        }
    }


    public State shoot() {

        if ((b2body.getLinearVelocity().x != 0) && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            runShoot = true;
            return RUN_SHOOTING;
        }

        if ((b2body.getLinearVelocity().x != 0) && !Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            runShoot = false;
            return RUNNING;
        }

        if ((b2body.getLinearVelocity().x == 0 && b2body.getLinearVelocity().y == 0) && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            setShoot(true);
            return SHOOTING;
        }

        if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            setShoot(false);
            return IDLE;
        }
        return IDLE;
    }

    public void fire() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            shots.add(new GunShot(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false, getState()));
        }
    }


    public boolean isCrouched() {
        return crouched;
    }

    public void setCrouched(boolean crouched) {
        this.crouched = crouched;
    }

    public boolean isShoot() {
        return shoot;
    }

    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
        for (GunShot shot : shots)
            shot.draw(batch);
    }

    public boolean getRunningRight() {
        return runningRight;
    }

    public void takeDamage() {

        hp -= 0.5;

        if (hp <= 0) {
            hp = 0;
            return;
        }
    }

    public double getHp() {
        return hp;
    }

    public long getTimeToHang() {
        return timeToHang;
    }
}
