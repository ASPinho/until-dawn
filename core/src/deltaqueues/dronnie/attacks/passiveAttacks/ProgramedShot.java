package deltaqueues.dronnie.attacks.passiveAttacks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import deltaqueues.dronnie.attacks.AbstractProjectiles;
import deltaqueues.dronnie.attacks.FireType;
import deltaqueues.dronnie.elements.enemies.Boss;

import static deltaqueues.dronnie.Utilities.PICTURE_SIZE;

public class ProgramedShot extends AbstractProjectiles {


    private FireType fireType;
    private int serialNum = serialNumber;
    private static int serialNumber = 1;
    private Sprite progShot;
    private float originX;
    private float originY;




    public ProgramedShot(Rectangle player) {

        fireType = FireType.PLAYER_FIRE;


        serialNumber +=1;
        updateSerialNumber();

        this.player = player;

        body = new Rectangle();
        body.x = player.x;
        body.y = player.y;
        originX = body.x;
        originY = body.y;
        body.width = 20;
        body.height = 20;

        bodyPic = new Texture(Gdx.files.internal("programedShot.png"));
        progShot = new Sprite(bodyPic);
    }



    public FireType getFireType() {
        return fireType;
    }


    public void updateSerialNumber() {
        if (serialNumber > 8) {
            serialNumber = 1;
        }
    }

    public void fire(SpriteBatch batch) {

        switch (serialNum) {
            case 1:
                moveStraightUp();
                break;
            case 2:
                moveStraightDown();
                break;
            case 3:
                moveStraightLeft();
                break;
            case 4:
                moveStraightRight();
                break;
            case 5:
                moveDiagonaltUR();
                break;
            case 6:
                moveDiagonaltUL();
                break;
            case 7:
                moveDiagonalDL();
                break;
            case 8:
                moveDiagonalDR();
                break;
        }
        rotate();
        progShot.draw(batch);
    }

    public void moveStraightRight() {
        body.x += 20;
    }

    public void moveStraightLeft() {
        body.x-=20;
    }

    public void moveStraightDown() {
        body.y-=20;
    }

    public void moveStraightUp() {
        body.y+=20;
    }

    public void moveDiagonaltUR() {
        body.x+=20;
        body.y+=20;
    }

    public void moveDiagonaltUL() {
        body.x-=20;
        body.y+=20;
    }

    public void moveDiagonalDR() {
        body.x+=20;
        body.y-=20;
    }

    public void moveDiagonalDL() {
        body.y-=20;
        body.x-=20;
    }

    public static int getSerialNumber() {
        return serialNumber;
    }


    public void rotate() {

        progShot.setPosition(body.x - PICTURE_SIZE*2 -50, body.y - PICTURE_SIZE*2-50);

        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(originY - body.y, originX - body.x);

        if (angle < 0) {
            angle += 360;
        }
        progShot.setRotation(angle+90);

        //progShot.draw(batch);
    }

}
