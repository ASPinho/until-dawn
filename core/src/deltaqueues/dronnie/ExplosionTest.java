package deltaqueues.dronnie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;



public class ExplosionTest {

    public static final int SIZE = 64;
    private static final int FRAME_COLS = 4, FRAME_ROWS = 4;
    private static final float OFFSET = 8;
    Animation<TextureRegion> walkAnimation;
    Texture walkSheet;
    private final static int STARTING_X = 50;
    private final static int STARTING_Y = 50;
    TextureRegion reg;
    float stateTime;
    public boolean remove;
    float x, y;


    public ExplosionTest(float x, float y){
        this.x = x - OFFSET;
        this.y = y - OFFSET;
        createIdleAnimation();
    }

    private void createIdleAnimation() {
        walkSheet = new Texture(Gdx.files.internal("explosion9.png"));

        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth()/FRAME_COLS,
                walkSheet.getHeight()/FRAME_ROWS);

        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        //walkAnimation = new Animation<TextureRegion>(0.025f, walkFrames);
        walkAnimation = new Animation<TextureRegion>(0.05f,walkFrames);
        stateTime = 0f;
        reg=walkAnimation.getKeyFrame(0);
    }


    public void update (float deltatime) {
        stateTime += deltatime;
        if (walkAnimation.isAnimationFinished(stateTime))
            remove = true;
    }

    public void render (SpriteBatch batch) { batch.draw(walkAnimation.getKeyFrame(stateTime), x, y, SIZE, SIZE);
    }







    /*
    @Override
    public void act(float delta) {
        super.act(delta);

        stateTime += delta;
        reg = walkAnimation.getKeyFrame(stateTime,true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(reg,getX(),getY(),getWidth()/2,getHeight()/2,getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation());
    }
    */
}