import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Class that describes the "ball" graphic object.
 */
public class Ball extends JComponent {
    public Rectangle ball;
    public int radius = 15;
    private static Color color = Color.WHITE;

    /**
     * Default constructor. Creates JComponent.
     */
    public Ball() {
        super();
        setLocation(290, 190);
        setSize(new Dimension(2 * radius + 2, 2 * radius + 2));
    }

    /**
     * The method is responsible for drawing the ball (ellipse/circle) on the JComponent.
     * @param g graphics context
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        Ellipse2D circle = new Ellipse2D.Double(0, 0, 2 * radius, 2 * radius);
        g2.draw(circle);
        g2.fill(circle);
    }
}
