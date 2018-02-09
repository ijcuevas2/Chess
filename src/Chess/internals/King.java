package Chess.internals;

/**
 * Created by ismael on 2/5/17.
 */

public class King extends Piece {
    /* Constructor for the King*/
    public King(int turn){
        super(turn);
    }

    /* Movement for the King. */
    public boolean movement(int source_x, int source_y, int dest_x, int dest_y){
        return true;
    }
}
