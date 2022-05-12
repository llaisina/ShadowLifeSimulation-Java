import bagel.util.Point;
/**
 * Represent a Hoard entity which extends from Inventory
 */
public class Hoard extends Inventory {
    public Hoard(Point point, int orderID) {
        super("res/images/hoard.png", point, orderID);
    }
}
