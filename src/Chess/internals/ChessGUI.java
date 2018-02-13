package Chess.internals;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import java.io.File;
import javax.imageio.ImageIO;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
import java.util.Collections;
import java.util.Arrays;

/*Source: http://stackoverflow.com/questions/21142686/making-a-robust-resizable-swing-chess-gui*/

/**
 * Creates the ChessGUI.
 */
public class ChessGUI {

    /*Initialize a GUI object */
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] chessBoardSquares = new JButton[8][8];

    /*Array to Store Chess Pieces*/
    private Image[][] chessPieceImages = new Image[2][6];

    private JPanel chessBoard;
    private final JLabel message = new JLabel("Chess");

    private static final String COLS = "ABCDEFGH";
    /*Save piece constants in an array*/
    public static final int QUEEN = 0, KING = 1,
            ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
    public static final int BLACK = 0, WHITE = 1;
    public static final int[] STARTING_ROW = {
            ROOK, KNIGHT, BISHOP, KING, QUEEN, BISHOP, KNIGHT, ROOK
    };

    public static final int ROOK_POSL = 0, KNIGHT_POSL = 1, BISHOP_POSL = 2,
    QUEEN_POS = 3, KING_POS = 4, BISHOP_POSR = 5, KNIGHT_POSR = 6, ROOK_POSR = 7;


    public final void initializeGui() {
        createImages();

        gui.setBorder(new EmptyBorder(2, 2, 2, 2));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);

        Action newGameAction = new AbstractAction("New Game") {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupNewGame();
            }
        };

        /*Adds Game Buttons*/
        tools.add(newGameAction);
        tools.add(new JButton("Undo"));
        tools.addSeparator();
        tools.add(message);

        gui.add(new JLabel("?"), BorderLayout.LINE_START);
        chessBoard = new JPanel(new GridLayout(0, 9)); /* {

            @Override
            public final Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                Dimension prefSize = null;
                Component c = getParent();
                if (c == null) {
                    prefSize = new Dimension(
                            (int)d.getWidth(),(int)d.getHeight());
                } else if (c!=null &&
                        c.getWidth()>d.getWidth() &&
                        c.getHeight()>d.getHeight()) {
                    prefSize = c.getSize();
                } else {
                    prefSize = d;
                }
                int w = (int) prefSize.getWidth();
                int h = (int) prefSize.getHeight();
                int s = (w>h ? h : w);
                return new Dimension(s,s);
            }
        };*/

        chessBoard.setBorder(new CompoundBorder(
                new EmptyBorder(8, 8, 8, 8),
                new LineBorder(Color.BLACK)
        ));

        chessBoard.setBackground(Color.GRAY);
        JPanel boardConstrain = new JPanel(new GridBagLayout());
        boardConstrain.setBackground(Color.GRAY);
        // boardConstrain.setBackground(Color.RED);
        boardConstrain.add(chessBoard);
        gui.add(boardConstrain);

        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int ii = 0; ii < chessBoardSquares.length; ii++) {
            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                b.setRolloverEnabled(true);
                ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                b.setName("Empty");
                // if ((jj % 2 == 1 && ii % 2 == 1)
                //         || (jj % 2 == 0 && ii % 2 == 0)) {
                //     b.setBackground(Color.WHITE);
                // } else {
                //     b.setBackground(Color.LIGHT_GRAY);
                // }
                b.setBackground(Color.BLUE);
                b.getModel().addChangeListener(e -> {
                    ButtonModel model = (ButtonModel) e.getSource();
                    if(model.isRollover()){
                        b.setOpaque(true);
                        System.out.println(b.getName());
                    } else{
                        b.setOpaque(false);
                    }
                });
                chessBoardSquares[jj][ii] = b;
            }
        }

        /*
         * fill the chess board
         */
        chessBoard.add(new JLabel(""));
        for (int ii = 0; ii < 8; ii++) {
            chessBoard.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                            SwingConstants.CENTER));
        }
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                switch (jj) {
                    case 0:
                        chessBoard.add(new JLabel("" + (9 - (ii + 1)),
                                SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[jj][ii]);
                }
            }
        }
    }

    ChessGUI() {
        initializeGui();
    }

    public final JComponent getGui() {
        return gui;
    }

    private final void createImages() {
        try {
            File file = new File("src/Chess/internals/assets/sprite.png");
            BufferedImage bi = ImageIO.read(file);
            for (int ii = 0; ii < 2; ii++) {
                for (int jj = 0; jj < 6; jj++) {
                    chessPieceImages[ii][jj] = bi.getSubimage(
                            jj * 64, ii * 64, 64, 64);
                }
            }
            Collections.reverse(Arrays.asList(chessPieceImages[0]));
            Collections.reverse(Arrays.asList(chessPieceImages[1]));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /* Initializes the icons of the initial chess board piece places */
    private final void setupNewGame() {
        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
            chessBoardSquares[ii][0].setIcon(new ImageIcon(chessPieceImages[BLACK][STARTING_ROW[ii]]));
            switch (ii){
                case ROOK_POSL:
                case ROOK_POSR:
                    chessBoardSquares[ii][0].setName("Rk");
                    break;
                case KNIGHT_POSL:
                case KNIGHT_POSR:
                    chessBoardSquares[ii][0].setName("Kt");
                    break;
                case BISHOP_POSL:
                case BISHOP_POSR:
                    chessBoardSquares[ii][0].setName("Bp");
                    break;
                case KING_POS:
                    chessBoardSquares[ii][0].setName("Kg");
                    break;
                case QUEEN_POS:
                    chessBoardSquares[ii][0].setName("Qn");
                    break;
            }
        }
        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
            chessBoardSquares[ii][1].setIcon(new ImageIcon(chessPieceImages[BLACK][PAWN]));
            chessBoardSquares[ii][1].setName("Pn");
        }
        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
            chessBoardSquares[ii][6].setIcon(new ImageIcon(chessPieceImages[WHITE][PAWN]));
            chessBoardSquares[ii][6].setName("Pn");
        }
        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
            chessBoardSquares[ii][7].setIcon(new ImageIcon(chessPieceImages[WHITE][STARTING_ROW[ii]]));
            switch (ii){
                case ROOK_POSL:
                case ROOK_POSR:
                    chessBoardSquares[ii][7].setName("Rk");
                    break;
                case KNIGHT_POSL:
                case KNIGHT_POSR:
                    chessBoardSquares[ii][7].setName("Kt");
                    break;
                case BISHOP_POSL:
                case BISHOP_POSR:
                    chessBoardSquares[ii][7].setName("Bp");
                    break;
                case KING_POS:
                    chessBoardSquares[ii][7].setName("Kg");
                    break;
                case QUEEN_POS:
                    chessBoardSquares[ii][7].setName("Qn");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Runnable r = () -> {
            ChessGUI cg = new ChessGUI();
            JFrame f = new JFrame("Chess");
            f.add(cg.getGui());
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setLocationByPlatform(true);

            f.pack();
            f.setMinimumSize(f.getSize());
            f.setVisible(true);
        };
        SwingUtilities.invokeLater(r);
    }
}

