package mx.kenzie.maze;

import java.util.Collection;

/**
 * A path through a maze, i.e., a sequence of points.
 * */
public interface Path extends Iterable<Point> {

    /**
     * @return A path that is this path followed by the other path.
     * */
    Path union(Path other);

    /**
     * @return A path that is all points of this path that are included in the other path.
     * */
    Path intersect(Path other);

    /**
     * Cuts this path into the given {@link Maze}, removing walls.
     * */
    void cut(Maze maze);

    /**
     * Cuts this path into the given {@link Maze}, setting all points to the given {@link State}.
     * */
    void cut(Maze maze, State state);

    /**
     * @return The last point in this path.
     * */
    Point last();

    /**
     * @return The first point in this path.
     * */
    Point first();

    /**
     * @return All points in this path.
     * */
    Collection<Point> collect();

    /**
     * @return Whether this path contains the given point.
     * */
    boolean contains(Point point);

    /**
     * @return The number of points in this path.
     * */
    int length();

}
