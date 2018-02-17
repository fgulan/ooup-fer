package hr.fer.zemris.ooup.lab3.zad_2.editor;

import hr.fer.zemris.ooup.lab3.zad_2.actions.edit.*;
import hr.fer.zemris.ooup.lab3.zad_2.iterator.LinesIterator;
import hr.fer.zemris.ooup.lab3.zad_2.observers.cursor.ICursorObserver;
import hr.fer.zemris.ooup.lab3.zad_2.observers.selection.ISelectionObserver;
import hr.fer.zemris.ooup.lab3.zad_2.observers.stack.UndoManager;
import hr.fer.zemris.ooup.lab3.zad_2.observers.text.ITextObserver;
import hr.fer.zemris.ooup.lab3.zad_2.position.Location;
import hr.fer.zemris.ooup.lab3.zad_2.position.LocationRange;

import java.util.*;

/**
 * @author Filip Gulan
 */
public class TextEditorModel {

    private List<String> lines;
    private LocationRange selectionRange;
    private Location cursorLocation;

    // Observers
    private Set<ICursorObserver> cursorObservers;
    private Set<ITextObserver> textObservers;
    private Set<ISelectionObserver> selectionObservers;
    private UndoManager undoManager;
    public boolean shouldFolowCaret = false;

    public TextEditorModel(String defaultText) {

        this.lines = new ArrayList<>(Arrays.asList(defaultText.replaceAll("\\t", "    ").split("\n")));
        this.cursorObservers = new HashSet<>();
        this.textObservers = new HashSet<>();
        this.selectionObservers = new HashSet<>();

        this.cursorLocation = new Location();
        this.undoManager = UndoManager.getInstance();
    }

    public void reset(List<String> lines) {
        if (lines != null) {
            this.lines = new ArrayList<>(lines);
        } else {
            this.lines = new ArrayList<>();
            this.lines.add("");
        }
        this.cursorLocation = new Location();
        this.selectionRange = null;
        this.undoManager.clear();

        notifySelectionObservers();
        notifyTextObservers();
        notifyCursorObservers();
    }

    // Lines iterator

    public Iterator<String> allLines() {
        return new LinesIterator(lines);
    }

    public Iterator<String> linesRange(int start, int end) {
        return new LinesIterator(lines, start, end);
    }

    // Cursor observers

    public boolean addCursorObserver(ICursorObserver cursorObserver) {
        return cursorObservers.add(cursorObserver);
    }

    public boolean removeCursorObserver(ICursorObserver cursorObserver) {
        return cursorObservers.remove(cursorObserver);
    }

    public void notifyCursorObservers() {
        shouldFolowCaret = true;
        cursorObservers.forEach(cursorObserver ->
                                    cursorObserver.updateCursorLocation(cursorLocation));
    }

    // Selection observers

    public boolean addSelectionObserver(ISelectionObserver observer) {
        return selectionObservers.add(observer);
    }

    public boolean removeSelectionObserver(ISelectionObserver observer) {
        return selectionObservers.remove(observer);
    }

    public void notifySelectionObservers() {
        boolean hasSelection = hasSelection();
        selectionObservers.forEach((observer -> observer.selectionUpdate(hasSelection)));
    }

    public Location moveCursorLeft() {
        if (cursorLocation.getColumn() != 0) {
            cursorLocation.update(0, -1);
        } else if (cursorLocation.getLine() != 0) {
            int newX = cursorLocation.getLine() - 1;
            int newY = lines.get(newX).length();
            cursorLocation.setLocation(newX, newY);
        } else {
            return cursorLocation;
        }
        notifyCursorObservers();
        return cursorLocation;
    }

    public Location moveCursorRight() {
        int lineLength = lines.get(cursorLocation.getLine()).length();
        if (cursorLocation.getColumn() != lineLength) {
            cursorLocation.update(0, 1);
        } else if (cursorLocation.getLine() < lines.size() - 1) {
            int newX = cursorLocation.getLine() + 1;
            cursorLocation.setLocation(newX, 0);
        } else {
            return cursorLocation;
        }
        notifyCursorObservers();
        return cursorLocation;
    }

    public Location moveCursorUp() {
        if (cursorLocation.getLine() <= 0) {
            return cursorLocation;
        }

        int lineLength = lines.get(cursorLocation.getLine() - 1).length();
        if (cursorLocation.getColumn() > lineLength) {
            cursorLocation.setLocation(cursorLocation.getLine() - 1, lineLength);
        } else {
            cursorLocation.update(-1, 0);
        }

        notifyCursorObservers();
        return cursorLocation;
    }

    public Location moveCursorDown() {
        if (cursorLocation.getLine() >= lines.size() - 1) {
            return cursorLocation;
        }
        int lineLength = lines.get(cursorLocation.getLine() + 1).length();
        if (cursorLocation.getColumn() > lineLength) {
            cursorLocation.setLocation(cursorLocation.getLine() + 1, lineLength);
        } else {
            cursorLocation.update(1, 0);
        }

        notifyCursorObservers();
        return cursorLocation;
    }

