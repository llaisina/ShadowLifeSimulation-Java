import bagel.util.Point;

/**
 * Represent Direction Board entities which extends from Sprites
 */
public abstract class DirectionBoard extends Sprites{
    public DirectionBoard(String source, Point point){
        super(source,point);
    }
    public <T extends MovingEntities>void changeDirection(T type){
        type.setxAxis(1);
        type.setDirection(1);
    }
}