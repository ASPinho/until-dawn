package deltaqueues.dronnie.elements.enemies;

import com.badlogic.gdx.Gdx;
import deltaqueues.dronnie.Directions;
import deltaqueues.dronnie.Utilities;
import deltaqueues.dronnie.elements.AbstractElements;
import deltaqueues.dronnie.elements.allys.Dronnie;
import deltaqueues.dronnie.elements.PlayerChaser;

import static deltaqueues.dronnie.Directions.*;
import static deltaqueues.dronnie.Utilities.DRONE_BACKGROUND_HEIGHT;
import static deltaqueues.dronnie.Utilities.DRONE_BACKGROUND_WIDTH;

public abstract class AbstractEnemy extends AbstractElements implements PlayerChaser {

    protected boolean destroyed = false;
    protected int directionChangeLevel = 8;
    public Directions currentDirection = LEFT;
    protected boolean confused = false;
    //private Dronnie dronnie;
    private int firstMoves = 30;
    private int moves = 0;
    private int turning = 4;
    private int speed = 2;

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }


    // Chase dronnie
    public void moveTowardsPlayer() {

        switch (checkHorizontalDirection()) {
            case LEFT:
                currentDirection = LEFT;
                body.x -= Utilities.ENEMY_SPEED * Gdx.graphics.getDeltaTime();
                break;

            case RIGHT:
                currentDirection = RIGHT;
                body.x += Utilities.ENEMY_SPEED * Gdx.graphics.getDeltaTime();
                break;
        }

        switch (checkVerticalDirection()) {
            case DOWN:
                currentDirection = DOWN;
                body.y -= Utilities.ENEMY_SPEED * Gdx.graphics.getDeltaTime();
                break;
            case UP:
                currentDirection = UP;
                body.y += Utilities.ENEMY_SPEED * Gdx.graphics.getDeltaTime();
        }

    }

    public Directions checkHorizontalDirection() {

        float enemyX = body.x;
        float playerX = player.x;

        int distance = (int) (enemyX - playerX);

        if (distance == 0) {
            return Directions.STAY;
        }

        return distance > 0 ? LEFT : RIGHT;
    }

    public Directions checkVerticalDirection() {

        float enemyY = body.y;
        float playerY = player.y;

        int distance = (int) (enemyY - playerY);

        if (distance == 0) {
            return Directions.STAY;
        }

        return distance > 0 ? DOWN : Directions.UP;
    }


    // Move randomly when dronnie is invisible

    public boolean isHittingWall() {

        switch (currentDirection) {
            case LEFT:
                if (body.x == 0) {
                    return true;
                }
                break;
            case RIGHT:
                if (body.x == DRONE_BACKGROUND_WIDTH - 1) {
                    return true;
                }
                break;
            case UP:
                if (body.x == 0) {
                    return true;
                }
                break;
            case DOWN:
                if (body.y == DRONE_BACKGROUND_HEIGHT - 1) {
                    return true;
                }
        }

        return false;

    }

    public Directions chooseDirection() {

        // Let's move in the same direction by default
        Directions newDirection = currentDirection;

        // Sometimes, we want to change direction...
        if (Math.random() > ((double) directionChangeLevel) / 10) {
            newDirection = Directions.values()[(int) (Math.random() * Directions.values().length)];

            // but we do not want to perform U turns..
            if (newDirection.isOpposite(currentDirection)) {
                return chooseDirection();
            }
        }

        return newDirection;

    }

    public void moveInDirection(Directions direction, float distance) {

        float x = body.x;
        float y = body.y;

        System.out.println(getY());
        System.out.println(getX());

            switch (direction) {

                case UP:
                    moveUp(distance);
                    break;
                case DOWN:
                    moveDown(distance);
                    break;
                case LEFT:
                    moveLeft(distance);
                    break;
                case RIGHT:
                    moveRight(distance);
                    break;
                case STAY:
                    break;
            }

           // float x2 = body.x;
           // float y2 = body.y;

            //body.setPosition(x2 - x, y2 - y);
    }

    public void accelerate(Directions direction, int speed) {

        Directions newDirection = direction;

        // Perform a U turn if we have bumped against the wall
        if (isHittingWall()) {
            newDirection = direction.oppositeDirection();
        }

        // Accelerate in the choosen direction
        this.currentDirection = newDirection;
        for (int i = 0; i < speed; i++) {
            moveInDirection(newDirection, 1);
        }
    }

    public void moveUp(float input) {
        body.y += input;
    }
    public void moveDown(float input) {
        body.y -= input;
    }

    public void moveLeft(float input) {
        body.x -= input;
    }
    public void moveRight(float input) {
        body.x += input;
    }

    public void moveRandomly(Dronnie dronnie) {

        if(!dronnie.getInvisibleMode()) {
            return;
        }

        moves++;

        if (moves < firstMoves  || moves % turning != 0) {
            accelerate(chooseDirection(), speed);
        }

    }
}