    public void moveSelectionUp() {
        if (!hasSelection()) {
            selectionRange = new LocationRange(this.cursorLocation, this.cursorLocation);
        }
        selectionRange.setEnd(moveCursorUp());
        notifySelectionObservers();
    }

    public void moveSelectionDown() {
        if (!hasSelection()) {
            selectionRange = new LocationRange(this.cursorLocation, this.cursorLocation);
        }
        selectionRange.setEnd(moveCursorDown());
        notifySelectionObservers();
    }

    public void moveSelectionLeft() {
        if (!hasSelection()) {
            selectionRange = new LocationRange(this.cursorLocation, this.cursorLocation);
        }
        selectionRange.setEnd(moveCursorLeft());
        notifySelectionObservers();
    }

    public void moveSelectionRight() {
        if (!hasSelection()) {
            selectionRange = new LocationRange(this.cursorLocation, this.cursorLocation);
        }
        selectionRange.setEnd(moveCursorRight());
        notifySelectionObservers();
    }

    public boolean hasSelection() {
        return this.selectionRange != null;
    }

    // Text observers

    public boolean addTextObserver(ITextObserver textObserver) {
        if (!textObservers.contains(textObserver)) {
            return textObservers.add(textObserver);
        }
        return false;
    }

    public boolean removeTextObserver(ITextObserver textObserver) {
        return textObservers.remove(textObserver);
    }

    public void notifyTextObservers() {
        textObservers.forEach(textObserver -> {textObserver.updateText();});
    }

    // Delete actions

    public void deleteBefore() {
        IEditAction action = new DeleteBeforeAction(this);
        action.executeDo();
        undoManager.push(action);
    }

    public void deleteAfter() {
        IEditAction action = new DeleteAfterAction(this);
        action.executeDo();
        undoManager.push(action);
    }

    public void deleteRange(LocationRange range) {
        if (range == null) {
            return;
        }
        IEditAction action = new DeleteRangeAction(this, range);
        action.executeDo();
        undoManager.push(action);
    }

    // Text insert

    public void insert(char c) {
        IEditAction action = new InsertCharacterAction(this, c);
        action.executeDo();
        undoManager.push(action);
    }

    public void insert(String text) {
        if (text == null || text.isEmpty()) {
            return;
        }
        IEditAction action = new InsertTextAction(this, text.replaceAll("\\t", "    "));
        action.executeDo();
        undoManager.push(action);
    }

    public void clear() {
        IEditAction action = new ClearTextAction(this);
        action.executeDo();
        undoManager.push(action);
    }
    // Selection text getter

    public String getSelectionText() {
        if (!hasSelection()) {
            return null;
        }

        LocationRange range = getSelectionRange();
        int numberOfLines = range.numberOfLines();
        if (numberOfLines < 0 || (numberOfLines == 0
                && range.getStart().getColumn() > range.getEnd().getColumn())) {
            range.swap();
        }
        Location start = range.getStart();
        Location end = range.getEnd();
        if (numberOfLines == 0) {
            return lines.get(start.getLine()).substring(start.getColumn(), end.getColumn());
        } else {
            StringBuilder builder = new StringBuilder();
            String line = lines.get(start.getLine()).substring(start.getColumn());
            builder.append(line).append(System.lineSeparator());

            for (int i = start.getLine() + 1, size = end.getLine(); i < size ; i++) {
                builder.append(lines.get(i)).append(System.lineSeparator());
            }
            line = lines.get(end.getLine()).substring(0, end.getColumn());
            builder.append(line);
            return builder.toString();
        }
    }

    public void moveCursorToStart() {
        setCursorLocation(new Location());
    }

    public void moveCursorToEnd() {
        int lineIndex = lines.size() - 1;
        if (lineIndex < 0) {
            moveCursorToStart();
            return;
        }

        setCursorLocation(new Location(lineIndex, getLine(lineIndex).length()));
    }

    // Getters and setters

    public Location getCursorLocation() {
        return new Location(cursorLocation);
    }

    public void setCursorLocation(Location cursorLocation) {
        this.cursorLocation = cursorLocation;
        notifyCursorObservers();
    }

    public LocationRange getSelectionRange() {
        if (selectionRange == null) {
            return null;
        }
        return new LocationRange(selectionRange);
    }

    public void setSelectionRange(LocationRange range) {
        this.selectionRange = range;
        notifySelectionObservers();
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public String getLine(int index) {
        return lines.get(index);
    }

    public String getDocument() {
        StringBuilder builder = new StringBuilder();
        lines.forEach((line) -> {
            builder.append(line).append(System.lineSeparator());
        });
        return builder.toString();
    }

}
