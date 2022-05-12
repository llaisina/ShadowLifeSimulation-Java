import bagel.Font;
import bagel.Input;
import bagel.util.Point;

/**
 * Represent entities that act as an inventory
 */
public abstract class Inventory extends Sprites{
    private int stockCount;
    private final int orderID;
    private static final Font STOCK_FONT = new Font("res/VeraMono.ttf", 18);
    public Inventory(String source, Point point, int orderID){
        super(source, point);
        this.stockCount = 0;
        this.orderID = orderID;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getStockCount() {
        return stockCount;
    }



    /**
     * Adds a stock to the stockpile of fruits
     */
    public void addStock(){
        stockCount++;
    }

    /**
     * Reduce a stock to the stockpile of fruits
     */
    public void reduceStock(){
        stockCount--;
    }
    /**
     * Updates the current stock amount in the stockpile
     * @param input The current mouse/keyboard state
     */
    @Override
    public void update(Input input) {
        STOCK_FONT.drawString(Integer.toString(stockCount), getCoordinate().x, getCoordinate().y);
        super.update(input);
    }
}
