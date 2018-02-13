package Chess.internals;

import java.util.Scanner;
import static Chess.internals.Players.BLACK_TURN;
import static Chess.internals.Players.WHITE_TURN;

public class BoardManagement {
    public static int turn = 0;

    /**
     * @param source_x x_position of chess piece.
     * @param source_y y_position of chess piece.
     * @param dest_x   x_destination of chess piece.
     * @param dest_y   y_destination of chess piece.
     * @return
     */
    public static boolean movementPhase(int source_x, int source_y, int dest_x, int dest_y){
        boolean validMove = validMovement(source_x, source_y, dest_x, dest_y);
        // System.out.println("validMove: " + validMove);
        if(!validMove)
            return false;
        /* This is purely for test purposes */
        swapPieces(source_x, source_y, dest_x, dest_y);
        return true;
    }

    /**
     * @param source chess coordinate of piece
     * @return position of chess x-coordinate in array.
     */
    public static int extractXCoor(String source){
        return source.charAt(0) - 'A';
    }

    /**
     * @param source
     * @return position of chess y-coordinate in array.
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
     * @param source_x x_position of piece
     * @param source_y y_position of piece
     * @return A boolean that represents whether
     * the player of the current turn in fact owns
     * the piece that they desired.
     */
    public static boolean pieceOwnership(int source_x, int source_y){
        Piece testPiece = Game.Board[source_x][source_y];
        char label = testPiece.getAffiliation();

        if(turnCharMatch(label))
            return true;
        return false;
    }

    /**
     * @param source_x x_position of piece
     * @param source_y y_position of piece
     * @param dest_x x_destination of piece
     * @param dest_y y_destination of piece
     * @return A boolean of whether the selected movement was valid.
     */
    public static boolean validMovement(int source_x, int source_y, int dest_x, int dest_y) {
        Piece piece = Game.Board[source_x][source_y];
        return piece.movement(source_x, source_y, dest_x, dest_y);
    }

    /**
     * Returns true if the space is occupied
     * by a non-empty piece.
     */
    public static boolean isOccupied(int x, int y){
        return Game.Board[x][y].getName() != "  ";
    }

    /**
     * @param source_x x_position of piece
     * @param source_y y_position of piece
     * @param dest_x x_destination of piece
     * @param dest_y y_destination of piece
     * Swap the coordinates of the pieces. This is the
     * main function used for movement.
     */
    public static void swapPieces(int source_x, int source_y, int dest_x, int dest_y){
        Game.Board[dest_x][dest_y]     = Game.Board[source_x][source_y];
        Game.Board[source_x][source_y] = new Empty();
        System.out.println("Piece changed!!");
    }

    /**
     * Print current state of board.
     */
    public static void printBoard(){
        System.out.print("   ");
        for(int i = 'A'; i <= 'H'; i++){
            System.out.print((char)i + "   ");
        }

        System.out.println("");
        for(int y = 0; y < Game.boardSize; y++){
            for(int x = 0; x < Game.boardSize; x++){
                if(x == 0)
                    System.out.print((8 - y) + "  ");
                System.out.print(Game.Board[x][y].name + "  ");
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
        for(int x = 0; x < Game.boardSize; x++){
            for(int y = 2; y < Game.boardSize - 2; y++){
                Game.Board[x][y] = new Empty();
            }
        }
    }

    /**
     * Initialize pawns on the Board.
     */
    public static void createPawns() {
        for (int i = 0; i < Game.boardSize; i++) {
        /* Initialize Pawns on the Penultimate Rows */
            Game.Board[i][Game.finalIndex - 1] = new Pawn(BLACK_TURN.ordinal());
            Game.Board[i][Game.firstIndex + 1] = new Pawn(WHITE_TURN.ordinal());

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
        Game.Board[Game.firstIndex][Game.firstIndex] = new Rook(WHITE_TURN.ordinal());
        Game.Board[Game.finalIndex][Game.firstIndex] = new Rook(WHITE_TURN.ordinal());

    /* Create space for black rooks. */
        Game.Board[Game.firstIndex][Game.finalIndex] = new Rook(BLACK_TURN.ordinal());
        Game.Board[Game.finalIndex][Game.finalIndex] = new Rook(BLACK_TURN.ordinal());
    }

    /**
     * Initializes parts of the Board with Knights
     */
    public static void createKnights() {
    /* Create space for white Knight. */
        Game.Board[Game.firstIndex + 1][Game.firstIndex] = new Knight(WHITE_TURN.ordinal());
        Game.Board[Game.finalIndex - 1][Game.firstIndex] = new Knight(WHITE_TURN.ordinal());

    /* Create space for black Knight. */
        Game.Board[Game.firstIndex + 1][Game.finalIndex] = new Knight(BLACK_TURN.ordinal());
        Game.Board[Game.finalIndex - 1][Game.finalIndex] = new Knight(BLACK_TURN.ordinal());
    }

    /**
     * Initializes parts of the Board with Knights
     */
    public static void createBishops() {
    /* Create space for white Bishops. */
        Game.Board[Game.firstIndex + 2][Game.firstIndex] = new Bishop(WHITE_TURN.ordinal());
        Game.Board[Game.finalIndex - 2][Game.firstIndex] = new Bishop(WHITE_TURN.ordinal());

    /* Create space for black Bishops. */
        Game.Board[Game.firstIndex + 2][Game.finalIndex] = new Bishop(BLACK_TURN.ordinal());
        Game.Board[Game.finalIndex - 2][Game.finalIndex] = new Bishop(BLACK_TURN.ordinal());
    }

    /**
     * Creating King and Queen for each side.
     */
    public static void createRoyalty() {
        Game.Board[Game.firstIndex + 3][Game.finalIndex] = new Queen(BLACK_TURN.ordinal());
        Game.Board[Game.finalIndex - 3][Game.finalIndex] = new King(BLACK_TURN.ordinal());

        Game.Board[Game.firstIndex + 3][Game.firstIndex] = new Queen(WHITE_TURN.ordinal());
        Game.Board[Game.finalIndex - 3][Game.firstIndex] = new King(WHITE_TURN.ordinal());
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
     * @param letter, the first letter of a piece's name
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
     * @param input checks if string follows valid format.
     * @return if input is valid
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
        }

    }

    /**
     * @param promptNumber switch for specific prompt requested.
     */
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
}
