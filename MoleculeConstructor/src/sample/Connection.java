package sample;

import java.io.Serializable;

/**
 * The class that describes the valence bond between the atoms.
 * Contains the multiplicity (capacity) of the connection and the data required for drawing the line.
 * An object of this class can be serialized. Implements the interface Serializable.
 */
public class Connection implements Serializable {
    public Atom firstAtom;
    public Atom secondAtom;
    public Radical radical;
    public double x1;
    public double x2;
    public double y1;
    public double y2;
    public int capacity;

    /**
     * Constructor with parameters.
     * @param first the first atom
     * @param second the second atom (if there is; may be null)
     * @param radical radical (if there is; may be null)
     */
    public Connection(Atom first, Atom second, Radical radical) {
        if (first != null) {
            x1 = first.getCenterX();
            y1 = first.getCenterY();
        }
        if (second != null) {
            if (first != null) {
                x2 = second.getCenterX();
                y2 = second.getCenterY();
            }
            else {
                x1 = second.getCenterX();
                y1 = second.getCenterY();
            }
        }
        if (radical != null) {
            x2 = radical.getX() + 16;
            y2 = radical.getY() + 16;
        }
        this.firstAtom = first;
        this.secondAtom = second;
        this.radical = radical;
        capacity = 0;
    }

    /**
     * Method that creates the connection.
     * Increases the current number of connections for each component.
     * Increases the capacity of connections.
     */
    public void setConnection() {
        if (firstAtom != null) {
            firstAtom.currentConnectionsCount++;
        }
        if (secondAtom != null) {
            secondAtom.currentConnectionsCount++;
        }
        if (radical != null) {
            radical.connectionCount++;
        }
        capacity++;
    }

    /**
     * Method that deletes the connection.
     * Reduces the current number of connections for each component by the capacity value.
     * This Connection object will not be used in the future.
     */
    public void resetConnection() {
        for (int i = 0; i < capacity; i++) {
            if (firstAtom != null) {
                firstAtom.currentConnectionsCount--;
            }
            if (secondAtom != null) {
                secondAtom.currentConnectionsCount--;
            }
            if (radical != null) {
                radical.connectionCount--;
            }
        }
    }

    /**
     * Method that checks for the presence of a specific atom in a given bond.
     * @param currentAtom the atom you are looking for
     * @return true(if there is), false(if there isn't)
     */
    public boolean containsAtom(Atom currentAtom) {
        if (firstAtom != null && firstAtom.getCenterX() == currentAtom.getCenterX()
                && firstAtom.getCenterY() == currentAtom.getCenterY()) {
            return true;
        }
        if (secondAtom != null && secondAtom.getCenterX() == currentAtom.getCenterX()
                && secondAtom.getCenterY() == currentAtom.getCenterY()) {
            return true;
        }
        return false;
    }

    /**
     * Method that checks for the presence of a specific radical in a given bond.
     * @param currentRadical the radical you are looking for
     * @return true(if there is), false(if there isn't)
     */
    public boolean containsRadical(Radical currentRadical) {
        return (radical != null && radical.getX() == currentRadical.getX() && radical.getY() == currentRadical.getY());
    }

    /**
     * Method that checks whether method setConnection() can be called for this object.
     * @return true(if can), false(if can't)
     */
    public boolean canAddNewConnection() {
        boolean answer = true;
        if (firstAtom != null) answer = answer && firstAtom.canAddNewConnection();
        if (secondAtom != null) answer = answer && secondAtom.canAddNewConnection();
        if (radical != null) answer = answer && radical.connectionCount == 0;
        return answer;
    }
}
