package hr.fer.zemris.ooup.lab3.zad_2.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Filip Gulan
 */
public class LinesIterator implements Iterator<String> {

    private List<String> lines;
    private int current;
    private int end;

    public LinesIterator(List<String> lines) {
        if (lines == null ) {
            throw new NullPointerException("Input list is null");
        }
        this.lines = lines;
        this.current = 0;
        this.end = lines.size();
    }

    public LinesIterator(List<String> lines, int start, int end) {
        if (start > end) {
            throw new IndexOutOfBoundsException("Start index is bigger than end index.");
        } else if (end > lines.size()) {
            throw new IndexOutOfBoundsException("End index is bigger than list size.");
        }
        this.lines = lines;
        this.current = start;
        this.end = end;
    }

    @Override
    public boolean hasNext() {
        if (current < end) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String next() {
        if (hasNext()) {
            return lines.get(current++);
        } else {
            throw new NoSuchElementException("Collection has no more lines to iterate trough.");
        }
    }
}
