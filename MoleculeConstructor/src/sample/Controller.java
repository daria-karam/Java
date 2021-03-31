package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;

/**
 * Controller class for the GUI.
 * Contains fields and methods required for working with data and handling events related to the GUI.
 */
public class Controller implements EventHandler<MouseEvent>{
    @FXML
    public Rectangle H;
    @FXML
    public Rectangle N;
    @FXML
    public Rectangle C;
    @FXML
    public Rectangle O;
    @FXML
    public Rectangle connection;
    @FXML
    public Rectangle radical;
    @FXML
    public Rectangle remove;
    @FXML
    public AnchorPane pane;
    @FXML
    public MenuItem save;
    @FXML
    public MenuItem load;
    @FXML
    public Circle currentAtomOrRadical = new Circle();

    public Data data = new Data();

    public boolean isH = false;
    public boolean isN = false;
    public boolean isC = false;
    public boolean isO = false;
    public boolean isConnection = false;
    public boolean isRadical = false;
    public boolean isRemove = false;
    public boolean isCurrentAtomOrRadical = false;

    public Atom firstAtom = null;
    public Atom secondAtom = null;
    public Radical firstRadical = null;
    public Radical secondRadical = null;

    /**
     * Method that calls when the "hydrogen" option is selected.
     * Sets the appropriate flags and changes the color of the corresponding objects.
     */
    @FXML
    public void choiceH() {
        setAllOptionsFalse();
        isH = true;
        setFillToRectanglesAndResetConnectionOptions();
        System.out.println("Choice: H");
    }

    /**
     * Method that calls when the "nitrogen" option is selected.
     * Sets the appropriate flags and changes the color of the corresponding objects.
     */
    @FXML
    public void choiceN() {
        setAllOptionsFalse();
        isN = true;
        setFillToRectanglesAndResetConnectionOptions();
        System.out.println("Choice: N");
    }

    /**
     * Method that calls when the "carbon" option is selected.
     * Sets the appropriate flags and changes the color of the corresponding objects.
     */
    @FXML
    public void choiceC() {
        setAllOptionsFalse();
        isC = true;
        setFillToRectanglesAndResetConnectionOptions();
        System.out.println("Choice: C");
    }

    /**
     * Method that calls when the "oxygen" option is selected.
     * Sets the appropriate flags and changes the color of the corresponding objects.
     */
    @FXML
    public void choiceO() {
        setAllOptionsFalse();
        isO = true;
        setFillToRectanglesAndResetConnectionOptions();
        System.out.println("Choice: O");
    }

    /**
     * Method that calls when the "connection" option is selected.
     * Sets the appropriate flags and changes the color of the corresponding objects.
     */
    @FXML
    public void choiceConnection() {
        setAllOptionsFalse();
        isConnection = true;
        setFillToRectanglesAndResetConnectionOptions();
        System.out.println("Choice: connection");
    }

    /**
     * Method that calls when the "radical" option is selected.
     * Sets the appropriate flags and changes the color of the corresponding objects.
     */
    @FXML
    public void choiceRadical() {
        setAllOptionsFalse();
        isRadical = true;
        setFillToRectanglesAndResetConnectionOptions();
        System.out.println("Choice: radical");
    }

    /**
     * Method that calls when the "remove" option is selected.
     * Sets the appropriate flags and changes the color of the corresponding objects.
     */
    @FXML
    public void choiceRemove() {
        setAllOptionsFalse();
        isRemove = true;
        setFillToRectanglesAndResetConnectionOptions();
        System.out.println("Choice: remove");
    }

    /**
     * Method that resets all flags.
     */
    public void setAllOptionsFalse() {
        isH = false;
        isN = false;
        isC = false;
        isO = false;
        isConnection = false;
        isRadical = false;
        isRemove = false;
    }

