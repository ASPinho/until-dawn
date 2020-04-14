package deltaqueues.dronnie.elements.enemies;

import com.badlogic.gdx.Gdx;
import deltaqueues.dronnie.Directions;
import deltaqueues.dronnie.Utilities;
import deltaqueues.dronnie.elements.AbstractElements;
import deltaqueues.dronnie.elements.PlayerChaser;

public abstract class AbstractEnemy extends AbstractElements implements PlayerChaser {

    protected boolean distroyed = false;

    public boolean isDistroyed() {
        return distroyed;
    }

    public void setDistroyed(boolean distroyed) {
        this.distroyed = distroyed;
    }

    public void moveTowardsPlayer(){

        switch (checkHorizontalDirection()){
            case LEFT:
                body.x -= Utilities.ENEMY_SPEED * Gdx.graphics.getDeltaTime();
                break;

            case RIGHT:
                body.x += Utilities.ENEMY_SPEED * Gdx.graphics.getDeltaTime();
                break;
        }

        switch (checkVerticalDirection()){
            case DOWN:
                body.y -= Utilities.ENEMY_SPEED * Gdx.graphics.getDeltaTime();
                break;
            case UP:
                body.y += Utilities.ENEMY_SPEED * Gdx.graphics.getDeltaTime();
        }

    }

    public Directions checkHorizontalDirection(){

        float enemyX = body.x;
        float playerX = player.x;

        int distance = (int) (enemyX - playerX);

        if (distance == 0){
            return Directions.STAY;
        }

        return distance > 0 ? Directions.LEFT : Directions.RIGHT;
    }

    public Directions checkVerticalDirection(){

        float enemyY = body.y;
        float playerY = player.y;

        int distance = (int) (enemyY - playerY);

        if (distance == 0) {
            return Directions.STAY;
        }

        return distance > 0 ? Directions.DOWN : Directions.UP;
    }

}
