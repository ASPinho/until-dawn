package deltaqueues.dronnie.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import static deltaqueues.dronnie.Utilities.*;
import static deltaqueues.dronnie.Utilities.PLAYER_SPEED;

public class Dronnie extends AbstractElements {

    private boolean isInfected = false;
    private long infectionTime;
    private int score = 0;
    private int hp = 130;

    public Dronnie() {

        bodyPic = new Texture(Gdx.files.internal("bonnie-drone-32.png"));

        body = new Rectangle();
        body.x = PICTURE_SIZE * 2;
        body.y = BACKGROUND_HEIGHT / 2 - PICTURE_SIZE / 2;
        body.width = PICTURE_SIZE;
        body.height = PICTURE_SIZE;

    }

    public void move(){

        // Player move left
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.x -= PLAYER_SPEED * Gdx.graphics.getDeltaTime();
        }

        // Player move right
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.x += PLAYER_SPEED * Gdx.graphics.getDeltaTime();
        }

        // Horizontal bounds
        if (body.x < 0) body.x = 0;
        if (body.x > BACKGROUND_WIDTH - PICTURE_SIZE) body.x = BACKGROUND_WIDTH - PICTURE_SIZE;

        //vertical bounds
        if (body.y < 0) body.y = 0;
        if (body.y > BACKGROUND_HEIGHT - 100) body.y = BACKGROUND_HEIGHT - 100;

        // Player move up
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            body.y += PLAYER_SPEED * Gdx.graphics.getDeltaTime();
        }

        // Player move down
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            body.y -= PLAYER_SPEED * Gdx.graphics.getDeltaTime();
        }
    }

    public boolean isInfected() {
        return isInfected;
    }

    public void setInfected(boolean infected) {
        isInfected = infected;
        if(isInfected) { getSick(); }
    }

    public void getSick() {

        if((TimeUtils.nanoTime() - infectionTime > 1000000000)) {

        //setHp(-1);
        takeDamage(1);
        infectionTime = TimeUtils.nanoTime();
        // health.setHealth(hp) <- this method will draw!
        }
    }

    public int getScore() {
        return score;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void addHealth(int hp){
        this.hp += hp;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getHp() {
        return hp;
    }

    public void takeDamage(int hp) {
        this.hp -= hp;
    }
}
