package deltaqueues.dronnie.animations.attack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import deltaqueues.dronnie.attacks.activeAttacks.LaserPortal;

public class Portal {
    public static final int SIZE = 100;
    private static final int FRAME_COLS = 10, FRAME_ROWS = 10;
    private static final float OFFSET = 8;
    Animation<TextureRegion> walkAnimation;
    Texture walkSheet;
    private final static int STARTING_X = 50;
    private final static int STARTING_Y = 50;
    private TextureRegion reg;
    private float stateTime;
    public boolean remove;
    private float x, y;
    private LaserPortal laser;
    private Rectangle body;
    private long timeToClose;
    private boolean used = false;
    private boolean preparingToClose = false;


    public Portal(float x, float y, LaserPortal laser) {
        this.x = x;
        this.y = y;
        this.laser = laser;
        body = new Rectangle();
        body.x = x + 40;
        body.y = y + 40;

        createIdleAnimation();
    }

    private void createIdleAnimation() {
        walkSheet = new Texture(Gdx.files.internal("portal.png"));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / FRAME_COLS,
                walkSheet.getHeight() / FRAME_ROWS);

        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {

            for (int j = 0; j < FRAME_COLS; j++) {

                walkFrames[index++] = tmp[i][j];
            }
        }

        //walkAnimation = new Animation<TextureRegion>(0.025f, walkFrames);
        walkAnimation = new Animation<TextureRegion>(0.02f, walkFrames);
        stateTime = 0f;
        reg = walkAnimation.getKeyFrame(0);
    }


    public void update(float deltatime) {
        if (!laser.isLosted()) {
            return;
        }
        stateTime += deltatime;
        if (walkAnimation.isAnimationFinished(stateTime))
            stateTime = 0;
    }


    public void render(SpriteBatch batch) {
        if (!laser.isLosted()) {
            return;
        }

        if (used) {
            prepareToClose();
            if (TimeUtils.timeSinceMillis(timeToClose) > 500) {
                remove = true;
                return;
            }
        }

        if (stateTime >= 1.8f) {
            stateTime = 0.2f;
            return;
        }
        batch.draw(walkAnimation.getKeyFrame(stateTime), x, y);
    }

    public Rectangle getBody() {
        return body;
    }

    public void setPosition(float x, float y) {
        body.x += x;
        body.y += y;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public void setPreparingToClose(boolean prepareToClose) {
        this.preparingToClose = prepareToClose;
    }

    public void prepareToClose() {
        if (preparingToClose) {
            return;
        }
        timeToClose = TimeUtils.millis();
        setPreparingToClose(true);
    }
}
