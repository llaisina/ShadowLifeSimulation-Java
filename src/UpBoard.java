import bagel.util.Point;
/**
 * Represent an Upward Direction Board entity which extends from DirectionBoard
 */
public class UpBoard extends DirectionBoard {
    public UpBoard(Point point) { super("res/images/up.png", point);
    }
    @Override
    public <T extends MovingEntities> void changeDirection(T type) {
        type.setxAxis(-1);
        type.setDirection(-1);
    }
}