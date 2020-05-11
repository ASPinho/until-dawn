package deltaqueues.dronnie.tileObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import deltaqueues.dronnie.screens.Test;
import deltaqueues.dronnie.streetGame.PlataformPlayer;
import deltaqueues.dronnie.tileObjects.Enemy;

import static deltaqueues.dronnie.Utilities.*;

public class Turret extends Enemy {

    private float stateTime;
    private Animation animation;
    private Array<TextureRegion> frames;
    private Array<EnemyShot> shots;
    private boolean setToDestroy;
    private boolean destroyed;
    private int count = 0;
    private long currentTime;


    public Turret(Test screen, float x, float y, TiledMap map, Rectangle rectangle) {
        super(screen, x, y + 5 / PPM, map, rectangle);
        frames = new Array<TextureRegion>();
        for (int i = 0; i <= 4; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("drone"), (i * 25) + 550, 0, 25, 23));
        }
        animation = new Animation<TextureRegion>(0.4f, frames);
        stateTime = 0;

        this.setBounds(getX(), getY(), 25 / PPM, 23 / PPM);

        shots = new Array<>();
    }

    public void update(float delta) {

        stateTime += delta;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion((TextureRegion) animation.getKeyFrame(stateTime, true));

        for (EnemyShot shot : shots) {
            shot.update(delta);
            if (shot.isDestroyed()) if (setToDestroy && !destroyed) {
                shots.removeValue(shot, true);
            }
        }

        if (setToDestroy && !destroyed) {

            if (count == 10) {

                destroyed = true;
            }
            for (int i = 1; i <= 7; i++) {

                frames.add(new TextureRegion(new Texture("enemies/explosions/explosion" + i + ".png")));

            }


            animation = new Animation<TextureRegion>(0.2f, frames);
            setRegion((TextureRegion) animation.getKeyFrame(stateTime, true));

            count++;
        }


    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(getX(), getY());
        b2body = world.createBody(bdef);
        shape.setAsBox((rectangle.getWidth() / 2) / PPM, ((rectangle.getHeight()) / 2) / PPM);
        screen.getFdef().shape = shape;


        screen.getFdef().filter.categoryBits = ENEMY_BIT;
        screen.getFdef().filter.maskBits = DEFAULT_BIT |
                PLAYER_FIRE_BIT;

        screen.getFdef().shape = shape;
        b2body.createFixture(screen.getFdef()).setUserData(this);
        b2body = world.createBody(bdef);
    }

    public void destroy() {

        setToDestroy = true;
    }

    @Override
    public boolean getDestroyed() {
        return destroyed;
    }

    public void fire(PlataformPlayer player) {
        if (TimeUtils.timeSinceMillis(currentTime) < 2000) {
            return;
        }
        if (player.getX() - b2body.getPosition().x <= 2 && player.getX() - b2body.getPosition().x >= -2) {
            if(player.getY() - b2body.getPosition().y <= 0.5 && player.getY() - b2body.getPosition().y >= -0.5)
            shots.add(new EnemyShot(screen, b2body.getPosition().x, b2body.getPosition().y, player));
            currentTime = TimeUtils.millis();
        }
    }


    public void draw(SpriteBatch batch, PlataformPlayer player) {
        if (!destroyed) {
            super.draw(batch);
            fire(player);
            for (EnemyShot shot : shots) {
                if(shot.isDestroyed()){
                    shots.removeValue(shot, true);
                    continue;
                }
                shot.draw(batch);
            }
        }
    }

    public Body getBody() {
        return b2body;
    }
}
