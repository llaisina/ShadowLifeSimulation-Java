import bagel.Image;
import bagel.Input;
import bagel.util.Point;


public abstract class Sprites {

    private final Image image;
    private Point coordinate;


    /**
     * Represents the game entities
     */
    public Sprites(String source, Point point) {
        this.image = new Image(source);
        this.coordinate = point;
    }

    public Point getCoordinate() {
        return coordinate;
    }


    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Update method for entities
     * @param input The current mouse/keyboard state
     */
    public void update(Input input){
        image.drawFromTopLeft(getCoordinate().x, getCoordinate().y);
    }
}
