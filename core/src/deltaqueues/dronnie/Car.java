package deltaqueues.dronnie;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import deltaqueues.dronnie.screens.Test;

import static deltaqueues.dronnie.Utilities.PPM;

public class Car extends Sprite {

    private Test screen;
    private World world;
    private float stateTime;
    private float x, y;
    private int carType;
    private float move = 0;
    private float count;

    public Car(Camera camera) {

        this.screen = screen;
        this.world = world;

        carType = (int) Math.floor((Math.random() * 10) * 3);
        if (carType > 2) {
            setTexture(new Texture("cars/police.png"));
            setBounds(x + 1 / PPM, y + 1 / PPM, 163 / PPM, 60 / PPM);
        } else if (carType > 1) {
            setTexture(new Texture("cars/truck.png"));
            setBounds(x + 1 / PPM, y + 1 / PPM, 257 / PPM, 104 / PPM);
        } else if (carType >= 0) {
            setTexture(new Texture("cars/yellow.png"));
            setBounds(x + 1 / PPM, y + 1 / PPM, 93 / PPM, 60 / PPM);
        }

        setPosition(camera.viewportWidth -100, camera.viewportHeight/1.5f);
        x = camera.viewportWidth - 100;
    }


    public void update(float dt) {
      count += 18;
      move -= getX() - count;
      setPosition(getX() - move, getY());
    }


/*
    public void draw(SpriteBatch batch, float dt){
       // batch.draw(getTexture(), camera2.viewportWidth/2, camera2.viewportHeight /2);
        move += 18;
        draw(getTexture(), getX() - move, getY());
        setX(getX() - move);
    }*/


}
