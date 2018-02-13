package Chess.internals;

/**
 * Created by ismael on 2/15/17.
 */
public class EvilKermit extends Piece{
    public EvilKermit(){
        super(BoardManagement.currentTurn());
    }

    @Override
    public boolean movement(int source_x, int source_y, int dest_x, int dest_y) {
        return true;
    }
}
