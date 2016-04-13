package ai.gage.mitch.checkers.agent;

/**
 * This class will represent a node in the game tree. It will also contain data so alpha beta pruning can be done
 * Created by gvanderclay on 4/12/16.
 */
public class GameNode {

    // minimum upper bound of possible solutions
    private int alpha;

    // maximum upper bound of possible solutions
    private int beta;
}
