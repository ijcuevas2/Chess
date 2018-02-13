/**
 * Created by ismael on 2/5/17.
 */

package Chess;
import Chess.internals.*;
import java.lang.*;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game ChessGame = new Game();
        Scanner scanner = new Scanner(System.in);
        BoardManagement.userInput(scanner);
    }
}
