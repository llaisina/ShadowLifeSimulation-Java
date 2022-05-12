import bagel.util.Point;
/**
 * Represent a Right Direction Board entity which extends from DirectionBoard
 */
public class RightBoard extends DirectionBoard {
    public RightBoard(Point point) { super("res/images/right.png", point);
    }
    @Override
    public <T extends MovingEntities> void changeDirection(T type) {
        type.setxAxis(1);
        type.setDirection(1);
    }
}