package deltaqueues.dronnie.attacks.passiveAttacks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import deltaqueues.dronnie.attacks.AbstractProjectiles;
import deltaqueues.dronnie.attacks.FireType;
import deltaqueues.dronnie.elements.allys.Dronnie;

import static deltaqueues.dronnie.Utilities.PICTURE_SIZE;

public class Satellite extends AbstractProjectiles {

    private Sprite satellite;
    private Satellite brother;
    private float brotherCount;
    private Dronnie dronnie;
    private int satelliteNum = satelliteNumber;
    private static int satelliteNumber = 0;
    private float countX = 0;
    private float countY = 0;
    // private float distance = 60;
    private FireType fireType;
    private boolean firtsSpin = true;
    private int spinByOrder;

    private int moveStep;
    private int previousMoveStep;
    private float distanceX = -50;
    private float distanceY = 0;
    private boolean moving = false;
    private Vector2 origin;
    private Vector2 distance;


    public Satellite(Dronnie dronnie, float num, float num2) {
        fireType = FireType.PLAYER_FIRE;

        this.player = dronnie.getBody();
        this.dronnie = dronnie;

        body = new Rectangle();
        body.x = player.x;
        body.y = player.y;
        body.width = PICTURE_SIZE;
        body.height = PICTURE_SIZE;

        bodyPic = new Texture(Gdx.files.internal("plasmaBall6.png"));

        TextureRegion text1 = new TextureRegion(bodyPic);
        satellite = new Sprite(bodyPic);

        //satellite.setRegionWidth(50);
        ctor(num, num2);
        satelliteNumber++;
        satelliteNum = satelliteNumber;
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
        satellite.draw(batch);

    }

    void ctor(float num, float num2) {
        origin = new Vector2();
        distance = new Vector2(num, num2);
    }

    public void update(float Delta) {
        //if()

        origin.set(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2);
        distance.rotate(500 * Delta);
        Vector2 newPos = origin.add(distance);
        satellite.setPosition(newPos.x - satellite.getWidth() / 2, newPos.y - satellite.getHeight() / 2);
       // System.out.println(newPos.y - satellite.getHeight() / 2  + "    " + player.getY());
        body.x = satellite.getX();
        body.y = satellite.getY();
    }

    public void update2(float Delta) {
        origin.set(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2);
        distance.rotate(360 * Delta);
        Vector2 newPos = origin.add(distance);
        satellite.setPosition(newPos.x - satellite.getWidth() / 2, newPos.y - satellite.getHeight() /2);
        //System.out.println(newPos.y - satellite.getHeight() / 2  + "    " + satellite.getY());
        body.x = satellite.getX();
        body.y = satellite.getY();

        //System.out.println(distance.angle());
        body.x = satellite.getX();
        body.y = satellite.getY();
    }
    public Vector2 getDistance() {
        return distance;
    }

