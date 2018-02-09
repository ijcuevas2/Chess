package Chess.internals;

/**
 * Created by Ismael.
 */
public class Empty extends Piece{
    public Empty(){
       this.name = "   ";
    }

    // public Empty(int turn){
    //     super(turn);
    // }

    /**
     * @return: This function will always return false.
     */
    @Override
    public boolean movement(int source_x, int source_y, int dest_x, int dest_y){
        return false;
    }
}
