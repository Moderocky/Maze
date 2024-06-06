package mx.kenzie.maze;

import java.util.Collection;
import java.util.LinkedList;

public class DrawingPath extends LinkedList<Point> implements Path {

    public DrawingPath() {
    }

    protected DrawingPath(Collection<? extends Point> c) {
        super(c);
    }

    public void chop(Point point) {
        int index = this.indexOf(point);
        if (index == -1 || index == this.size() - 1) return;
        this.removeRange(index + 1, this.size());
    }

    @Override
    public Path union(Path other) {
        final DrawingPath path = new DrawingPath(this);
        path.addAll(other.collect());
        return path;
    }

    @Override
    public Path intersect(Path other) {
        final DrawingPath path = new DrawingPath(this);
        path.removeIf(point -> !other.contains(point));
        return path;
    }

    public void cut(Maze maze) {
        for (Point point : this) point.cut(maze);
    }

    public void cut(Maze maze, State state) {
        for (Point point : this) point.cut(maze, state);
    }

    @Override
    public Point last() {
        return this.getLast();
    }

    @Override
    public Point first() {
        return this.getFirst();
    }

    @Override
    public Collection<Point> collect() {
        return this;
    }

    @Override
    public boolean contains(Point point) {
        return this.contains((Object) point);
    }

    @Override
    public int length() {
        return this.size();
    }

}
