import bagel.util.Point;

/**
 * Represent sprites with dynamic movements and positions
 */
public abstract class MovingEntities extends Sprites{
    private int xAxis;
    private int direction;
    private int newXis;
    private int newDir;
    private boolean isActive;

    public MovingEntities(String source, Point point, int xAxis,
                          int direction, boolean isActive, int newXis, int newDir){
        super(source, point);
        this.xAxis = xAxis;
        this.direction = direction;
        this.newXis = newXis;
        this.newDir = newDir;
        this.isActive = isActive;
    }
    public int getNewXis() {
        return newXis;
    }

    public int getNewDir() {
        return newDir;
    }

    public void setxAxis(int xAxis) {
        this.xAxis = xAxis;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
    /**
     * Rotate the movement direction of moving entities
     * @param degree the angle of rotation
     */

    public void rotateDirection(int degree){
        if (degree == 180) {
            if (direction == 1) {
                direction = -1;
            }
            else if (direction == -1) {
                direction = 1;
            }
        }
        else if (degree == 90) {
            if (xAxis == 1 && direction == 1) {
                xAxis = -1;
                direction = 1;
            }
            else if (xAxis == -1 && direction == -1){
                xAxis = 1;
                direction = 1;
            }
            else if (xAxis == 1 && direction == -1){
                xAxis = -1;
                direction = -1;
            }
            else {
                xAxis = 1;
                direction = -1;
            }
        }
        else if (degree == 270) {
            if (xAxis==1 && direction == 1){
                xAxis = -1;
                direction = -1;
            }
            else if (xAxis== -1 && direction == 1){
                xAxis = 1;
                direction = -1;
            }
            else if (xAxis== 1 && direction == -1){
                xAxis = -1;
                direction = 1;
            }
            else {
                xAxis = 1;
                direction = 1;
            }
        }
    }
    /**
     * Change the coordinate of movement of the Sprite;
     */
    public void changeCoordinate(){
        Point point;
        if (xAxis == 1){
            point = new Point(getCoordinate().x + 64 * direction, getCoordinate().y);
        }
        else {
            point = new Point(getCoordinate().x, getCoordinate().y + 64 * direction);
        }
        setCoordinate(point);
    }

    /**
     * This functions check the direction and axis of moving entities that interact with mitosis pool.
     * @param degree the angle in which the clone of the moving entities is supposed to go (either 90 or 270 degree).
     * @return A point type coordinate of the cloned moving entity.
     */
    public Point mitosisCoordinate(int degree){
        if (degree == 90) {
            if (xAxis == 1 && direction == 1) {
                newXis = -1;
                newDir = 1;
            }
            else if (xAxis == -1 && direction == -1){
                newXis = 1;
                newDir = 1;
            }
            else if (xAxis == 1 && direction == -1){
                newXis = -1;
                newDir = -1;
            }
            else {
                newXis = 1;
                newDir = -1;
            }
        }
        else if (degree == 270) {
            if (xAxis==1 && direction == 1){
                newXis = -1;
                newDir = -1;
            }
            else if (xAxis== -1 && direction == 1){
                newXis = 1;
                newDir = -1;
            }
            else if (xAxis== 1 && direction == -1){
                newXis = -1;
                newDir = 1;
            }
            else {
                newXis = 1;
                newDir = 1;
            }
        }
        Point point;
        if (newXis == 1){
            point = new Point(getCoordinate().x + 64 * newDir, getCoordinate().y);
        }
        else {
            point = new Point(getCoordinate().x, getCoordinate().y + 64 * newDir);
        }
        return point;
    }
}

