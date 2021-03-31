package sample;

import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import java.io.Serializable;

/**
 * Class describing the radical. Inherited from the Rectangle class.
 * Contains the Label, the current number of connections, and the data required for drawing it.
 * An object of this class can be serialized. Implements the interface Serializable.
 */
public class Radical extends Rectangle implements Serializable {
    public int connectionCount = 0;
    transient public Label radicalLabel;
    public double centerX;
    public double centerY;

    /**
     * Constructor with parameters. Also initializes data inherited from the Rectangle class.
     * @param centerX x-coordinate of the center of the radical
     * @param centerY y-coordinate of the center of the radical
     */
    public Radical(double centerX, double centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.setWidth(32);
        this.setHeight(32);
        this.setFill(Paint.valueOf("WHITE"));
        this.setStroke(Paint.valueOf("#ff0e0e"));
        this.setX(centerX-16);
        this.setY(centerY-16);
        radicalLabel = new Label();
        radicalLabel.setText("R");
        radicalLabel.setFont(new Font("System Bold", 24));
        radicalLabel.setLayoutX(centerX - 8);
        radicalLabel.setLayoutY(centerY - 18);
    }

    /**
     * Method that updates data inherited from the Rectangle class.
     * Is invoked to restore the data after deserialization.
     */
    public void update() {
        this.setWidth(32);
        this.setHeight(32);
        this.setFill(Paint.valueOf("WHITE"));
        this.setStroke(Paint.valueOf("#ff0e0e"));
        this.setX(centerX-16);
        this.setY(centerY-16);
        radicalLabel = new Label();
        radicalLabel.setText("R");
        radicalLabel.setFont(new Font("System Bold", 24));
        radicalLabel.setLayoutX(centerX - 8);
        radicalLabel.setLayoutY(centerY - 18);
    }
}
