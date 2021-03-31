import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Class that describes the "racket" graphic object.
 */
public class Racket extends JComponent {
    private int startX;
    private static int startY = 150;
    public static int width = 20;
    public static int height = 100;
    private static Color color = Color.BLUE;

    /**
     * Constructor with the parameter. Creates JComponent.
     * @param startX start x-coordinate
     */
    public Racket(int startX) {
        super();
        this.startX = startX;
        setLocation(startX, startY);
        setSize(new Dimension(width, height));
    }

    /**
     * The method is responsible for drawing the paddle (rectangle) on the JComponent.
     * @param g graphics context
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(color);
        g2.setColor(color);
        Rectangle2D rect = new Rectangle2D.Double(0, 0, width, height);
        g2.draw(rect);
        g2.fill(rect);
    }
}
