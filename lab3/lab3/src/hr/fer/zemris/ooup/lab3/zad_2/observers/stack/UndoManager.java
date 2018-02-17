package hr.fer.zemris.ooup.lab3.zad_2.observers.stack;

import hr.fer.zemris.ooup.lab3.zad_2.actions.edit.IEditAction;

import java.util.*;

/**
 * @author Filip Gulan
 */
public class UndoManager {

    private static UndoManager instance;

    private Stack<IEditAction> undoStack;
    private Stack<IEditAction> redoStack;

    private Set<StackObserver> undoObservers;
    private Set<StackObserver> redoObservers;

    private UndoManager() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        this.undoObservers = new HashSet<>();
        this.redoObservers = new HashSet<>();
    }

    public static UndoManager getInstance() {
        if(instance == null) {
            instance = new UndoManager();
        }
        return instance;
    }

    public void clear() {
        this.undoStack.clear();
        this.redoStack.clear();

        notifyRedoObservers();
        notifyUndoObservers();
    }

    public void push(IEditAction action) {
        redoStack.clear();
        notifyRedoObservers();
        undoStack.push(action);
        notifyUndoObservers();
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            notifyUndoObservers();
            return;
        }

        IEditAction action = undoStack.pop();
        notifyUndoObservers();

        action.executeUndo();

        redoStack.push(action);
        notifyRedoObservers();
    }


    public void redo() {
        if (redoStack.isEmpty()) {
            notifyRedoObservers();
            return;
        }

        IEditAction action = redoStack.pop();
        notifyRedoObservers();

        action.executeDo();

        undoStack.push(action);
        notifyUndoObservers();
    }

    public boolean addUndoObserver(StackObserver observer) {
        return undoObservers.add(observer);
    }

    public boolean removeUndoObserver(StackObserver observer) {
        return undoObservers.remove(observer);
    }

    private void notifyUndoObservers() {
        boolean isEmpty = undoStack.isEmpty();
        undoObservers.forEach((observer) -> observer.updateStack(isEmpty));
    }

    public boolean addRedoObserver(StackObserver observer) {
        return redoObservers.add(observer);
    }

    public boolean removeRedoObserver(StackObserver observer) {
        return redoObservers.remove(observer);
    }

    private void notifyRedoObservers() {
        boolean isEmpty = redoStack.isEmpty();
        redoObservers.forEach((observer) -> observer.updateStack(isEmpty));
    }
}
