/**
 * The main class of the program that contains the main method.
 */
public class Main {
    public static boolean isRun = false;
    public static int width = 600;
    public static int height = 400;

    public static boolean isW = false;
    public static boolean isS = false;
    public static boolean isUp = false;
    public static boolean isDown = false;

    public static PongFrame pongFrame;

    /**
     * Starting method of the program.
     * @param args not used
     */
    public static void main(String[] args) {
        pongFrame = new PongFrame();
    }
}
