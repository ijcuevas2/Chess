package Chess.internals;

/**
 * Created by ismael on 2/7/17.
 */

/**
 * Class for creating the Bishop.
 */
public class Bishop extends Piece {
    public Bishop(int turn){
        super(turn);
    }

    public static int moveBishop(int start, int dest){
        int value = start < dest ? start + 1 : start - 1;
        return value;
    }

    public boolean movement(int source_x, int source_y, int dest_x, int dest_y){
        int XCoorDifference = displacement(source_x, dest_x);
        int YCoorDifference = displacement(source_y, dest_y);

        int horizontalAbs = absoluteDisplacement(XCoorDifference);
        int verticalAbs   = absoluteDisplacement(YCoorDifference);

        if(horizontalAbs != verticalAbs)
            return false;
        int start_x = moveBishop(source_x, dest_x);
        int start_y = moveBishop(source_y, dest_y);

        while(!reachedDestination(start_x, start_y, dest_x, dest_y)){
            if(BoardManagement.isOccupied(start_x, start_y))
                return false;
            /* If the start_x < dest_x: increment
             * else decrement
             **/
            start_x = moveBishop(start_x, dest_x);
            start_y = moveBishop(start_y, dest_y);
        }
        return true;
    }
}


