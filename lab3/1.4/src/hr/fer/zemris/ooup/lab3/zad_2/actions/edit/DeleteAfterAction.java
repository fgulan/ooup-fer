package hr.fer.zemris.ooup.lab3.zad_2.actions.edit;

import hr.fer.zemris.ooup.lab3.zad_2.editor.TextEditorModel;
import hr.fer.zemris.ooup.lab3.zad_2.position.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class DeleteAfterAction implements IEditAction {

    private TextEditorModel model;
    private List<String> undoLines;
    private Location undoCursorLocation;

    public DeleteAfterAction(TextEditorModel model) {
        this.model = model;
    }

    @Override
    public void executeDo() {
        List<String> lines = model.getLines();
        Location cursorLocation = model.getCursorLocation();

        undoCursorLocation = new Location(cursorLocation);
        undoLines = new ArrayList<>(lines);

        int currentIndex = cursorLocation.getLine();
        String currentLine = lines.get(currentIndex);
        if (cursorLocation.getColumn() == currentLine.length()) {
            if (lines.size() <= 1) {
                return;
            }

            lines.set(cursorLocation.getLine(), currentLine + lines.get(currentIndex + 1));
            lines.remove(currentIndex + 1);
        } else {
            StringBuilder builder = new StringBuilder(currentLine);
            builder.deleteCharAt(cursorLocation.getColumn());
            lines.set(cursorLocation.getLine(), builder.toString());
        }

        model.setLines(lines);
        model.notifyTextObservers();
    }

    @Override
    public void executeUndo() {
        model.setLines(undoLines);
        model.setCursorLocation(undoCursorLocation);
        model.notifyTextObservers();
        model.notifyCursorObservers();
    }
}
