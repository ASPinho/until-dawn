package deltaqueues.dronnie.elements.allys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import deltaqueues.dronnie.Directions;
import deltaqueues.dronnie.Utilities;
import deltaqueues.dronnie.elements.AbstractElements;

import static deltaqueues.dronnie.Utilities.*;
import static deltaqueues.dronnie.Utilities.PLAYER_SPEED;

public class Dronnie extends AbstractElements {

    protected float previousX;
    protected float previousY;

    protected boolean isInfected = false;
    protected long infectionTime;
    protected int score = 0;
    protected int hp = 130;
    protected int energy = 130;
    protected boolean canHide = true;
    protected boolean invisibleMode = false;
    protected Directions direction = Directions.STAY;
    protected int speed = 1;
    protected boolean alive = true;


    public Dronnie() {

        bodyPic = new Texture(Gdx.files.internal("bonnie-drone-32.png"));


        body = new Rectangle();
        body.x = PICTURE_SIZE * 2;
        body.y = PICTURE_SIZE * 2;
        body.width = PICTURE_SIZE;
        body.height = PICTURE_SIZE;
        previousX = body.x;
    }

    public float getPreviousX() {
        return previousX;
    }

    public void setPreviousX(float previousX) {
        this.previousX = previousX;
    }

    public float getPreviousY() {
        return previousY;
    }

    public void setPreviousY(float previousY) {
        this.previousY = previousY;
    }

    public void setPosition(float x, float y) {
        body.x = x;
        body.y = y;
    }


    public void move(int backgroundWidth, int backgroundHeight) {

        if(!alive) {
            return;
        }

        // Player move left
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.x -= PLAYER_SPEED * Gdx.graphics.getDeltaTime() * speed;
            direction = Directions.LEFT;
        }

        // Player move right
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.x += PLAYER_SPEED * Gdx.graphics.getDeltaTime() * speed;
            direction = Directions.RIGHT;
        }

        // Horizontal bounds
        if (body.x < 0) body.x = 0;
        if (body.x > backgroundWidth - PICTURE_SIZE) body.x = backgroundWidth - PICTURE_SIZE;

        //vertical bounds
        if (body.y < 0) body.y = 0;
        if (body.y > backgroundHeight - 100) body.y = backgroundHeight - 100;

        // Player move up
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            body.y += PLAYER_SPEED * Gdx.graphics.getDeltaTime() * speed;
            direction = Directions.UP;
        }

        // Player move down
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            body.y -= PLAYER_SPEED * Gdx.graphics.getDeltaTime() * speed;
            direction = Directions.DOWN;
        }
    }

    public Directions getDirection() {
        return direction;
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }

    public boolean isInfected() {
        return isInfected;
    }

    public void setInfected(boolean infected) {
        isInfected = infected;
        if (isInfected) {
            getSick();
        }
    }

    public void getSick() {

        if ((TimeUtils.nanoTime() - infectionTime > 1000000000)) {

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

    public void addHealth(int hp) {
        if (this.hp + hp > 130) {
            hp = 130 - (this.hp + hp);
        }
        this.hp += hp;
    }

    public void addScore(int score) {
        this.score += score;
        if (this.score > 100) {
            canHide = true;
        }
        if (this.score > 300) {
            //1st satelite
        }
        if (this.score > 500) {
            //automatic shoot
        }
        if (this.score > 100) {
            //ally
        }
    }

    public int getHp() {
        return hp;
    }

    public void takeDamage(int hp) {
        this.hp -= hp;
    }

    public int getEnergy() {
        return energy;
    }

    public void addEnergy(int energy) {
        this.energy += energy;
    }

    public void wasteEnergy(int energy) {
        this.energy -= energy;
        if (energy <= 0) {
            setInvisibleMode(false);
        }
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setInvisibleMode(boolean invisible) {
        invisibleMode = invisible;
    }

    public boolean getCanHide() {
        return canHide;
    }

    public boolean getInvisibleMode() {
        return invisibleMode;
    }

    public void boost() {
        speed = 10;
    }

    public void normalSpeed() {
        if(score > 700){
            speed = 2;
            return;
        }
       speed = 1;
    }

    public boolean isMoving() {
        if (previousX == body.x && previousY == body.y) {
            return false;
        }
        return true;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
