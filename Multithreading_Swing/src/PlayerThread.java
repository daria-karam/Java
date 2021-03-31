/**
 * Class responsible for the movement of the object "racket".
 */
public class PlayerThread extends Thread{
    public final Racket player;
    public int playerID;
    private static int speed = 10;

    /**
     * Constructor with the parameter.
     * @param racket the object that will be moved
     * @param playerID ID of player (needs in switch (run))
     */
    public PlayerThread(Racket racket, int playerID) {
        player = racket;
        this.playerID = playerID;
    }

    /**
     * Method responsible for moving the object "racket".
     */
    @Override
    public void run() {
        while (Main.isRun) {
            switch (playerID) {
                case 1:
                    if (Main.isW && player.getLocation().y - speed > 0) {
                        player.setLocation(player.getLocation().x, player.getLocation().y - speed);
                    }
                    if (Main.isS && player.getLocation().y + speed < Main.height - Racket.height - 30) {
                        player.setLocation(player.getLocation().x, player.getLocation().y + speed);
                    }
                    break;
                case 2:
                    if (Main.isUp && player.getLocation().y - speed > 0) {
                        player.setLocation(player.getLocation().x, player.getLocation().y - speed);
                    }
                    if (Main.isDown && player.getLocation().y + speed < Main.height - Racket.height - 30) {
                        player.setLocation(player.getLocation().x, player.getLocation().y + speed);
                    }
                    break;
            }
            try {
                this.sleep(50);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
