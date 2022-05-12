import bagel.*;
import bagel.util.Point;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.ListIterator;

public class ShadowLife extends AbstractGame {
    private final Image background;
    private final ArrayList<Gatherer> gatherer;
    private final ArrayList<Tree> tree;
    private final ArrayList<Cherries> cherries;
    private final ArrayList<Fence> fences;
    private final ArrayList<Thief> thieves;
    private final ArrayList<GoldTree> goldTrees;
    private final ArrayList<Hoard> hoards;
    private final ArrayList<Pool> pools;
    private final ArrayList<UpBoard> upBoards;
    private final ArrayList<RightBoard> rightBoards;
    private final ArrayList<LeftBoard> leftBoards;
    private final ArrayList<DownBoard> downBoards;
    private final ArrayList<Pad> pads;
    private final Ticks tick;

    /**
     * Create a new ShadowLife instance
     */
    public ShadowLife(int tickRate, int tickMax, String worldFile) {
        super(1024,768, "ShadowLife");
        this.background = new Image("res/images/background.png");
        this.tree = new ArrayList<>();
        this.gatherer = new ArrayList<>();
        this.cherries = new ArrayList<>();
        this.fences = new ArrayList<>();
        this.thieves = new ArrayList<>();
        this.goldTrees = new ArrayList<>();
        this.hoards = new ArrayList<>();
        this.pools = new ArrayList<>();
        this.upBoards = new ArrayList<>();
        this.rightBoards = new ArrayList<>();
        this.leftBoards = new ArrayList<>();
        this.downBoards = new ArrayList<>();
        this.pads = new ArrayList<>();
        this.tick = new Ticks(tickRate, tickMax);
        readCsv(worldFile);
    }

