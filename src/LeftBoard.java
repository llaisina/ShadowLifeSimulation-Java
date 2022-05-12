import bagel.util.Point;
/**
 * Represent a Left Direction Board entity which extends from DirectionBoard
 */
public class LeftBoard extends DirectionBoard {
    public LeftBoard(Point point) { super("res/images/left.png", point);
    }

    @Override
    public <T extends MovingEntities> void changeDirection(T type) {
        type.setxAxis(1);
        type.setDirection(-1);
    }
}