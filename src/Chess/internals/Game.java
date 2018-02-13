package Chess.internals;

/**
 * Creates a Game where most of the code
 * executes.
 */
public class Game {
    public static final int firstIndex = 0;
    public static final int finalIndex = 7;
    public static final int boardSize = 8;
    public static Piece[][] Board;

    /**
     * Call helper functions and create
     * a Game with pieces.
     */
    public Game() {
        this.Board = new Piece[boardSize][boardSize];
        BoardManagement.setupPieces();
    }
}