    /**
     * Entry point for the game
     */
    public static void main(String[] args) {
        ShadowLife game = new ShadowLife(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2]);
        game.run();
    }


    /**
     * Read coordinates from CSV file and insert the values to the corresponding classes
     */
    public void readCsv(String worldFile){
        int i = 1;
        int inventoryOrderID = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(worldFile))) {
            String text;
            String[] columns;
            while ((text = br.readLine()) != null) {
                try {
                    columns = text.split(",");
                    Point coordinate = new Point(Integer.parseInt(columns[1]), Integer.parseInt(columns[2]));
                    switch (columns[0]) {
                        case "Tree":
                            tree.add(new Tree(coordinate));
                            break;
                        case "Gatherer":
                            gatherer.add(new Gatherer(coordinate, tick));
                            break;
                        case "Fence":
                            fences.add(new Fence(coordinate));
                            break;
                        case "Thief":
                            thieves.add(new Thief(coordinate, tick));
                            break;
                        case "Pool":
                            pools.add(new Pool(coordinate));
                            break;
                        case "Hoard":
                            hoards.add(new Hoard(coordinate, inventoryOrderID++));
                            break;
                        case "GoldenTree":
                            goldTrees.add(new GoldTree(coordinate));
                            break;
                        case "Stockpile":
                            cherries.add(new Cherries(coordinate, inventoryOrderID++));
                            break;
                        case "Pad":
                            pads.add(new Pad(coordinate));
                            break;
                        case "SignRight":
                            rightBoards.add(new RightBoard(coordinate));
                            break;
                        case "SignUp":
                            upBoards.add(new UpBoard(coordinate));
                            break;
                        case "SignLeft":
                            leftBoards.add(new LeftBoard(coordinate));
                            break;
                        case "SignDown":
                            downBoards.add(new DownBoard(coordinate));
                            break;
                        default :
                            System.out.println("error in file \"" + worldFile + "\" at line "+ i);
                            System.exit(-1);
                    }
                }
                catch (NullPointerException| ArrayIndexOutOfBoundsException | NumberFormatException e){
                    System.out.println("error in file \"" + worldFile + "\" at line "+ i);
                    System.exit(-1);
                }
            }
        }

        catch (Exception e) {
            System.out.println("error: file \"" + worldFile + "\" not found");
            System.exit(-1);
        }
    }

    /**
     * TimedOut function when tickCount goes over the allowed maximum ticks.
     */
    public void timedOut(){
        Window.close();
        System.out.println("Timed out\n");
        System.exit(-1);
    }

    /**
     * This function prints each fruit stock of the inventory sprite in the order of csv input
     */
    public void printTotalStock(){
        int curOrder = 0;
        Window.close();
        System.out.println(tick.toString());
        if (cherries.size() > 0 || hoards.size() > 0) {
            if (cherries.size() == 0) {
                for (Hoard curHoard : hoards) {
                    if (curHoard.getOrderID() == curOrder) {
                        System.out.println(curHoard.getStockCount());
                    }
                    curOrder++;
                }
            }
            else if (hoards.size() == 0){
                for (Cherries curCherries: cherries){
                    if (curCherries.getOrderID() == curOrder) {
                        System.out.println(curCherries.getStockCount());
                    }
                    curOrder++;
                }
            }
            else {
                for (Cherries curCherries : cherries) {
                    if (curCherries.getOrderID() == curOrder) {
                        System.out.println(curCherries.getStockCount());
                        curOrder++;
                    }
                    for (Hoard curHoard : hoards) {
                        if (curHoard.getOrderID() == curOrder) {
                            System.out.println(curHoard.getStockCount());
                            curOrder++;
                        }
                    }
                }
            }
        }
        System.exit(0);
    }

    /**
     * Update the state of the game
     * @param input The current mouse/keyboard state
     */
    @Override
    protected void update(Input input) {
        tick.timePassed();
        background.drawFromTopLeft(0,0);
        for (Tree curTree:tree) {
            for (Gatherer curGatherer:gatherer){
                if (!curGatherer.getIsStepping() && !curGatherer.isCarrying()){
                    if (curTree.getCoordinate().equals(curGatherer.getCoordinate()) && curTree.getStockCount() != 0) {
                        curGatherer.rotateDirection(180);
                        curGatherer.setStepping(true);
                        curGatherer.setCarrying(true);
                        curTree.reduceStock();
                        //break;
                    }
                }
                for (Thief curThieves:thieves) {
                    if (!curThieves.getIsStepping()) {
                        if (curTree.getCoordinate().equals(curThieves.getCoordinate()) && tick.getTickCount() > 1) {
                            if (curTree.getStockCount()>0) {
                                curTree.reduceStock();
                                curThieves.setCarrying(true);
                                curThieves.setStepping(true);
                            }
                        }
                    }
                }
            }
            curTree.update(input);
        }
        for (GoldTree curGtree:goldTrees) {
            for (Thief curThieves:thieves) {
                if (!curThieves.getIsStepping()) {
                    if (curGtree.getCoordinate().equals(curThieves.getCoordinate()) && tick.getTickCount() > 1) {
                        curThieves.setCarrying(true);
                        curThieves.setStepping(true);
                    }
                }
            }
            curGtree.update(input);
        }
        for (Pad curPad:pads) {
            for (Thief curThieves:thieves) {
                if (!curThieves.getIsStepping()) {
                    if (curPad.getCoordinate().equals(curThieves.getCoordinate()) && tick.getTickCount() > 1) {
                        curThieves.setConsuming(true);
                        curThieves.setStepping(true);
                    }
                }
            }
            curPad.update(input);
        }
        for (Fence curFence:fences) {
            curFence.update(input);
        }
        for (Cherries curCherries:cherries) {
            for (Gatherer curGatherer:gatherer){
                if (!curGatherer.getIsStepping()){
                    if (curCherries.getCoordinate().equals(curGatherer.getCoordinate()) && tick.getTickCount() > 1) {
                        if (curGatherer.isCarrying()) {
                            curGatherer.setCarrying(false);
                            curCherries.addStock();
                        }
                        curGatherer.setStepping(true);
                        curGatherer.rotateDirection(180);
                    }
                }
            }
            for (Thief curThieves:thieves){
                if (!curThieves.getIsStepping()){
                    if (curCherries.getCoordinate().equals(curThieves.getCoordinate()) && tick.getTickCount() > 1) {
                        if (!curThieves.isCarrying()) {
                            if (curCherries.getStockCount() > 0) {
                                curThieves.setCarrying(true);
                                curThieves.setConsuming(false);
                                curCherries.reduceStock();
                                curThieves.rotateDirection(90);
                            }
                        }
                        else {
                           curThieves.rotateDirection(90);
                        }
                        curThieves.setStepping(true);
                    }
                }
            }
            curCherries.update(input);
        }
        for (Hoard curHoard:hoards) {  
            for (Gatherer curGatherer:gatherer){
                if (!curGatherer.getIsStepping()){
                    if (curHoard.getCoordinate().equals(curGatherer.getCoordinate()) && tick.getTickCount() > 1) {
                        if (curGatherer.isCarrying()) {
                            curGatherer.setCarrying(false);
                            curHoard.addStock();
                        }
                        curGatherer.setStepping(true);
                        curGatherer.rotateDirection(180);
                    }
                }
            }
            for (Thief curThieves:thieves) {
                if (!curThieves.getIsStepping()) {
                    if (curHoard.getCoordinate().equals(curThieves.getCoordinate()) && tick.getTickCount() > 1) {
                        if (curThieves.isConsuming()) {
                            curThieves.setConsuming(false);
                            if (!curThieves.isCarrying()) {
                                if (curHoard.getStockCount()>0) {
                                    curThieves.setCarrying(true);
                                    curHoard.reduceStock();
                                }
                                else{curThieves.rotateDirection(90);}
                            }
                        }
                        else if (curThieves.isCarrying()){
                            curThieves.setCarrying(false);
                            curHoard.addStock();
                            curThieves.rotateDirection(90);
                        }
                        curThieves.setStepping(true);
                    }
                }
            }
            curHoard.update(input);
        }
        for (UpBoard curUpboard:upBoards) {
            for (Gatherer curGatherer:gatherer){
                if (!curGatherer.getIsStepping()) {
                    if (curUpboard.getCoordinate().equals(curGatherer.getCoordinate())) {
                        curGatherer.setStepping(true);
                        curUpboard.changeDirection(curGatherer);
                    }
                }
            }
            for (Thief curThieves:thieves){
                if (!curThieves.getIsStepping()) {
                    if (curUpboard.getCoordinate().equals(curThieves.getCoordinate())) {
                        curThieves.setStepping(true);
                        curUpboard.changeDirection(curThieves);
                    }
                }
            }
            curUpboard.update(input);
        }
        for (RightBoard curRightboard:rightBoards) {
            for (Gatherer curGatherer:gatherer){
                if (!curGatherer.getIsStepping()) {
                    if (curRightboard.getCoordinate().equals(curGatherer.getCoordinate())) {
                        curGatherer.setStepping(true);
                        curRightboard.changeDirection(curGatherer);
                    }
                }
            }
            for (Thief curThieves:thieves){
                if (!curThieves.getIsStepping()) {
                    if (curRightboard.getCoordinate().equals(curThieves.getCoordinate())) {
                        curThieves.setStepping(true);
                        curRightboard.changeDirection(curThieves);
                    }
                }
            }
            curRightboard.update(input);
        }
        for (LeftBoard curLeftboard:leftBoards) {
            for (Gatherer curGatherer:gatherer){
                if (!curGatherer.getIsStepping()) {
                    if (curLeftboard.getCoordinate().equals(curGatherer.getCoordinate())) {
                        curGatherer.setStepping(true);
                        curLeftboard.changeDirection(curGatherer);
                    }
                }
            }
            for (Thief curThieves:thieves){
                if (!curThieves.getIsStepping()) {
                    if (curLeftboard.getCoordinate().equals(curThieves.getCoordinate())) {
                        curThieves.setStepping(true);
                        curLeftboard.changeDirection(curThieves);
                    }
                }
            }
            curLeftboard.update(input);
        }
        for (DownBoard curDownboard:downBoards) {
            for (Gatherer curGatherer:gatherer){
                if (!curGatherer.getIsStepping()) {
                    if (curDownboard.getCoordinate().equals(curGatherer.getCoordinate())) {
                        curGatherer.setStepping(true);
                        curDownboard.changeDirection(curGatherer);
                    }
                }
            }
            for (Thief curThieves:thieves){
                if (!curThieves.getIsStepping()) {
                    if (curDownboard.getCoordinate().equals(curThieves.getCoordinate())) {
                        curThieves.setStepping(true);
                        curDownboard.changeDirection(curThieves);
                    }
                }
            }
            curDownboard.update(input);
        }
        ListIterator<Gatherer> gathererIterator = gatherer.listIterator();
        ListIterator<Thief> thiefIterator = thieves.listIterator();
        for (Pool curPools:pools) {
            while (gathererIterator.hasNext()){
                Gatherer curGatherer = gathererIterator.next();
                if (!curGatherer.getIsStepping()) {
                    if (curPools.getCoordinate().equals(curGatherer.getCoordinate())) {
                        gathererIterator.remove();
                        gathererIterator.add(new Gatherer(curGatherer.mitosisCoordinate(90),
                                tick, curGatherer.getNewXis(), curGatherer.getNewDir()));
                        gathererIterator.add(new Gatherer(curGatherer.mitosisCoordinate(270),
                                tick, curGatherer.getNewXis(), curGatherer.getNewDir()));
                    }
                }
            }
            while (thiefIterator.hasNext()){
                Thief curThief = thiefIterator.next();
                if (!curThief.getIsStepping()) {
                    if (curPools.getCoordinate().equals(curThief.getCoordinate())) {
                        gathererIterator.remove();
                        gathererIterator.add(new Gatherer(curThief.mitosisCoordinate(90),
                                tick, curThief.getNewXis(), curThief.getNewDir()));
                        gathererIterator.add(new Gatherer(curThief.mitosisCoordinate(270),
                                tick, curThief.getNewXis(), curThief.getNewDir()));
                    }
                }
            }
            curPools.update(input);
        }
        // Check gatherer's interaction with other entities
        boolean allGathererActive = true;
        for (Gatherer curGatherer:gatherer) {
            allGathererActive = ((curGatherer.isActive() == false) ? false : true);
            for (Thief curThieves:thieves){
                if (!curThieves.getIsStepping()) {
                    if (curGatherer.getCoordinate().equals(curThieves.getCoordinate())) {
                        curThieves.setStepping(true);
                        curThieves.rotateDirection(270);
                    }
                }
            }
            curGatherer.update(input, fences, pools);
        }
        boolean allThiefActive = true;
        for(Thief curThief:thieves) {
            allThiefActive = ((curThief.isActive() == false) ? false : true);
            curThief.update(input, fences, pools);
        }
        if (tick.getTickCount() > tick.getMaxTick()){
            timedOut();
        }
        if ((gatherer.size() > 0)&&(thieves.size() > 0)) {
            if ((!allGathererActive) && (!allThiefActive)) {
                printTotalStock();
            }
        }
        else if ((!allGathererActive) || (!allThiefActive)){
            printTotalStock();
        }
    }
}