    /**
     * Method that changes the colors of objects (buttons).
     * Also, it blanks the values of the following fields:
     * firstAtom, secondAtom, firstRadical, secondRadical.
     */
    public void setFillToRectanglesAndResetConnectionOptions() {
        Paint ifTrue = Paint.valueOf("#e0ffff");
        Paint ifFalse = Paint.valueOf("#b0e0e6");
        H.setFill(ifFalse);
        N.setFill(ifFalse);
        C.setFill(ifFalse);
        O.setFill(ifFalse);
        connection.setFill(ifFalse);
        radical.setFill(ifFalse);
        remove.setFill(ifFalse);
        if (isH) H.setFill(ifTrue);
        if (isN) N.setFill(ifTrue);
        if (isC) C.setFill(ifTrue);
        if (isO) O.setFill(ifTrue);
        if (isConnection) connection.setFill(ifTrue);
        if (isRadical) radical.setFill(ifTrue);
        if (isRemove) remove.setFill(ifTrue);

        firstAtom = null;
        secondAtom = null;
        firstRadical = null;
        secondRadical = null;
        if (isCurrentAtomOrRadical) {
            pane.getChildren().remove(currentAtomOrRadical);
            isCurrentAtomOrRadical = false;
        }
    }

    /**
     * The method that will handle events of the mouse.
     * @param event the intercepted event
     */
    @FXML
    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if ((event.getX() < 500 - 16) && (event.getY() >= 45) && (event.getY() <= 550 - 16) && (event.getX() >= 16)) {
                if (isC || isN || isH || isO) {
                    printAtom(event.getX(), event.getY());
                }
                if (isConnection) {
                    printConnection(event.getX(), event.getY());
                }
                if (isRadical) {
                    printRadical(event.getX(), event.getY());
                }
                if (isRemove) {
                    removeAtomOrRadicalWithConnections(event.getX(), event.getY());
                }
            }
        }
    }

    /**
     * Method that removes an atom or radical and its connections and lines from the GUI and data.
     * @param x x-coordinate of the atom or radical
     * @param y y-coordinate of the atom or radical
     */
    public void removeAtomOrRadicalWithConnections(double x, double y) {
        Atom currentAtom = getAtomFromHere(x, y);
        Radical currentRadical = getRadicalFromHere(x, y);
        if (currentAtom != null) {
            ArrayList<Connection> currentConnections = data.searchConnectionsByAtom(currentAtom);
            ArrayList<Line> currentLines = new ArrayList<>();
            for (int i = 0; i < currentConnections.size(); i++) {
                ArrayList<Line> currentLines1 = data.searchLinesByConnection(currentConnections.get(i));
                for (int j = 0; j < currentLines1.size(); j++) {
                    currentLines.add(currentLines1.get(j));
                }
            }
            for (int i = 0; i < currentLines.size(); i++) {
                pane.getChildren().remove(currentLines.get(i));
            }
            pane.getChildren().remove(currentAtom);
            data.removeConnections(currentConnections);
            data.removeLines(currentLines);
            data.atoms.remove(currentAtom);
        }
        if (currentRadical != null) {
            ArrayList<Connection> currentConnections = data.searchConnectionsByRadical(currentRadical);
            ArrayList<Line> currentLines = new ArrayList<>();
            for (int i = 0; i < currentConnections.size(); i++) {
                ArrayList<Line> currentLines1 = data.searchLinesByConnection(currentConnections.get(i));
                for (int j = 0; j < currentLines1.size(); j++) {
                    currentLines.add(currentLines1.get(j));
                }
            }
            for (int i = 0; i < currentLines.size(); i++) {
                pane.getChildren().remove(currentLines.get(i));
            }
            pane.getChildren().remove(data.radical);
            pane.getChildren().remove(data.radical.radicalLabel);
            data.removeConnections(currentConnections);
            data.removeLines(currentLines);
            data.radical = null;
        }
    }

    /**
     * A method that draws an atom with specific coordinates, if it can be drawn there.
     * Also adds data about it to the database.
     * @param x x-coordinate of the center of the atom
     * @param y y-coordinate of the center of the atom
     */
    public void printAtom(double x, double y) {
        String type = null;
        if (isH) {
            type = "H";
            if (!canPrintAtomHere(x, y, 12)) {
                return;
            }
        }
        if (isN) {
            type = "N";
            if (!canPrintAtomHere(x, y, 14)) {
                return;
            }
        }
        if (isC) {
            type = "C";
            if (!canPrintAtomHere(x, y, 14)) {
                return;
            }
        }
        if (isO) {
            type = "O";
            if (!canPrintAtomHere(x, y, 16)) {
                return;
            }
        }
        if (type == null) {
            return;
        }
        Atom atom = new Atom(type, x, y);
        pane.getChildren().add(atom);
        data.atoms.add(atom);
    }

    /**
     * Method that draws a connection if an atom or radical is located at these coordinates.
     * @param x x-coordinate of the atom or radical
     * @param y y-coordinate of the atom or radical
     */
    public void printConnection(double x, double y) {
        try {
            System.out.println("--begin--");
            if (firstAtom == null && firstRadical == null) {
                firstAtom = getAtomFromHere(x, y);
                firstRadical = getRadicalFromHere(x, y);
                if (firstAtom != null) {
                    currentAtomOrRadical.setCenterX(firstAtom.getCenterX());
                    currentAtomOrRadical.setCenterY(firstAtom.getCenterY());
                    currentAtomOrRadical.setRadius(3);
                    currentAtomOrRadical.setFill(Paint.valueOf("RED"));
                    pane.getChildren().add(currentAtomOrRadical);
                    isCurrentAtomOrRadical = true;
                } else {
                    if (firstRadical != null) {
                        currentAtomOrRadical.setCenterX(firstRadical.centerX);
                        currentAtomOrRadical.setCenterY(firstRadical.centerY);
                        currentAtomOrRadical.setRadius(3);
                        currentAtomOrRadical.setFill(Paint.valueOf("RED"));
                        pane.getChildren().add(currentAtomOrRadical);
                        isCurrentAtomOrRadical = true;
                    }
                }
                System.out.println("first");
                return;
            }
            System.out.println("second");
            secondAtom = getAtomFromHere(x, y);
            secondRadical = getRadicalFromHere(x, y);
            Connection newConnection = null;
            if (firstAtom != null && secondAtom != null && firstAtom != secondAtom) {
                newConnection = new Connection(firstAtom, secondAtom, null);
                if (newConnection.canAddNewConnection()) {
                    int index = data.getConnectionByConnection(newConnection);
                    if (index == -1) {
                        newConnection.setConnection();
                        data.connections.add(newConnection);
                        Line line = new Line();
                        line.setStartX(firstAtom.getCenterX());
                        line.setStartY(firstAtom.getCenterY());
                        line.setEndX(secondAtom.getCenterX());
                        line.setEndY(secondAtom.getCenterY());
                        line.setStrokeWidth(2);
                        data.lines.add(line);
                        pane.getChildren().add(line);
                    } else {
                        data.connections.get(index).setConnection();
                        ArrayList<Line> currentLines = data.searchLinesByConnection(data.connections.get(index));
                        for (int i = 0; i < currentLines.size(); i++) {
                            pane.getChildren().remove(currentLines.get(i));
                        }
                        printConnectionWithCapacitySwitch(index);
                    }
                    pane.getChildren().remove(firstAtom);
                    pane.getChildren().remove(secondAtom);
                    pane.getChildren().add(firstAtom);
                    pane.getChildren().add(secondAtom);
                }
            } else {
                if (firstAtom != null && secondRadical != null) {
                    newConnection = new Connection(firstAtom, null, secondRadical);
                    if (newConnection.canAddNewConnection()) {
                        int index = data.getConnectionByConnection(newConnection);
                        if (index == -1) {
                            newConnection.setConnection();
                            data.connections.add(newConnection);
                            Line line = new Line();
                            line.setStartX(firstAtom.getCenterX());
                            line.setStartY(firstAtom.getCenterY());
                            line.setEndX(secondRadical.getX() + 16);
                            line.setEndY(secondRadical.getY() + 16);
                            line.setStrokeWidth(2);
                            line.setFill(Paint.valueOf("BLACK"));
                            data.lines.add(line);
                            pane.getChildren().add(line);
                        } else {
                            data.connections.get(index).setConnection();
                            printConnectionWithCapacitySwitch(index);
                        }
                        pane.getChildren().remove(firstAtom);
                        pane.getChildren().remove(secondRadical);
                        pane.getChildren().remove(data.radical.radicalLabel);
                        pane.getChildren().add(firstAtom);
                        pane.getChildren().add(secondRadical);
                        pane.getChildren().add(data.radical.radicalLabel);
                    }
                } else {
                    if (firstRadical != null && secondAtom != null) {
                        newConnection = new Connection(secondAtom, null, firstRadical);
                        if (newConnection.canAddNewConnection()) {
                            int index = data.getConnectionByConnection(newConnection);
                            if (index == -1) {
                                newConnection.setConnection();
                                data.connections.add(newConnection);
                                Line line = new Line();
                                line.setStartX(secondAtom.getCenterX());
                                line.setStartY(secondAtom.getCenterY());
                                line.setEndX(firstRadical.getX() + 16);
                                line.setEndY(firstRadical.getY() + 16);
                                line.setStrokeWidth(2);
                                line.setFill(Paint.valueOf("BLACK"));
                                data.lines.add(line);
                                pane.getChildren().add(line);
                            } else {
                                data.connections.get(index).setConnection();
                                printConnectionWithCapacitySwitch(index);
                            }
                            pane.getChildren().remove(secondAtom);
                            pane.getChildren().remove(firstRadical);
                            pane.getChildren().remove(data.radical.radicalLabel);
                            pane.getChildren().add(secondAtom);
                            pane.getChildren().add(firstRadical);
                            pane.getChildren().add(data.radical.radicalLabel);
                        }
                    }
                }
            }
            if (isCurrentAtomOrRadical) {
                pane.getChildren().remove(currentAtomOrRadical);
                isCurrentAtomOrRadical = false;
            }
            firstAtom = null;
            firstRadical = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method that draws a line with a specific capacity.
     * (i.e., a certain number of lines)
     * @param index connection index in the list of connections
     */
    public void printConnectionWithCapacitySwitch(int index) {
        switch(data.connections.get(index).capacity) {
            case 1:
                Line line11 = getLineWithParameters(data.connections.get(index).x1, data.connections.get(index).y1,
                        data.connections.get(index).x2, data.connections.get(index).y2, Paint.valueOf("BLACK"), 2);
                data.lines.add(line11);
                pane.getChildren().add(line11);
                break;
            case 2:
                Line line21 = getLineWithParameters(data.connections.get(index).x1, data.connections.get(index).y1,
                        data.connections.get(index).x2, data.connections.get(index).y2, Paint.valueOf("BLACK"), 6);
                data.lines.add(line21);
                pane.getChildren().add(line21);
                Line line22 = getLineWithParameters(data.connections.get(index).x1, data.connections.get(index).y1,
                        data.connections.get(index).x2, data.connections.get(index).y2, Paint.valueOf("WHITE"), 2);
                data.lines.add(line22);
                pane.getChildren().add(line22);
                break;
            case 3:
                Line line31 = getLineWithParameters(data.connections.get(index).x1, data.connections.get(index).y1,
                        data.connections.get(index).x2, data.connections.get(index).y2, Paint.valueOf("BLACK"), 10);
                data.lines.add(line31);
                pane.getChildren().add(line31);
                Line line32 =getLineWithParameters(data.connections.get(index).x1, data.connections.get(index).y1,
                        data.connections.get(index).x2, data.connections.get(index).y2, Paint.valueOf("WHITE"), 6);
                data.lines.add(line32);
                pane.getChildren().add(line32);
                Line line33 = getLineWithParameters(data.connections.get(index).x1, data.connections.get(index).y1,
                        data.connections.get(index).x2, data.connections.get(index).y2, Paint.valueOf("BLACK"), 2);
                data.lines.add(line33);
                pane.getChildren().add(line33);
                break;
            case 4:
                Line line41 = getLineWithParameters(data.connections.get(index).x1, data.connections.get(index).y1,
                        data.connections.get(index).x2, data.connections.get(index).y2, Paint.valueOf("BLACK"), 14);
                data.lines.add(line41);
                pane.getChildren().add(line41);
                Line line42 = getLineWithParameters(data.connections.get(index).x1, data.connections.get(index).y1,
                        data.connections.get(index).x2, data.connections.get(index).y2, Paint.valueOf("WHITE"), 10);
                data.lines.add(line42);
                pane.getChildren().add(line42);
                Line line43 = getLineWithParameters(data.connections.get(index).x1, data.connections.get(index).y1,
                        data.connections.get(index).x2, data.connections.get(index).y2, Paint.valueOf("BLACK"), 6);
                data.lines.add(line43);
                pane.getChildren().add(line43);
                Line line44 = getLineWithParameters(data.connections.get(index).x1, data.connections.get(index).y1,
                        data.connections.get(index).x2, data.connections.get(index).y2, Paint.valueOf("WHITE"), 2);
                data.lines.add(line44);
                pane.getChildren().add(line44);
                break;
            default:
                System.out.println("something wrong");
                break;
        }
    }

    /**
     * Returns an object of the Line class with appropriately initialized fields and properties.
     * @param x1 start x-coordinate
     * @param y1 start y-coordinate
     * @param x2 end x-coordinate
     * @param y2 end y-coordinate
     * @param paint line color
     * @param strokeWidth line width
     * @return line with these parameters
     */
    public Line getLineWithParameters(double x1, double y1, double x2, double y2, Paint paint, int strokeWidth) {
        Line line = new Line();
        line.setStartX(x1);
        line.setStartY(y1);
        line.setEndX(x2);
        line.setEndY(y2);
        line.setStrokeWidth(strokeWidth);
        line.setStroke(paint);
        return line;
    }

    /**
     * A method that draws an atom with specific coordinates, if it can be drawn there.
     * Also adds data about it to the database.
     * @param x x-coordinate of the center of the radical
     * @param y y-coordinate of the center of the radical
     */
    public void printRadical(double x, double y) {
        if (data.radical == null) {
            if (!canPrintRadicalHere(x, y)) {
                return;
            }
            Radical radical = new Radical(x, y);
            pane.getChildren().add(radical);
            pane.getChildren().add(radical.radicalLabel);
            data.radical = radical;
        }
    }

    /**
     * Method for searching for an atom by coordinates.
     * @param x x-coordinate of the atom
     * @param y y-coordinate of the atom
     * @return an atom, if there is one; null(if not)
     */
    public Atom getAtomFromHere(double x, double y) {
        for (int i = 0; i < data.atoms.size(); i++) {
            if ((x - data.atoms.get(i).getCenterX())*(x - data.atoms.get(i).getCenterX()) +
                    (y - data.atoms.get(i).getCenterY())*(y - data.atoms.get(i).getCenterY()) <
                    data.atoms.get(i).getRadius()*data.atoms.get(i).getRadius()) {
                System.out.println("find atom success");
                return data.atoms.get(i);
            }
        }
        System.out.println("find atom NOT success");
        return null;
    }

    /**
     * Method for searching for an radical by coordinates.
     * @param x x-coordinate of the radical
     * @param y y-coordinate of the radical
     * @return an radical(if there is one), null(if not)
     */
    public Radical getRadicalFromHere(double x, double y) {
        if ((data.radical != null) && (data.radical.getX() < x) && (data.radical.getX() + 32 > x) &&
                (data.radical.getY() < y) && (data.radical.getY() + 32 > y)) {
            System.out.println("find radical success");
            return data.radical;
        } else {
            System.out.println("find radical NOT success");
            return null;
        }
    }

    /**
     * Method that checks whether an atom can be drawn at a given point.
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     * @param radius radius of the future atom
     * @return true(if can), false(if can't)
     */
    public boolean canPrintAtomHere(double x, double y, double radius) {
        for (int i = 0; i < data.atoms.size(); i++) {
            if ((x - data.atoms.get(i).getCenterX())*(x - data.atoms.get(i).getCenterX()) +
                    (y - data.atoms.get(i).getCenterY())*(y - data.atoms.get(i).getCenterY()) <
                    (data.atoms.get(i).getRadius() + radius + 2)*(data.atoms.get(i).getRadius() + radius + 2)) {
                return false;
            }
        }
        if ((data.radical != null) && ((data.radical.getX() + 16 - x)*(data.radical.getX() + 16 - x) +
                (data.radical.getY() + 16 - y)*(data.radical.getY() + 16 - y) < 25*25)) {
            return false;
        }
        return true;
    }

    /**
     * Method that checks whether an radical can be drawn at a given point.
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     * @return true(if can), false(if can't)
     */
    public boolean canPrintRadicalHere(double x, double y) {
        if (data.radical != null) {
            return false;
        }
        for (int i = 0; i < data.atoms.size(); i++) {
            if ((x - data.atoms.get(i).getCenterX())*(x - data.atoms.get(i).getCenterX()) +
                    (y - data.atoms.get(i).getCenterY())*(y - data.atoms.get(i).getCenterY()) <
                    (data.atoms.get(i).getRadius() + 25)*(data.atoms.get(i).getRadius() + 25)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method that handles calling the "save" event.
     * Opens the file selection menu and calls the method for serializing data to this file.
     */
    @FXML
    public void clickSave() {
        try {
            System.out.println("Choice: save");
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select file to save to");
            File file = chooser.showOpenDialog(new Stage());
            if (file != null) {
                saveToFile(file);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method that handles calling the "load" event.
     * Opens the file selection menu and calls the method for deserializing data from this file.
     */
    @FXML
    public void clickLoad() {
        try {
            System.out.println("Choice: load");
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select file to load from");
            File file = chooser.showOpenDialog(new Stage());
            if (file != null) {
                loadFromFile(file);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method for loading data from a file. Uses deserialization.
     * @param file file to download from
     */
    public void loadFromFile(File file) {
        try {
            System.out.println("load started...");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            removeAllData();
            data = (Data) ois.readObject();
            ois.close();
            fis.close();
            System.out.println("load success");
            updateAllDataAfterDownload();
            printAllData();
        } catch (FileNotFoundException e) {
            System.out.println("Exception: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Method for saving data to a file. Uses the serialization.
     * @param file file to save to
     */
    public void saveToFile(File file) {
        FileOutputStream fos;
        try {
            System.out.println("save started...");
            fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            fos.close();
            System.out.println("save success");
        }
        catch (FileNotFoundException e) {
            System.out.println("Exception: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Method that clears all data from the database. Also clears the GUI window.
     */
    public void removeAllData() {
        try {
            System.out.println("begin remove...");
            if (data.lines != null && !data.lines.isEmpty()) {
                for (int i = 0; i < data.lines.size(); i++) {
                    pane.getChildren().remove(data.lines.get(i));
                }
                System.out.println("lines removed");
            }
            if (data.atoms != null && !data.atoms.isEmpty()) {
                for (int i = 0; i < data.atoms.size(); i++) {
                    pane.getChildren().remove(data.atoms.get(i));
                }
                System.out.println("atoms removed");
            }
            if (data.radical != null) {
                pane.getChildren().remove(data.radical);
                if (data.radical.radicalLabel != null) {
                    pane.getChildren().remove(data.radical.radicalLabel);
                    System.out.println("radical removed");
                }
            }
            System.out.println("end remove");
            data.clearAllData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method that displays all data from the database in the GUI window.
     */
    public void printAllData() {
        try {
            System.out.println("begin printing...");
            if (data.lines != null && !(data.lines.isEmpty())) {
                for (int i = 0; i < data.lines.size(); i++) {
                    pane.getChildren().add(data.lines.get(i));
                }
                System.out.println("lines printed");
            }
            if (data.atoms != null && !data.atoms.isEmpty()) {
                for (int i = 0; i < data.atoms.size(); i++) {
                    pane.getChildren().add(data.atoms.get(i));
                }
                System.out.println("atoms printed");
            }
            if (data.radical != null) {
                pane.getChildren().add(data.radical);
                if (data.radical.radicalLabel != null) {
                    pane.getChildren().add(data.radical.radicalLabel);
                    System.out.println("radical printed");
                }
            }
            System.out.println("end printing");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method that updates data from the database.
     * Used to restore part of the data after deserialization.
     */
    public void updateAllDataAfterDownload() {
        try {
            System.out.println("update data started...");
            if (data.atoms != null) {
                for (int i = 0; i < data.atoms.size(); i++) {
                    data.atoms.get(i).update();
                }
            }
            System.out.println("update atoms done");
            if (data.radical != null) {
                data.radical.update();
            }
            System.out.println("update radical done");
            data.lines = new ArrayList<>();
            if (data.connections != null) {
                for (int i = 0; i < data.connections.size(); i++) {
                    addLinesByConnections(i);
                }
            }
            System.out.println("update lines done");
            System.out.println("update data end");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method that complements the list of lines from the database with lines corresponding to this connection.
     * Used to restore part of the data after deserialization.
     * @param index index of the connection in the list of connections from the database
     */
    public void addLinesByConnections(int index) {
        try {
            switch (data.connections.get(index).capacity) {
                case 1:
                    Line line11 = getLineWithParameters(data.connections.get(index).x1, data.connections.get(index).y1,
                            data.connections.get(index).x2, data.connections.get(index).y2, Paint.valueOf("BLACK"), 2);
                    data.lines.add(line11);
                    break;
                case 2:
                    Line line21 = getLineWithParameters(data.connections.get(index).x1, data.connections.get(index).y1,
                            data.connections.get(index).x2, data.connections.get(index).y2, Paint.valueOf("BLACK"), 6);
                    data.lines.add(line21);
                    Line line22 = getLineWithParameters(data.connections.get(index).x1, data.connections.get(index).y1,
                            data.connections.get(index).x2, data.connections.get(index).y2, Paint.valueOf("WHITE"), 2);
                    data.lines.add(line22);
                    break;
                case 3:
                    Line line31 = getLineWithParameters(data.connections.get(index).x1, data.connections.get(index).y1,
                            data.connections.get(index).x2, data.connections.get(index).y2, Paint.valueOf("BLACK"), 10);
                    data.lines.add(line31);
                    Line line32 = getLineWithParameters(data.connections.get(index).x1, data.connections.get(index).y1,
                            data.connections.get(index).x2, data.connections.get(index).y2, Paint.valueOf("WHITE"), 6);
                    data.lines.add(line32);
                    Line line33 = getLineWithParameters(data.connections.get(index).x1, data.connections.get(index).y1,
                            data.connections.get(index).x2, data.connections.get(index).y2, Paint.valueOf("BLACK"), 2);
                    data.lines.add(line33);
                    break;
                case 4:
                    Line line41 = getLineWithParameters(data.connections.get(index).x1, data.connections.get(index).y1,
                            data.connections.get(index).x2, data.connections.get(index).y2, Paint.valueOf("BLACK"), 14);
                    data.lines.add(line41);
                    Line line42 = getLineWithParameters(data.connections.get(index).x1, data.connections.get(index).y1,
                            data.connections.get(index).x2, data.connections.get(index).y2, Paint.valueOf("WHITE"), 10);
                    data.lines.add(line42);
                    Line line43 = getLineWithParameters(data.connections.get(index).x1, data.connections.get(index).y1,
                            data.connections.get(index).x2, data.connections.get(index).y2, Paint.valueOf("BLACK"), 6);
                    data.lines.add(line43);
                    Line line44 = getLineWithParameters(data.connections.get(index).x1, data.connections.get(index).y1,
                            data.connections.get(index).x2, data.connections.get(index).y2, Paint.valueOf("WHITE"), 2);
                    data.lines.add(line44);
                    break;
                default:
                    System.out.println("something wrong");
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
