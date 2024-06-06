package mx.kenzie.maze;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * An unmodifiable path of points.
 * */
public record FixedPath(Point... points) implements Path {

    @Override
    public Path union(Path other) {
        final DrawingPath path = new DrawingPath(List.of(points));
        return path.union(other);
    }

    @Override
    public Path intersect(Path other) {
        final DrawingPath path = new DrawingPath(List.of(points));
        return path.intersect(other);
    }

    @Override
    public void cut(Maze maze) {
        for (Point point : points) point.cut(maze);
    }

    @Override
    public void cut(Maze maze, State state) {
        for (Point point : points) point.cut(maze, state);
    }

    @Override
    public Point last() {
        return points[points.length - 1];
    }

    @Override
    public Point first() {
        return points[0];
    }

    @Override
    public Collection<Point> collect() {
        return List.of(points);
    }

    @Override
    public boolean contains(Point point) {
        for (Point thing : points) if (thing.equals(point)) return true;
        return false;
    }

    @Override
    public int length() {
        return points.length;
    }

    @Override
    public Iterator<Point> iterator() {
        return new Iterator<>() {
            int index;

            @Override
            public boolean hasNext() {
                return index < points.length;
            }

            @Override
            public Point next() {
                return points[index++];
            }
        };
    }

}
