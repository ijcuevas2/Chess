package Chess.internals;
import static Chess.internals.Players.*;
import java.util.Scanner;

/**
 * Creates a Game where most of the code
 * executes.
 */
public class Game {
    public static final int firstIndex = 0;
    public static final int finalIndex = 7;
    public static final int boardSize = 8;
    public static int turn = 0;
    public static Piece[][] Board;

    /**
     * Call helper functions and create
     * a Game with pieces.
     */
    public Game() {
        this.Board = new Piece[boardSize][boardSize];

        /**
         * Test to check proper coordinates for
         * the board.
         */
        setupPieces();
        Scanner scanner = new Scanner(System.in);
        // while(true){
            userInput(scanner);
        //}
    }

    /**
     * Set Turn
     */
    public static void showTurn(){
        int currPlayerTurn = currentTurn();
        if(currPlayerTurn == WHITE_TURN.ordinal()){
            System.out.println("Turn of White Player.");
        }
        if(currPlayerTurn == BLACK_TURN.ordinal()){
            System.out.println("Turn of Black Player.");
        }
    }

    public static void prompt(int promptNumber){
        switch(promptNumber){
            case 1: System.out.println("Which piece would you like to move? (Please Enter"
                    + " in form of Coordinates)");
                break;
            case 2: System.out.println("To where would you like to move this "
                    + " piece? (Please Enter in form of Coordinates)");
                break;
        }
    }

    /**
     * @return A boolean representing whether
     * it is the still the first turn.
     */
    public static boolean firstTurn(){
        return turn <= 1;
    }

    /**
     * @return The color of the current turn.
     */
    public static int currentTurn(){
        return (turn % 2);
    }

    public static boolean whiteTurn(){
        if(currentTurn() == WHITE_TURN.ordinal())
            return true;
        return false;
    }

    public static boolean blackTurn(){
        if(currentTurn() == BLACK_TURN.ordinal())
            return true;
        return false;
    }

    /**
     * @param  letter, the first letter of a piece's name
     * @return whether the current turn matches the first
     * letter's affiliation.
     */
    public static boolean turnCharMatch(char letter){
        if(letter == 'B' && (currentTurn() == BLACK_TURN.ordinal()))
            return true;
        if(letter == 'W' && (currentTurn() == WHITE_TURN.ordinal()))
            return true;
        return false;
    }

    /**
     * @param input, checks if string follows valid format.
     * @return
     */
    public static boolean checkValidInput(String input){
            /* Length Check */
            if(input.length() != 2){
                invalidInput();
                return false;
            }

            /* Range Check */
            if(input.charAt(0) > 'H' || input.charAt(0) < 'A'){
                invalidInput();
                return false;
            }

            if(input.charAt(1) == '0'){
                invalidInput();
                return false;
            }
            return true;
    }

    /**
     * Prints out an invalid message.
     */
    public static void invalidInput(){
        System.out.println("Invalid Input! Try Again!");
    }

