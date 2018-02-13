package Chess.internals;
import static Chess.internals.Players.*;

/**
 * Created by ismael on 2/5/17.
 */

abstract class Piece {
    protected String name;
    protected char affiliation;
    public abstract boolean movement(int source_x, int source_y, int dest_x, int dest_y);

    /**
     * Constructor for all the pieces.
     * Initializes the pieces with the first
     * and last letter.
     */
    public Piece(){
        String className = this.getClass().getSimpleName();
        this.name = "" + className.charAt(0) + className.charAt(className.length()-1);
    }

    /**
     * @param: the turn of the current player.
     */
    public Piece(int turn){
        this();
        if(turn == BLACK_TURN.ordinal()){
            this.affiliation = 'B';
        } else if(turn == WHITE_TURN.ordinal()){
            this.affiliation = 'W';
        }
    }

    /* Used to check if the same pieces are used */
    public boolean samePiece(int source_x, int source_y, int dest_x, int dest_y){
        if(source_x == dest_x && source_y == dest_y)
            return true;
        return false;
    }

    /**
     * @param source start piece location
     * @param dest target piece location
     * @return
     */
    public int displacement(int source, int dest){
        return source - dest;
    }

    /**
     * @param displacement value returned from displacement
     * @return the absolute value in terms of distance
     */
    public int absoluteDisplacement(int displacement){
        return Math.abs(displacement);
    }

    /**
     * @param source_x start x_position of piece
     * @param source_y end y_position of piece
     * @param dest_x target x_position of piece
     * @param dest_y target y_position of piece
     * @return boolean value whether the piece reached its destination
     */
    public boolean reachedDestination(int source_x, int source_y, int dest_x, int dest_y){
        return source_x == dest_x && source_y == dest_y;
    }
    /**
     * @param source_x: input x-coordinate
     * @param source_y: input y-coordinate
     * @param dest_x: destination x-coordinate
     * @param dest_y: destination y-coordinate
     * @return boolean value dependent on whether the action
     *         passes a piece check and an ownership check.
     */
    protected boolean pieceChecks(int source_x, int source_y, int dest_x, int dest_y){
        if(samePiece(source_x, source_y, dest_x, dest_y))
            return false;
        if(!BoardManagement.pieceOwnership(source_x, source_y))
            return false;
        return true;
    }



    // protected int xDisplacement(int x, int y){ return y - x; }

    /**
     * @return: The first three initials of the piece.
     */
    public String getName(){
        return this.name;
    }

    /**
     * @return the string's affliation
     */
    public char getAffiliation(){
        return this.affiliation;
    }
}
