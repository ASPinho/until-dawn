package deltaqueues.dronnie;

public enum Directions {
    UP,
    DOWN,
    RIGHT,
    LEFT,
    STAY;


    public boolean isOpposite(Directions dir) {
        return dir.equals(oppositeDirection());
    }


    public Directions oppositeDirection() {

        Directions opposite = null;

        switch (this) {
            case UP:
                opposite = Directions.DOWN;
                break;
            case DOWN:
                opposite = Directions.UP;
                break;
            case LEFT:
                opposite = Directions.RIGHT;
                break;
            case RIGHT:
                opposite = Directions.LEFT;
                break;
            case STAY:
                opposite = Directions.STAY;
        }

        return opposite;
    }

}
