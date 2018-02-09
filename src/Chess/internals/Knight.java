package Chess.internals;

/**
 * Created by ismael on 2/7/17.
 */
public class Knight extends Piece {
    public Knight(int turn){
        super(turn);
    }
    @Override
    public boolean movement(int source_x, int source_y, int dest_x, int dest_y){
        return true;
    }
}
