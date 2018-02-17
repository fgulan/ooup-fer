package hr.fer.zemris.ooup.lab3.zad_2.actions.edit;

import hr.fer.zemris.ooup.lab3.zad_2.editor.TextEditorModel;
import hr.fer.zemris.ooup.lab3.zad_2.position.Location;
import hr.fer.zemris.ooup.lab3.zad_2.position.LocationRange;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class DeleteRangeAction implements IEditAction {

    private List<String> undoLines;
    private Location undoCursorLocation;
    private LocationRange undoSelectionRange;

    private TextEditorModel model;
    private LocationRange range;

    public DeleteRangeAction(TextEditorModel model, LocationRange range) {
        this.model = model;
        this.range = range;
    }

    @Override
    public void executeDo() {
        if (range == null) {
            return;
        }
        List<String> lines = model.getLines();
        Location cursorLocation = model.getCursorLocation();

        undoLines = new ArrayList<>(lines);
        undoCursorLocation = new Location(cursorLocation);
        undoSelectionRange = null;
        if (range != null) {
            undoSelectionRange =  new LocationRange(range);
        }

        int numberOfLines = range.numberOfLines();
        if (numberOfLines < 0 || (numberOfLines == 0
                && range.getStart().getColumn() > range.getEnd().getColumn())) {
            range.swap();
        }

        Location start = range.getStart();
        Location end = range.getEnd();

        if(cursorLocation.equals(end)) {
            cursorLocation.setLocation(start);
        }

        if (numberOfLines == 0) {
            StringBuilder builder = new StringBuilder(lines.get(start.getLine()));
            builder.delete(start.getColumn(), end.getColumn());
            lines.set(start.getLine(), builder.toString());
        } else {
            int removeIndex = start.getLine() + 1;
            for (int i = start.getLine() + 1, last = end.getLine(); i < last; i++) {
                lines.remove(removeIndex);
                end.update(-1, 0);
            }

            String firstline = lines.get(start.getLine());
            StringBuilder builder = new StringBuilder(firstline);
            builder.delete(start.getColumn(), firstline.length());
            firstline = builder.toString();

            String secondLine = lines.get(end.getLine());
            builder = new StringBuilder(secondLine);
            builder.delete(0, end.getColumn());

            lines.remove(end.getLine());
            lines.set(start.getLine(), firstline + builder.toString());
        }

        model.setLines(lines);
        model.setCursorLocation(cursorLocation);
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
