package hr.fer.zemris.ooup.lab3.zad_2.position;

/**
 * @author Filip Gulan
 */
public class Location {

    private int line;
    private int column;

    public Location() {
        line = 0;
        column = 0;
    }

    public Location(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public Location(Location location) {
        this.line = location.line;
        this.column = location.column;
    }

    public void update(int dline, int dcolumn) {
        line += dline;
        column += dcolumn;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setLocation(Location location) {
        this.line = location.line;
        this.column = location.column;
    }

    public void setLocation(int line, int column) {
        this.line = line;
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (line != location.line) return false;
        return column == location.column;

    }

    @Override
    public int hashCode() {
        int result = line;
        result = 31 * result + column;
        return result;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", line, column);
    }
}
