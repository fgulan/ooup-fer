package hr.fer.zemris.ooup.lab3.zad_2.actions.edit;

import hr.fer.zemris.ooup.lab3.zad_2.editor.TextEditorModel;
import hr.fer.zemris.ooup.lab3.zad_2.position.Location;
import hr.fer.zemris.ooup.lab3.zad_2.position.LocationRange;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class InsertCharacterAction implements IEditAction {

    private List<String> undoLines;
    private Location undoCursorLocation;
    private LocationRange undoSelectionRange;

    private TextEditorModel model;
    private char c;

    public InsertCharacterAction(TextEditorModel model, char c) {
        this.model = model;
        this.c = c;
    }

    @Override
    public void executeDo() {
        LocationRange selectionRange = model.getSelectionRange();
        if (selectionRange != null) {
            new DeleteRangeAction(model, selectionRange).executeDo();
        }

        List<String> lines = model.getLines();
        Location cursorLocation = model.getCursorLocation();

        undoLines = new ArrayList<>(lines);
        undoCursorLocation = new Location(cursorLocation);
        undoSelectionRange = null;
        if (selectionRange != null) {
            undoSelectionRange =  new LocationRange(selectionRange);
        }

        if (String.valueOf(c).equals(System.lineSeparator())) {
            List<String> newLines = new ArrayList<>();
            for (int i = 0, size = lines.size(); i < size; i++) {
                if (cursorLocation.getLine() == i) {
                    newLines.add(lines.get(i).substring(0, cursorLocation.getColumn()));
                    newLines.add(lines.get(i).substring(cursorLocation.getColumn()));
                } else {
                    newLines.add(lines.get(i));
                }
            }
            cursorLocation.setLocation(cursorLocation.getLine() + 1, 0);
            lines = newLines;
        } else {
            StringBuilder builder = new StringBuilder(lines.get(cursorLocation.getLine()));
            builder.insert(cursorLocation.getColumn(), c);
            lines.set(cursorLocation.getLine(), builder.toString());
            cursorLocation.update(0, 1);
        }

        model.setLines(lines);
        model.setCursorLocation(cursorLocation);

        model.notifyCursorObservers();
        model.notifyTextObservers();
    }

    @Override
    public void executeUndo() {
        model.setLines(undoLines);
        model.setCursorLocation(undoCursorLocation);
        model.setSelectionRange(undoSelectionRange);
        model.notifyCursorObservers();
        model.notifyTextObservers();
        model.notifySelectionObservers();
    }
}
