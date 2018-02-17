package hr.fer.zemris.ooup.lab3.zad_2.observers.clipboard;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * @author Filip Gulan
 */
public class ClipboardStack {

    private Stack<String> texts;
    private Set<ClipboardObserver> clipboardObservers;

    public ClipboardStack() {
        this.texts = new Stack<>();
        this.clipboardObservers = new HashSet<>();
    }

    public boolean addClipboardObserver(ClipboardObserver observer) {
        return clipboardObservers.add(observer);
    }

    public boolean removeClipobardObserver(ClipboardObserver observer) {
        return clipboardObservers.remove(observer);
    }

    private void notifyObservers() {
        clipboardObservers.forEach((clipboardObserver) ->
                clipboardObserver.updateClipboard());
    }

    public void clear() {
        texts.clear();
    }

    public boolean hasText() {
        return !texts.isEmpty();
    }

    public String peek() {
        return texts.peek();
    }

    public String pop() {
        String text = texts.pop();
        notifyObservers();
        return text;
    }

    public void push(String text) {
        texts.push(text);
        notifyObservers();
    }
}
