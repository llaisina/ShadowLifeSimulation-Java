import bagel.util.Point;
/**
 * Represent a Stockpile entity which extends from Inventory
 */
public class Cherries extends Inventory {
    public Cherries(Point point, int orderID) {
        super("res/images/cherries.png", point, orderID);
    }
}

