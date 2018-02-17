package hr.fer.zemris.ooup.lab3.zad_2.actions.edit;

import hr.fer.zemris.ooup.lab3.zad_2.editor.TextEditorModel;
import hr.fer.zemris.ooup.lab3.zad_2.position.Location;
import hr.fer.zemris.ooup.lab3.zad_2.position.LocationRange;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class ClearTextAction implements IEditAction {

    private List<String> undoLines;
    private Location undoCursorLocation;
    private LocationRange undoSelectionRange;

    private TextEditorModel model;

    public ClearTextAction(TextEditorModel model) {
        this.model = model;
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

        lines.clear();
        lines.add("");
        model.setCursorLocation(new Location(0, 0));
        model.setSelectionRange(null);

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
    }
}
