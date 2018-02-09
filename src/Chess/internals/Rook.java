/**
 * Created by Ismael
 */

package Chess.internals;

/**
 * Class for creating the Rook.
 */
public class Rook extends Piece {
    public Rook(int turn){
        super(turn);
    }

    public int moveRook(int start, int dest){
        int value = start < dest ? start + 1 : start - 1;
        return value;
    }

    public int moveRookInDirection(int source_x, int source_y, int dest_x, int dest_y, boolean moveInXDirection){
        if(moveInXDirection)
            return moveRook(source_x, dest_x);
        else
            return moveRook(source_y, dest_y);
    }

    public boolean noPiecesInTheWay(int source_x, int source_y, int dest_x, int dest_y, boolean moveInXDirection){
        boolean firstTurn = false;
        while(!reachedDestination(source_x, source_y, dest_x, dest_y)){
            //MoveInXDirection
            System.out.println("in Pieces");
            if(Game.isOccupied(source_x, source_y) && !firstTurn)
                return false;
            moveRookInDirection(source_x, source_y, dest_x, dest_y, moveInXDirection);
            firstTurn = true;
        }
        return true;
    }

    public boolean movement(int source_x, int source_y, int dest_x, int dest_y){
        int XCoorDifference = displacement(source_x, dest_x);
        int YCoorDifference = displacement(source_y, dest_y);

        boolean moveInXDirection = XCoorDifference > 0;
        boolean moveInYDirection = YCoorDifference > 0;

        // So moving an x-direction and y-direction is now
        // mutually exclusive.
        if(moveInXDirection && moveInYDirection)
            return false;

        // Everything else is fine, just check if there
        // are no pieces in the way.
        moveRookInDirection(source_x, source_y, dest_x, dest_y, moveInXDirection);
        return noPiecesInTheWay(source_x, source_y, dest_x, dest_y, moveInXDirection);
    }
}
