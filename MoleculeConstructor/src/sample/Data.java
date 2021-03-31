package sample;

import javafx.scene.shape.Line;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that contains all the data necessary for drawing,
 * storing, saving, and loading complete information about created objects.
 * Stores data about the radical and lists of atoms, connections, and lines.
 * An object of this class can be serialized. Implements the interface Serializable.
 * It is an object of this class that is used for serialization of data to a file
 * (and deserialization from a file).
 */
public class Data implements Serializable {
    public ArrayList<Atom> atoms = new ArrayList<>();
    public ArrayList<Connection> connections = new ArrayList<>();
    transient public ArrayList<Line> lines = new ArrayList<>();
    public Radical radical = null;

    /**
     * Default constructor.
     */
    public Data() {
        atoms = new ArrayList<>();
        connections = new ArrayList<>();
        lines = new ArrayList<>();
        radical = null;
    }

    /**
     * Method that deletes all data stored in the class object.
     */
    public void clearAllData() {
        atoms.clear();
        connections.clear();
        lines.clear();
        radical = null;
    }

    /**
     * Method that returns a connection if it already exists in the class.
     * It is needed to implement communication capacity and prevent duplication.
     * @param currentConnection the connection you are looking for
     * @return connection if it already exists in the class (or null, if not)
     */
    public int getConnectionByConnection(Connection currentConnection) {
        for (int i = 0; i < connections.size(); i++) {
            if ((connections.get(i).x1 == currentConnection.x1 &&
                    connections.get(i).x2 == currentConnection.x2 &&
                    connections.get(i).y1 == currentConnection.y1 &&
                    connections.get(i).y2 == currentConnection.y2) ||
                    (connections.get(i).x1 == currentConnection.x2 &&
                            connections.get(i).x2 == currentConnection.x1 &&
                            connections.get(i).y1 == currentConnection.y2 &&
                            connections.get(i).y2 == currentConnection.y1)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Method that removes lines from the list of lines.
     * Called when an atom or radical associated with them is removed.
     * @param currentLines lines to delete
     */
    public void removeLines(ArrayList<Line> currentLines) {
        if (currentLines != null) {
            for (int i = 0; i < currentLines.size(); i++) {
                lines.remove(currentLines.get(i));
            }
        }
    }

    /**
     * Method that removes connections from the list of connections.
     * Called when an atom or radical associated with them is removed.
     * @param currentConnections connections to delete
     */
    public void removeConnections(ArrayList<Connection> currentConnections) {
        if (currentConnections != null) {
            for (int i = 0; i < currentConnections.size(); i++) {
                currentConnections.get(i).resetConnection();
                connections.remove(currentConnections.get(i));
            }
        }
    }

    /**
     * Method that returns the lines corresponding to this connection.
     * @param currentConnection connection that the search is based on
     * @return lines corresponding to this connection
     */
    public ArrayList<Line> searchLinesByConnection(Connection currentConnection) {
        if (currentConnection != null) {
            ArrayList<Line> currentLines = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++) {
                /*
                if ((lines.get(i).getStartX() == currentConnection.x1 && lines.get(i).getStartY() == currentConnection.y1 &&
                        lines.get(i).getEndX() == currentConnection.x2 && lines.get(i).getEndY() == currentConnection.y2) ||
                        (lines.get(i).getStartX() == currentConnection.x2 && lines.get(i).getStartY() == currentConnection.y2 &&
                                lines.get(i).getEndX() == currentConnection.x1 && lines.get(i).getEndY() == currentConnection.y1)) {
                    currentLines.add(lines.get(i));
                }
                 */
                if (doesLineMatchConnection(lines.get(i), currentConnection)) {
                    currentLines.add(lines.get(i));
                }
            }
            return currentLines;
        }
        return null;
    }

    /**
     * Method that determines whether the line and connection match each other.
     * @param line line being checked
     * @param connection connection being checked
     * @return true(if they match), false(if not)
     */
    public boolean doesLineMatchConnection(Line line, Connection connection) {
        boolean answer1 = (line.getStartX() - connection.x1)*(line.getStartX() - connection.x1) +
                (line.getStartY() - connection.y1)*(line.getStartY() - connection.y1) < 6;
        answer1 = answer1 && ((line.getEndX() - connection.x2)*(line.getEndX() - connection.x2) +
                (line.getEndY() - connection.y2)*(line.getEndY() - connection.y2) < 6);
        boolean answer2 = (line.getStartX() - connection.x2)*(line.getStartX() - connection.x2) +
                (line.getStartY() - connection.y2)*(line.getStartY() - connection.y2) < 6;
        answer2 = answer2 && ((line.getEndX() - connection.x1)*(line.getEndX() - connection.x1) +
                (line.getEndY() - connection.y1)*(line.getEndY() - connection.y1) < 6);
        return answer1 || answer2;
    }

    /**
     * Method that returns a list of connections containing this atom.
     * @param currentAtom the atom you are looking for
     * @return list of connections containing this atom
     */
    public ArrayList<Connection> searchConnectionsByAtom(Atom currentAtom) {
        ArrayList<Connection> currentConnections = new ArrayList<>();
        for (int i = 0; i < connections.size(); i++) {
            if (connections.get(i).containsAtom(currentAtom)){
                currentConnections.add(connections.get(i));
            }
        }
        return currentConnections;
    }

    /**
     * Method that returns a list of connections containing this radical.
     * @param currentRadical the radical you are looking for
     * @return list of connections containing this radical
     */
    public ArrayList<Connection> searchConnectionsByRadical(Radical currentRadical) {
        ArrayList<Connection> currentConnections = new ArrayList<>();
        for (int i = 0; i < connections.size(); i++) {
            if (connections.get(i).containsRadical(currentRadical)){
                currentConnections.add(connections.get(i));
            }
        }
        return currentConnections;
    }
}
