package deltaqueues.dronnie.tileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import deltaqueues.dronnie.screens.Test;
import deltaqueues.dronnie.streetGame.PlataformPlayer;

import static deltaqueues.dronnie.Utilities.*;

public class Drone extends Enemy {

    private float stateTime;
    private boolean setToDestroy;
    private boolean destroyed;
    private boolean finalAnimation;
    private int count = 0;
    private float originX;
    private boolean reachBound1 = true;
    private boolean reachBound = false;
    private Array<EnemyShot> shots;
    private long currentTime;


    private Animation animation;
    private Array<TextureRegion> frames;


    public Drone(Test screen, float x, float y, TiledMap map, Rectangle rectangle, MapObject object) {
        super(screen, x, y, map, rectangle);


        frames = new Array<TextureRegion>();
        for (int i = 0; i <= 3; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("drone"), (i * 55), 0, 55, 52));
        }
        animation = new Animation<TextureRegion>(0.4f, frames);
        stateTime = 0;

        this.setBounds(getX(), getY(), 55 / PPM, 52 / PPM);

        originX = x;
        shots = new Array<>();
    }


    public void update(float delta) {

        stateTime += delta;

            if(!setToDestroy) {
                round();
                //b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
                setRegion((TextureRegion) animation.getKeyFrame(stateTime, true));
            }

        for(EnemyShot shot : shots) {
            shot.update(delta);
            if(shot.isDestroyed())
                shots.removeValue(shot, true);
        }

        if (setToDestroy && !destroyed) {


            if (count == 10) {
                destroyed=true;
            }
            for (int i = 1; i <= 6; i++) {

                frames.add(new TextureRegion(new Texture("enemies/explosions/explosion" + i + ".png")));

            }


            animation = new Animation<TextureRegion>(0.2f, frames);
            setRegion((TextureRegion) animation.getKeyFrame(stateTime,true));


            if (animation.isAnimationFinished(stateTime)) {

                //world.destroyBody(b2body);
                //destroyed = true;
            }
            count ++;
        }

    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.KinematicBody;
        bdef.position.set(getX(), getY());
        b2body = world.createBody(bdef);
        b2body.setUserData(this);
        shape.setAsBox((rectangle.getWidth() / 2) / PPM, (rectangle.getHeight() / 2) / PPM);
        screen.getFdef().shape = shape;

        shape.setRadius(5 / PPM);

        screen.getFdef().filter.categoryBits = DEFAULT_BIT;
        screen.getFdef().filter.maskBits = PLAYER_FIRE_BIT;

        screen.getFdef().shape = shape;
        b2body.createFixture(screen.getFdef()).setUserData(this);
        b2body = world.createBody(bdef);


        PolygonShape bodyBullet = new PolygonShape();
        bodyBullet.setAsBox(0/PPM,20/ PPM, new Vector2(0,-5/ PPM),0);
        screen.getFdef().shape = bodyBullet;
        screen.getFdef().filter.categoryBits = ENEMY_BIT;
        screen.getFdef().filter.maskBits = PLAYER_FIRE_BIT;
        b2body.createFixture(screen.getFdef()).setUserData(this);
      //  b2body = world.createBody(bdef);

    }


    public void fire(PlataformPlayer player) {
        if (TimeUtils.timeSinceMillis(currentTime) < 2000) {
            return;
        }
        if (player.getX() - b2body.getPosition().x <= 2 && player.getX() - b2body.getPosition().x >= -2) {
            if(player.getY() - b2body.getPosition().y <= 2 && player.getY() - b2body.getPosition().y >= -2)
                shots.add(new EnemyShot(screen, b2body.getPosition().x, b2body.getPosition().y -25/ PPM, player));
            currentTime = TimeUtils.millis();
        }
    }



    public Body getBody() {
        return b2body;
    }

    public void destroy() {
        setToDestroy = true;
    }
    public boolean getSetToDestroy() {
        return setToDestroy;
    }

    public boolean getDestroyed() {
        return destroyed;
    }

    public void draw(SpriteBatch batch, PlataformPlayer player) {

        if (!destroyed) {

            super.draw(batch);
            fire(player);
            for (EnemyShot shot : shots) {
                if(shot.isDestroyed()){
                    continue;
                }
                shot.draw(batch);
            }

        }
    }

    public void setAnimation(Array<TextureRegion> frames) {
        if(!finalAnimation) {
            animation = new Animation<TextureRegion>(1f, frames);
        }
        finalAnimation = true;
    }


    public void round() {


        if(b2body.getPosition().x < originX + 3f && !reachBound1) {
            b2body.setLinearVelocity(0.5f, 0f);
        }

        if(b2body.getPosition().x > originX -3f && !reachBound) {

            b2body.setLinearVelocity(-0.5f,0f);
        }

        if(b2body.getPosition().x >= originX +2) {
            reachBound1 = true;
            reachBound = false;
        }

        if ((b2body.getPosition().x <= originX -2)) {
            reachBound = true;
            reachBound1 = false;
        }
    }

}
