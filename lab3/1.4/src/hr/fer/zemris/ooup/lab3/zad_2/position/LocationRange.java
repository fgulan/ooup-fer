package hr.fer.zemris.ooup.lab3.zad_2.position;

/**
 * @author Filip Gulan
 */
public class LocationRange {

    private Location start;
    private Location end;

    public LocationRange() {
        start = new Location();
        end = new Location();
    }

    public LocationRange(Location start, Location end) {
        this.start = new Location(start);
        this.end = new Location(end);
    }

    public LocationRange(LocationRange range) {
        start = new Location(range.start);
        end = new Location(range.end);
    }

    public Location getStart() {
        return start;
    }

    public void setStart(Location start) {
        this.start = new Location(start);
    }

    public Location getEnd() {
        return end;
    }

    public void setEnd(Location end) {
        this.end = new Location(end);
    }

    public void setRange(LocationRange range) {
        start = new Location(range.start);
        end = new Location(range.end);
    }

    public void updateStart(int dx, int dy) {
        start.update(dx, dy);
    }

    public void updateEnd(int dx, int dy) {
        end.update(dx, dy);
    }

    public void swap() {
        Location temp = start;
        start = end;
        end = temp;
    }

    public int numberOfLines() {
        return end.getLine() - start.getLine();
    }

    public int yDistance() {
        return end.getColumn() - start.getColumn();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationRange that = (LocationRange) o;

        if (start != null ? !start.equals(that.start) : that.start != null) return false;
        return end != null ? end.equals(that.end) : that.end == null;
    }

    @Override
    public int hashCode() {
        int result = start != null ? start.hashCode() : 0;
        result = 31 * result + (end != null ? end.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LocationRange{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
