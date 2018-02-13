package Chess.internals;


/**
 * Created by ismael on 2/5/17.
 */

public class Pawn extends Piece {
    public Pawn(int turn){
        super(turn);
    }

    public boolean firstTurnBonus(int x_displacement, int y_displacement){
        return (y_displacement == 2) && BoardManagement.firstTurn() && (x_displacement == 0);
    }
    /**
     * @param source_y
     * @param dest_y
     * @return A boolean value, returns false if a white pawn
     * moves down or a black pawn moves up.
     */
    public boolean PawnChecks(int source_x, int source_y, int dest_x, int dest_y){
        int sign = (BoardManagement.whiteTurn() ? 1 : - 1);
        if(sign > 0 && (dest_y - source_y) < 0)
            return false;
        if(sign < 0 && (dest_y - source_y) > 0)
            return false;
        int x_movement = displacement(source_x, dest_x);
        int y_movement = displacement(source_y, dest_y);
        if(x_movement > 1)
            return false;
        if(y_movement > 1){
            if(!(firstTurnBonus(x_movement, y_movement)))
               return false;
        }
        return true;
    }

    /**
     * @return: Boolean value which represents
     * whether a Pawn can move or not.
     */
    @Override
    public boolean movement(int source_x, int source_y, int dest_x, int dest_y) {
        /* If you aren't the owner, you can't move this.*/
        boolean passedChecks = pieceChecks(source_x, source_y, dest_x, dest_y);
        System.out.println("passChecks: " + passedChecks);
        if(!passedChecks)
            return false;


        boolean passedPawnChecks = PawnChecks(source_x, source_y, dest_x, dest_y);
        System.out.println("passedPawnChecks: " + passedPawnChecks);
        if(!passedPawnChecks)
            return false;

        int XCoorDifference = displacement(source_x, dest_x);
        int YCoorDifference = displacement(source_y, dest_y);

        /* Test for pawn checks */
        System.out.println("Passed checks and Pawn Checks");
        /* TODO: Insert test to check if this will fail with own piece */
        if(XCoorDifference == 1 && YCoorDifference == 1){
            /* Edit this to take King into account. */
            if(BoardManagement.isOccupied(dest_x, dest_y))
                return true;
            return false;
        }
        return true;
    }
}
