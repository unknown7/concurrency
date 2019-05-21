package sharingConstrainedResources.criticalArea;

public class Pair {
    private int x, y;
    public Pair() {
        this(0, 0);
    }
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void incrementX() {
        x++;
    }
    public void incrementY() {
        y++;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