    /**
     * Starts the Game by asking for user input.
     */
    public static void userInput(Scanner scanner){
        while(true){
            printBoard();
            // int playerTurn = currentTurn(turn);
            showTurn();
            prompt(1);
            String sourceInput = scanner.next();
            String source = sourceInput.toUpperCase();

            /* For debugging. */
            System.out.println("Source: " + source);

            boolean validSource = checkValidInput(source);
            if(!validSource)
                continue;

            prompt(2);
            String destinationInput = scanner.next();
            String destination = destinationInput.toUpperCase();
            boolean destinationValidation = checkValidInput(destination);
            if(!destinationValidation)
                continue;

            System.out.println("Destination: " + destination);
            int[] coordinates = convertAlgebraTo2DCoors(source, destination);

            boolean successfulMove = movementPhase(coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
            if(successfulMove)
                turn++;
            else
                invalidInput();
            /* Test to check coordinates */
            // for(int i = 0; i < coordinates.length; i++)
            //     System.out.println("coordinates[" + i + "]: " + coordinates[i]);
            // break;
        }

        /* Test for trailing whitespace, not necessary */
        // String filtered_request = request.trim();
        // System.out.println("filtered_request: " + filtered_request);

        /* This returned a string before. */
        // return "";
    }

    public static boolean movementPhase(int source_x, int source_y, int dest_x, int dest_y){
        boolean validMove = validMovement(source_x, source_y, dest_x, dest_y);
        // System.out.println("validMove: " + validMove);
        if(!validMove)
            return false;
        /* This is purely for test purposes */
        swapPieces(source_x, source_y, dest_x, dest_y);
        return true;
    }

    public static int extractXCoor(String source){
        return source.charAt(0) - 'A';
    }

    /**
     * @param source
     * @return
     */
    public static int extractYCoor(String source){
        return 8 - (source.charAt(1) - 48);
    }

    /**
     * @param source
     * @param destination
     * @return An array that contains the source coordinates
     * in the first two indices, and dest coordinates in the
     * last two indices.
     */
    public static int[] convertAlgebraTo2DCoors(String source, String destination){
        int source_x = extractXCoor(source);
        int source_y = extractYCoor(source);

        /* Test for checking source_x and source_y */
        // System.out.println("source_x: " + source_x);
        // System.out.println("source_y: " + source_y);

        int dest_x = extractXCoor(destination);
        int dest_y = extractYCoor(destination);

        /* Test for checking source_x and source_y */
        // System.out.println("dest_x: " + dest_x);
        // System.out.println("dest_y: " + dest_y);

        int[] coordinates = new int[4];
        coordinates[0]    = source_x;
        coordinates[1]    = source_y;
        coordinates[2]    = dest_x;
        coordinates[3]    = dest_y;
        return coordinates;
    }

    /**
     * @param source_x
     * @param source_y
     * @return A boolean that represents whether
     * the player of the current turn in fact owns
     * the piece that they desired.
     */
    public static boolean pieceOwnership(int source_x, int source_y){
        Piece testPiece = Board[source_x][source_y];
        char label      = testPiece.getName().charAt(0);

        if(turnCharMatch(label))
            return true;
        return false;
    }

    /**
     *
     */
    public static boolean validMovement(int source_x, int source_y, int dest_x, int dest_y) {
        Piece piece = Board[source_x][source_y];
        return piece.movement(source_x, source_y, dest_x, dest_y);
    }

    /**
     * Returns true if the space is occupied
     * by a non-empty piece.
     */
    public static boolean isOccupied(int x, int y){
        return Board[x][y].getName() != "  ";
    }

    /**
     * Swap the coordinates of the pieces. This is the
     * main function used for movement.
     */
    public static void swapPieces(int source_x, int source_y, int dest_x, int dest_y){
        Board[dest_x][dest_y]     = Board[source_x][source_y];
        Board[source_x][source_y] = new Empty();
        System.out.println("Piece changed!!");
    }

    /**
     * Print current state of board.
     */
    public static void printBoard(){
        System.out.print("  ");
        for(int i = 'A'; i <= 'H'; i++){
            System.out.print((char)i + "   ");
        }

        System.out.println("");
        for(int y = 0; y < boardSize; y++){
            for(int x = 0; x < boardSize; x++){
                if(x == 0)
                    System.out.print((8 - y) + " ");
                System.out.print(Board[x][y].name + " ");
            }
            System.out.println("");
        }
    }

    /**
     * Function that calls all the other
     * pieces functions.
     */
    public static void setupPieces(){
        createEmpty();
        createPawns();
        createRooks();
        createKnights();
        createBishops();
        createRoyalty();
    }

    /* Create Empty Piece Used to Represent Empty Space */
    public static void createEmpty(){
        for(int x = 0; x < boardSize; x++){
            for(int y = 2; y < boardSize - 2; y++){
                Board[x][y] = new Empty();
            }
        }
    }
    /**
     * Initialize pawns on the Board.
     */
    public static void createPawns() {
        for (int i = 0; i < boardSize; i++) {
        /* Initialize Pawns on the Penultimate Rows */
            Board[i][finalIndex - 1] = new Pawn(BLACK_TURN.ordinal());
            Board[i][firstIndex + 1] = new Pawn(WHITE_TURN.ordinal());

        /* Tests for checking PawnIDs*/
            // String blackPieceName = Board[i][1].name;
            // String whitePieceName = Board[i][6].name;
            // System.out.println("PawnIDs: " + blackPieceName);
            // System.out.println("PawnIDs: " + whitePieceName);
        }
    }

    /**
     * Initializes parts of the Board with Rooks.
     */
    public static void createRooks() {
    /* Create space for white rooks. */
        Board[firstIndex][firstIndex] = new Rook(WHITE_TURN.ordinal());
        Board[finalIndex][firstIndex] = new Rook(WHITE_TURN.ordinal());

    /* Create space for black rooks. */
        Board[firstIndex][finalIndex] = new Rook(BLACK_TURN.ordinal());
        Board[finalIndex][finalIndex] = new Rook(BLACK_TURN.ordinal());
    }

    /**
     * Initializes parts of the Board with Knights
     */
    public static void createKnights() {
    /* Create space for white Knight. */
        Board[firstIndex + 1][firstIndex] = new Knight(WHITE_TURN.ordinal());
        Board[finalIndex - 1][firstIndex] = new Knight(WHITE_TURN.ordinal());

    /* Create space for black Knight. */
        Board[firstIndex + 1][finalIndex] = new Knight(BLACK_TURN.ordinal());
        Board[finalIndex - 1][finalIndex] = new Knight(BLACK_TURN.ordinal());
    }

    /**
     * Initializes parts of the Board with Knights
     */
    public static void createBishops() {
    /* Create space for white Bishops. */
        Board[firstIndex + 2][firstIndex] = new Bishop(WHITE_TURN.ordinal());
        Board[finalIndex - 2][firstIndex] = new Bishop(WHITE_TURN.ordinal());

    /* Create space for black Bishops. */
        Board[firstIndex + 2][finalIndex] = new Bishop(BLACK_TURN.ordinal());
        Board[finalIndex - 2][finalIndex] = new Bishop(BLACK_TURN.ordinal());
    }

    /**
     * Creating King and Queen for each side.
     */
    public static void createRoyalty() {
        Board[firstIndex + 3][finalIndex] = new Queen(BLACK_TURN.ordinal());
        Board[finalIndex - 3][finalIndex] = new King(BLACK_TURN.ordinal());

        Board[firstIndex + 3][firstIndex] = new Queen(WHITE_TURN.ordinal());
        Board[finalIndex - 3][firstIndex] = new King(WHITE_TURN.ordinal());
    }
}