        /*
        float xInput = player.getX();
        float yInput = player.getY();

        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(yInput - body.y, xInput - body.x);

        if (angle < 0) {
            angle += 360;
        }
        satellite.setRotation(angle - 90);

        satellite.draw(batch);
        */
}






    /*
    public void move() {
        if (!moving) {
            checkMoveStep();
        }

        switch (moveStep) {
            case 1:
                firstMove();
                break;
            case 2:
                secondMove();
                break;
            case 3:
                thirdMove();
                break;
            case 4:
                fourthMove();
                break;
        }
    }


    public void firstMove() {
        if (body.x >= player.getX() && body.y > player.getY()) {
            moveStep = 2;
            secondMove();
        }
        body.x = player.getX() + (distanceX += 1);
        body.y = player.getY() + (distanceY += 1);
        previousMoveStep = 4;
    }

    public void secondMove() {
        if (body.y <= player.getY() && body.x > player.getX()) {
            moveStep = 3;
            thirdMove();
        }
        body.x = player.getX() + (distanceX += 1);
        body.y = player.getY() + (distanceY -= 1);
        previousMoveStep = 1;
    }

    public void thirdMove() {
        if (body.x <= player.getX() && body.y < player.getY()) {
            moveStep = 4;
            fourthMove();
        }
        body.x = player.getX() + (distanceX -= 1);
        body.y = player.getY() + (distanceY -= 1);
        previousMoveStep = 2;
    }

    public void fourthMove() {
        if (body.y >= player.getY() && body.x < player.getX()) {
            firstMove();
            moveStep = 1;
        }
        body.x = player.getX() + (distanceX -= 1);
        body.y = player.getY() + (distanceY += 1);
        previousMoveStep = 3;
    }


    public void checkMoveStep() {
        if (satelliteNum == 1) {
            moveStep = 1;
            moving = true;
            return;
        }

        if (satelliteNum == 2) {
            if (brother.getSatelliteNumber() == 1) {
                if (brother.getMoveStep() == 3 && brother.getPreviousMoveStep() == 2) {
                    moveStep = 1;
                    moving = true;
                    return;
                }
            }
        }

        if (satelliteNum == 3) {
            if(brother.getSatelliteNumber() == 2) {
                if(brother.getMoveStep() == 2 && brother.getPreviousMoveStep() == 1) {
                    moveStep = 1;
                    moving = true;
                    return;
                }
            }
        }

        if (satelliteNum == 4) {
            if(brother.getSatelliteNumber() == 3) {
                if(brother.getMoveStep() == 1 && brother.getPreviousMoveStep() ==4) {
                    moveStep = 3;
                    moving = true;
                    return;
                }
            }
        }
    }

    public int getSatelliteNumber() {
        return satelliteNum;
    }

    public int getMoveStep() {
        return moveStep;
    }

    public int getPreviousMoveStep() {
        return previousMoveStep;
    }

*/
/*
    public Satellite(Dronnie dronnie, Satellite brother) {
        fireType = FireType.PLAYER_FIRE;

        this.player = dronnie.getBody();
        this.dronnie = dronnie;

        body = new Rectangle();
        body.x = player.x;
        body.y = player.y;
        body.width = 20;
        body.height = 20;

        bodyPic = new Texture(Gdx.files.internal("plasmaBall6.png"));

        satellite = new Sprite(bodyPic);

        this.brother = brother;

        satelliteNumber++;
        satelliteNum = satelliteNumber;
    }

    public void draw(SpriteBatch batch) {
        if (satelliteNum == 1) {

            batch.draw(satellite, player.getX() - 90, player.getY()-50, satellite.getRegionWidth() + 50, satellite.getRegionHeight(), satellite.getRegionWidth(), satellite.getRegionHeight(), 1f, 1f, countX, false);
            if (countX <= 0.0f) {
                countX = 360.0f;
            } else if (dronnie.getSpeed() > 2) {
                countX -= 15;
            } else {
                countX -= 5;
            }
        }


    }


    public void drawBro(SpriteBatch batch, Satellite brother, int counter) {
        brotherCount = brother.getCount();


        if (satelliteNum == 2 && firtsSpin) {

            if (brotherCount == counter) {
                countX = 360;
                batch.draw(satellite, player.getX() - 90, player.getY()-50, satellite.getRegionWidth() + 50, satellite.getRegionHeight(), satellite.getRegionWidth(), satellite.getRegionHeight(), 1f, 1f, countX, false);
                firtsSpin = false;

                if (countX <= 0.0f) {
                    countX = 360.0f;
                } else if (dronnie.getSpeed() > 2) {
                    countX -= 15;
                } else {
                    countX -= 5;
                }
                return;
            }
        }

        if (satelliteNum == 2 && !firtsSpin) {

            batch.draw(satellite, player.getX() - 90, player.getY()-50, satellite.getRegionWidth() + 50, satellite.getRegionHeight(), satellite.getRegionWidth(), satellite.getRegionHeight(), 1f, 1f, countX, false);

            if (countX <= 0.0f) {
                countX = 360.0f;
            } else if (dronnie.getSpeed() > 2) {
                countX -= 15;
            } else {
                countX -= 5;
            }
        }
    }


    public void drawFinalBro(SpriteBatch batch, Satellite brother, int counter) {
        brotherCount = brother.getCount();



        if (satelliteNum == 3 && firtsSpin) {

            if (brotherCount == counter) {

                countX = 360;
                batch.draw(satellite, player.getX() - 90, player.getY()-50, satellite.getRegionWidth() + 50, satellite.getRegionHeight(), satellite.getRegionWidth(), satellite.getRegionHeight(), 1f, 1f, countX, false);
                firtsSpin = false;

                if (countX <= 0.0f) {
                    countX = 360.0f;
                } else if (dronnie.getSpeed() > 2) {
                    countX -= 15;
                } else {
                    countX -= 5;
                }

                return;
            }
        }

        if (satelliteNum == 3 && !firtsSpin) {

            batch.draw(satellite, player.getX() - 90, player.getY() -50, satellite.getRegionWidth() + 50, satellite.getRegionHeight(), satellite.getRegionWidth(), satellite.getRegionHeight(), 1f, 1f, countX, false);

            if (countX <= 0.0f) {
                countX = 360.0f;
            } else if (dronnie.getSpeed() > 2) {
                countX -= 15;
            } else {
                countX -= 5;
            }
        }
    }

    public void drawFinalBro2(SpriteBatch batch,Satellite brother,int counter) {
        brotherCount = brother.getCount();



        if (satelliteNum == 4 && firtsSpin) {

            if (brotherCount == counter) {

                countX = 360;
                batch.draw(satellite, player.getX() - 90, player.getY()-50, satellite.getRegionWidth() + 50, satellite.getRegionHeight(), satellite.getRegionWidth(), satellite.getRegionHeight(), 1f, 1f, countX, false);
                firtsSpin = false;

                if (countX <= 0.0f) {
                    countX = 360.0f;
                } else if (dronnie.getSpeed() > 2) {
                    countX -= 15;
                } else {
                    countX -= 5;
                }

                return;
            }
        }

        if (satelliteNum == 4 && !firtsSpin) {

            batch.draw(satellite, player.getX() - 90, player.getY()-50, satellite.getRegionWidth() + 50, satellite.getRegionHeight(), satellite.getRegionWidth(), satellite.getRegionHeight(), 1f, 1f, countX, false);

            if (countX <= 0.0f) {
                countX = 360.0f;
            } else if (dronnie.getSpeed() > 2) {
                countX -= 15;
            } else {
                countX -= 5;
            }
        }
    }

    public Sprite getSprite() {
        return satellite;
    }

    public float getCount() {
        return countX;
    }

}
        */