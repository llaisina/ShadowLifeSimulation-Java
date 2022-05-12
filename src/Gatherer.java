import bagel.Input;
import bagel.util.Point;
import java.util.ArrayList;

public class Gatherer extends MovingEntities{
    private long start;
    private long current;
    private final Ticks ticks;
    private boolean isStepping;
    private boolean isCarrying;
   // private Tree tree;
    /**
     * Represent a Gatherer entity which extends from Sprites
     */
    public Gatherer(Point point, Ticks ticks) {
        super("res/images/gatherer.png", point, 1, -1, true, 0,0);
        this.start = 0;
        this.current = 0;
        this.ticks = ticks;
        this.isStepping = false;
        this.isCarrying = false;
    }

    public Gatherer(Point point, Ticks ticks, int xAxis, int direction) {
        super("res/images/gatherer.png", point, xAxis, direction, true,0,0);
        this.start = 0;
        this.current = 0;
        this.ticks = ticks;
        this.isStepping = false;
        this.isCarrying = false;
    }

    public boolean isCarrying() {
        return isCarrying;
    }

    public void setCarrying(boolean carrying) {
        isCarrying = carrying;
    }

    public boolean getIsStepping() {
        return isStepping;
    }

    public void setStepping(boolean stepping) {
        isStepping = stepping;
    }

    /**
     * Update the direction according to the tick rate speed and checks if coordinates collides with a fence
     * @param input The current mouse/keyboard state
     * @param fences The Arraylist containing all locations of the fences
     */
    public void update(Input input, ArrayList<Fence> fences, ArrayList<Pool> pools) {
        current = System.currentTimeMillis();
        if (current - start >= ticks.getTickRate() && isActive()){
            start = current;
            changeCoordinate();
            isStepping = false;
        }
        for (Fence fence:fences){
            if (fence.getCoordinate().equals(getCoordinate())){
                rotateDirection(180);
                changeCoordinate();
                setActive(false);
            }
        }

        for (Pool pool:pools){
            if (pool.getCoordinate().equals(getCoordinate())){
                return;
            }
        }
        super.update(input);
    }
}
