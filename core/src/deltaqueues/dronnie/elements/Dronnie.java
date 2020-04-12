package deltaqueues.dronnie.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import deltaqueues.dronnie.elements.enemies.FinalBoss;

import static deltaqueues.dronnie.Utilities.*;
import static deltaqueues.dronnie.Utilities.PLAYER_SPEED;

public class Dronnie extends AbstractElements {

    private boolean isInfected = false;

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
    }
}
