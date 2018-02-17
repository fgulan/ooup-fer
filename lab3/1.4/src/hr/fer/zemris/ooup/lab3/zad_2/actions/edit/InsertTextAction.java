package hr.fer.zemris.ooup.lab3.zad_2.actions.edit;

import hr.fer.zemris.ooup.lab3.zad_2.editor.TextEditorModel;
import hr.fer.zemris.ooup.lab3.zad_2.position.Location;
import hr.fer.zemris.ooup.lab3.zad_2.position.LocationRange;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class InsertTextAction implements IEditAction {

    private List<String> undoLines;
    private Location undoCursorLocation;
    private LocationRange undoSelectionRange;

    private TextEditorModel model;
    private String text;

    public InsertTextAction(TextEditorModel model, String text) {
        this.model = model;
        this.text = text;
    }

    @Override
    public void executeDo() {
        LocationRange selectionRange = model.getSelectionRange();
        model.deleteRange(selectionRange);

        List<String> lines = model.getLines();
        Location cursorLocation = model.getCursorLocation();

        undoLines = new ArrayList<>(lines);
        undoCursorLocation = new Location(cursorLocation);
        undoSelectionRange = null;
        if (selectionRange != null) {
            undoSelectionRange =  new LocationRange(selectionRange);
        }

        if (text == null || text.isEmpty()) {
            return;
        }

        int line = cursorLocation.getLine();
        int column = cursorLocation.getColumn();
        String[] textLines = text.split(System.lineSeparator());
        String prefix = lines.get(line).substring(0, column);
        String suffix = lines.get(line).substring(column);

        if (textLines.length == 1) {
            lines.set(line, prefix + textLines[0] + suffix);
            int endNewLines = countEndingNewLines(text);
            for (int i = 1; i <= endNewLines; i++) {
                lines.add(line + i, "");
            }
            if (endNewLines == 0) {
                cursorLocation.update(0, textLines[0].length());
            } else {
                cursorLocation = new Location(line + textLines.length - 1 + endNewLines, 0);
            }
        } else {
            lines.set(line, prefix + textLines[0]);
            for (int i = 1, size = textLines.length - 1; i < size; i++) {
                lines.add(line + i, textLines[i]);
            }
            lines.add(line + textLines.length - 1, textLines[textLines.length - 1] + suffix);
            int endNewLines = countEndingNewLines(text);
            for (int i = 0; i < endNewLines; i++) {
                lines.add(line + textLines.length + i, "");
            }
            if (endNewLines == 0) {
                int newColumn = textLines[textLines.length - 1].length() + suffix.length();
                cursorLocation = new Location(line + textLines.length - 1, newColumn);
            } else {
                cursorLocation = new Location(line + textLines.length - 1 + endNewLines, 0);
            }
        }

        model.setLines(lines);
        model.setCursorLocation(cursorLocation);
        model.notifyCursorObservers();
        model.notifyTextObservers();
    }

    private int countEndingNewLines(String text) {
        int counter = 0;
        for (int i = text.length() - 1; i >= 0; --i) {
            if (text.charAt(i) == '\n') {
                counter++;
            } else {
                break;
            }
        }
        return counter;
    }

    @Override
    public void executeUndo() {
        model.setLines(undoLines);
        model.setCursorLocation(undoCursorLocation);
        model.setSelectionRange(undoSelectionRange);
        model.notifyCursorObservers();
        model.notifyTextObservers();
    }
}
