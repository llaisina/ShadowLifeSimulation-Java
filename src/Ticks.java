/**
 * Represent the tick rate and the maximum tick on the game
 */
public class Ticks {
    private final int tickRate;
    private final int maxTick;
    private int tickCount;
    private long current;
    private long start;

    public Ticks(int tickRate, int maxTick) {
        this.tickRate = tickRate;
        this.maxTick = maxTick;
        this.tickCount = 0;
        this.current = 0;
        this.tickCount = 0;
    }
    public int getTickRate(){return tickRate;}
    public int getMaxTick() {
        return maxTick;
    }

    public int getTickCount() {
        return tickCount;
    }

    /**
     * Calculate the amount of ticks that have passed according to the tick rate
     */
    public void timePassed(){
        current = System.currentTimeMillis();
        if (current - start >= tickRate){
            start = current;
            tickCount++;
        }
    }

    @Override
    public String toString() {
        return tickCount +
                " ticks";
    }
}
