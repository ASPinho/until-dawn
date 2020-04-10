package academia.tilldawn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import static academia.tilldawn.Utilities.BACKGROUND_HEIGHT;
import static academia.tilldawn.Utilities.BACKGROUND_WIDTH;

public class Target {

    private Rectangle position;
    private Texture trump;
    private int hp = 200;

    public Target(){
        position = new Rectangle();
        position.x = BACKGROUND_WIDTH - 800;
        position.y = BACKGROUND_HEIGHT - 800;
        position.width = 200;
        position.height = 200;
        trump = new Texture(Gdx.files.internal("unnamed.png"));
    }

    public void takeDamage(){
        hp -= 15;
    }

    public Rectangle getPosition() {
        return position;
    }

    public boolean isDead(){
        return hp <= 0;
    }

    public float getX(){
        return position.x;
    }

    public float getY(){
        return position.y;
    }

    public Texture getTrump() {
        return trump;
    }
}
