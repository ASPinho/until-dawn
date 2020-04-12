package deltaqueues.dronnie.elements;

import deltaqueues.dronnie.Directions;

public interface PlayerChaser {

     void moveTowardsPlayer();

     Directions checkHorizontalDirection();

     Directions checkVerticalDirection();

}
