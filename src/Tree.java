import bagel.Input;
import bagel.util.Point;
import bagel.Font;

/**
 * Represent a Tree entity which extends from Sprites
 */
public class Tree extends Sprites {
    private static final Font STOCK_FONT = new Font("res/VeraMono.ttf", 18);
    private int stockCount;
    public Tree(Point point) {
        super("res/images/tree.png", point);
        this.stockCount = 3;
    }

    /**
     * Reduce the stock number of fruits from the tree
     */
    public void reduceStock(){
        if (stockCount >= 1) {
            stockCount--;
        }
    }

    public int getStockCount() {
        return stockCount;
    }

    /**
     * Updates the current amount of fruits in the tree
     * @param input The current mouse/keyboard state
     */
    @Override
    public void update(Input input) {
        STOCK_FONT.drawString(Integer.toString(stockCount), getCoordinate().x, getCoordinate().y);
        super.update(input);
    }
}
