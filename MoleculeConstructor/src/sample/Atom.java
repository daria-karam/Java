package sample;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import java.io.Serializable;

/**
 * The class that defines the atom. Inherited from the Circle.
 * Contains the atom type, valence, and data to draw.
 * An object of this class can be serialized. Implements the interface Serializable.
 */
public class Atom extends Circle implements Serializable {
    public String type;
    public int maxConnectionsCount; //valence
    public int currentConnectionsCount = 0;
    public double centerX;
    public double centerY;

    /**
     * Constructor with parameters. Also initializes data inherited from the Circle class.
     * @param type atom type: hydrogen (H), nitrogen (N), carbon (C) or oxygen (O)
     * @param centerX x-coordinate of the center of the atom
     * @param centerY y-coordinate of the center of the atom
     */
    public Atom(String type, double centerX, double centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.type = type;
        switch (type) {
            case "H":
                this.setFill(Paint.valueOf("#1ff6ff"));
                this.maxConnectionsCount = 1;
                this.setRadius(12);
                break;
            case "N":
                this.setFill(Paint.valueOf("#ff70bc"));
                this.maxConnectionsCount = 3;
                this.setRadius(14);
                break;
            case "C":
                this.setFill(Paint.valueOf("#f59e41"));
                this.maxConnectionsCount = 4;
                this.setRadius(14);
                break;
            case "O":
                this.setFill(Paint.valueOf("#3e4efc"));
                this.maxConnectionsCount = 2;
                this.setRadius(16);
                break;
            default:
                break;
        }
        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setStroke(Paint.valueOf("BLACK"));
        this.setStrokeWidth(1);
    }

    /**
     * Method that updates data inherited from the Circle class.
     * Is invoked to restore the data after deserialization.
     */
    public void update() {
        switch (type) {
            case "H":
                this.setFill(Paint.valueOf("#1ff6ff"));
                this.maxConnectionsCount = 1;
                this.setRadius(12);
                break;
            case "N":
                this.setFill(Paint.valueOf("#ff70bc"));
                this.maxConnectionsCount = 3;
                this.setRadius(14);
                break;
            case "C":
                this.setFill(Paint.valueOf("#f59e41"));
                this.maxConnectionsCount = 4;
                this.setRadius(14);
                break;
            case "O":
                this.setFill(Paint.valueOf("#3e4efc"));
                this.maxConnectionsCount = 2;
                this.setRadius(16);
                break;
            default:
                break;
        }
        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setStroke(Paint.valueOf("BLACK"));
        this.setStrokeWidth(1);
    }

    /**
     * Method that determines whether another bond can be added to a given atom.
     * @return true(if can), false(if can't)
     */
    public boolean canAddNewConnection() {
        return currentConnectionsCount < maxConnectionsCount;
    }
}
