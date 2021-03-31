import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Class responsible for drawing the window and creating and running threads.
 */
public class PongFrame extends JFrame{
    public Score score;
    private JPanel panel;
    public PlayerThread player1;
    public PlayerThread player2;
    public BallThread ballThread;

    public Racket player1Element;
    public Racket player2Element;
    public Ball ballElement;

    public int scorePlayer1 = 0;
    public int scorePlayer2 = 0;

    /**
     * Default constructor. Draws the window and its elements.
     */
    public PongFrame() {
        player1Element = new Racket(5);
        player2Element = new Racket(600 - 25);
        ballElement = new Ball();

        setTitle("Pong Game");
        score = new Score();

        panel = new JPanel();
        panel.setLayout(null);
        panel.add(player1Element);
        panel.add(player2Element);
        panel.add(ballElement);
        panel.add(score);
        panel.setBackground(Color.CYAN);
        setSize(new Dimension(615, 400));
        setContentPane(panel);
        setVisible(true);
        setResizable(false);
        setLocation(600,300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addKeyListener(new myKeyEventHandler());
    }

    /**
     * Method that creates and starts threads.
     */
    public void run() {
        try {
            player1 = new PlayerThread(player1Element, 1);
            player2 = new PlayerThread(player2Element, 2);
            ballThread = new BallThread(ballElement);
            player1.start();
            player2.start();
            ballThread.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Class responsible for handling keyboard events.
     */
    public class myKeyEventHandler extends KeyAdapter {
        /**
         * Handler for pressing a key.
         * @param event the intercepted keyboard event
         */
        public void keyPressed(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.VK_P) {
                Main.isRun = !Main.isRun;
                if (Main.isRun) {
                    run();
                }
            }
            if (event.getKeyCode() == KeyEvent.VK_W) Main.isW = true;
            if (event.getKeyCode() == KeyEvent.VK_S) Main.isS = true;
            if (event.getKeyCode() == KeyEvent.VK_UP) Main.isUp = true;
            if (event.getKeyCode() == KeyEvent.VK_DOWN) Main.isDown = true;
        }

        /**
         * Handler for releasing a key.
         * @param event the intercepted keyboard event
         */
        public void keyReleased(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.VK_W) Main.isW = false;
            if (event.getKeyCode() == KeyEvent.VK_S) Main.isS = false;
            if (event.getKeyCode() == KeyEvent.VK_UP) Main.isUp = false;
            if (event.getKeyCode() == KeyEvent.VK_DOWN) Main.isDown = false;
        }
    }
}
