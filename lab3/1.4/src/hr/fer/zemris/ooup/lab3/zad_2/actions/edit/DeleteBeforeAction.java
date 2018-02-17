package hr.fer.zemris.ooup.lab3.zad_2.actions.edit;

import hr.fer.zemris.ooup.lab3.zad_2.editor.TextEditorModel;
import hr.fer.zemris.ooup.lab3.zad_2.position.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class DeleteBeforeAction implements IEditAction {

    private TextEditorModel model;
    private List<String> undoLines;
    private Location undoCursorLocation;

    public DeleteBeforeAction(TextEditorModel model) {
        this.model = model;
    }

    @Override
    public void executeDo() {
        List<String> lines = model.getLines();
        Location cursorLocation = model.getCursorLocation();

        undoCursorLocation = new Location(cursorLocation);
        undoLines = new ArrayList<>(lines);

        if (cursorLocation.equals(new Location())) {
            return;
        }

        if (cursorLocation.getColumn() == 0) {
            int previousLineIndex = cursorLocation.getLine() - 1;
            String previousLine = lines.get(previousLineIndex);
            lines.set(previousLineIndex, previousLine + lines.get(cursorLocation.getLine()));
            lines.remove(cursorLocation.getLine());
            cursorLocation.update(-1, previousLine.length());
        } else {
            StringBuilder builder = new StringBuilder(lines.get(cursorLocation.getLine()));
            builder.deleteCharAt(cursorLocation.getColumn() - 1);
            lines.set(cursorLocation.getLine(), builder.toString());
            cursorLocation.update(0, -1);
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
        model.notifyCursorObservers();
        model.notifyTextObservers();
    }
}
