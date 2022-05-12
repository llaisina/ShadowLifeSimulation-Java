import bagel.util.Point;
/**
 * Represent a Down Direction Board entity which extends from DirectionBoard
 */
public class DownBoard extends DirectionBoard {
    public DownBoard(Point point) { super("res/images/down.png", point);
    }
    @Override
    public <T extends MovingEntities> void changeDirection(T type) {
        type.setxAxis(-1);
        type.setDirection(1);
    }
}