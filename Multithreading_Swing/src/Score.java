import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Class that describes the "score" graphic object.
 * Also contains a description of the message about game start/stop.
 */
public class Score extends JComponent {
    public String scoreValue = "0:0";

    /**
     * Default constructor. Creates JComponent.
     */
    public Score() {
        super();
        setLocation(0, 0);
        setSize(new Dimension(615, 400));
    }

    /**
     * The method is responsible for drawing the score (text) and message about game start/stop on the JComponent.
     * @param g graphics context
     */
    @Override
    public void paintComponent(Graphics g) {
        try {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.PLAIN, 30));
            g2.drawString(scoreValue, 280, 50);
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            if (!Main.isRun) g2.drawString("Press P to start", 230, 350);
            else g2.drawString("Press P to stop", 230, 350);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
