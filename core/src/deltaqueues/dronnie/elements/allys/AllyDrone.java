package deltaqueues.dronnie.elements.allys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import deltaqueues.dronnie.Directions;
import deltaqueues.dronnie.Utilities;
import deltaqueues.dronnie.elements.AbstractElements;


import static deltaqueues.dronnie.Utilities.PICTURE_SIZE;

public class AllyDrone extends AbstractElements {

    private Directions direction;
    private Dronnie dronnie;
    private Sprite ally;
    private Vector2 origin;
    private Vector2 distance;
    private boolean moving;
    private int moveStep = 1;
    private float distanceX = -190;
    private float distanceY = 0;


    public AllyDrone(Dronnie dronnie, float num, float num2) {

        bodyPic = new Texture(Gdx.files.internal("allyDrone.png"));

        this.dronnie = dronnie;
        this.player = dronnie.getBody();
        body = new Rectangle();
        body.x = player.x;
        body.y = player.y;
        body.width = PICTURE_SIZE;
        body.height = PICTURE_SIZE;
        ally = new Sprite(bodyPic);
        ally.setPosition(player.getX(), player.getY());
        ctor(num, num2);
        moving = true;
    }


    public void move(SpriteBatch batch) {
        if(!moving) {
            return;
        }
        rotate(batch);
    }


    public void rotate(SpriteBatch batch) {

        update2(Gdx.graphics.getDeltaTime());
        ally.draw(batch);

    }

    void ctor(float num, float num2) {
        origin = new Vector2();
        distance = new Vector2(num, num2);
    }


    public void update2(float Delta) {
        origin.set(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2);
        distance.rotate(75 * Delta);
        Vector2 newPos = origin.add(distance);
        ally.setPosition(newPos.x - ally.getWidth() / 2, newPos.y - ally.getHeight() /2);
        //System.out.println(newPos.y - satellite.getHeight() / 2  + "    " + satellite.getY());
        body.x = ally.getX();
        body.y = ally.getY();

        //System.out.println(distance.angle());
        body.x = ally.getX();
        body.y = ally.getY();
    }
    public Vector2 getDistance() {
        return distance;
    }

}

