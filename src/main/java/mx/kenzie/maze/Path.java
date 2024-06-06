package mx.kenzie.maze;

import java.util.Collection;

public interface Path extends Iterable<Point> {

    Path union(Path other);

    Path intersect(Path other);

    void cut(Maze maze);

    void cut(Maze maze, State state);

    Point last();

    Point first();

    Collection<Point> collect();

    boolean contains(Point point);

    int length();

}
