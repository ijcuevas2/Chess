package Chess.internals;

public class Kermit extends Rook{
    public Kermit(){
        super(Game.currentTurn());
    }

    public boolean movement(int source_x, int source_y, int dest_x, int dest_y){
        int XCoorDifference = displacement(source_x, dest_x);
        int YCoorDifference = displacement(source_y, dest_y);

        boolean moveInXDirection = XCoorDifference > 0;
        boolean moveInYDirection = YCoorDifference > 0;

        if(XCoorDifference == 1 && YCoorDifference == 1){
             if(Game.isOccupied(dest_x, dest_y))
                 return true;
        }

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
