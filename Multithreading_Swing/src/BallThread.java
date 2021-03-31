import java.util.Random;

/**
 * Class responsible for the movement of the object "ball".
 */
public class BallThread extends Thread {
    public Ball ball;
    private int playerHeight;
    private int playerWidth;
    private int speedX = 10;
    private int speedY = 10;

    private Random random = new Random();

    /**
     * Constructor with the parameter.
     * @param ball the object that will be moved
     */
    public BallThread(Ball ball) {
        this.ball = ball;
        switchBallToStartPosition();
    }

    /**
     * A method that moves an object to its starting position and sets it a random starting direction.
     */
    public void switchBallToStartPosition() {
        speedY = 10 * ((random.nextInt(2) + 1) * 2 - 3);
        speedX = 10 * ((random.nextInt(2) + 1) * 2 - 3);
        ball.setLocation(290, 190);
    }

    /**
     * Method responsible for moving the object "ball".
     */
    @Override
    public void run() {
        playerHeight = Racket.height;
        playerWidth = Racket.width;
        Main.pongFrame.scorePlayer1 = 0;
        Main.pongFrame.scorePlayer2 = 0;
        Main.pongFrame.repaint();
        while (Main.isRun) {
            int x1, x2, y1, y2;
            synchronized (Main.pongFrame.player1.player) {
                x1 = Main.pongFrame.player1.player.getLocation().x;
                y1 = Main.pongFrame.player1.player.getLocation().y;
            }
            synchronized (Main.pongFrame.player2.player) {
                x2 = Main.pongFrame.player2.player.getLocation().x;
                y2 = Main.pongFrame.player2.player.getLocation().y;
            }
            int xBall = ball.getLocation().x;
            int yBall = ball.getLocation().y;
            if (((x1 + playerWidth > xBall) && (y1 <= yBall + 2 * ball.radius) && (y1 + playerHeight >= yBall))
                    || ((x2 < xBall + 2 * ball.radius) && (y2 <= yBall + 2 * ball.radius) && (y2 + playerHeight >= yBall))) {
                speedX *= -1;
                ball.setLocation(xBall + (int)(speedX * 1.5), yBall + (int)(speedY * 1.5));
            }
            else {
                if (yBall < 10 || yBall + 2 * ball.radius > 360) {
                    speedY *= -1;
                    ball.setLocation(xBall + speedX, yBall + speedY);
                }
                else {
                    if (xBall <= 5) {
                        switchBallToStartPosition();
                        Main.pongFrame.scorePlayer2++;
                        Main.pongFrame.score.scoreValue = Main.pongFrame.scorePlayer1 + ":" + Main.pongFrame.scorePlayer2;
                        Main.pongFrame.repaint();
                    }
                    else {
                        if (xBall + 2 * ball.radius >= 600) {
                            switchBallToStartPosition();
                            Main.pongFrame.scorePlayer1++;
                            Main.pongFrame.score.scoreValue = Main.pongFrame.scorePlayer1 + ":" + Main.pongFrame.scorePlayer2;
                            Main.pongFrame.repaint();
                        }
                        else {
                            ball.setLocation(xBall + speedX, yBall + speedY);
                        }
                    }
                }
            }
            try {
                this.sleep(50);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        switchBallToStartPosition();
        Main.pongFrame.score.scoreValue = "0:0";
        Main.pongFrame.repaint();
    }
}
