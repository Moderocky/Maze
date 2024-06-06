package mx.kenzie.maze.generator;

import mx.kenzie.maze.*;
import mx.kenzie.maze.random.Seed;

import java.util.Arrays;

/**
 * A generator for filling in a source with a defined path.
 */
public interface Generator {

    /**
     * @return The source being operated on by this generator
     */
    Maze maze();

    /**
     * @return The random provider used for source generation
     */
    Seed seed();

    /**
     * Creates a basic maze, with a path from the top left to the bottom right corner.
     *
     * @return The correct path for solving the maze
     */
    default Path fill() {
        final Path correct = this.drawPath(this.maze().first(), this.maze().last());
        this.scribble();
        return correct;
    }

    /**
     * @return A random direction to move in from the given point that is within the bounds of the maze, or null if no such direction exists.
     * Moving two steps in this direction will be within the bounds of the maze, but the endpoint may be a wall.
     * */
    default Direction nextPoint(Point current) {
        final Direction[] directions = Direction.randomOrder(this.seed());
        for (Direction direction : directions) {
            final Point point = direction.apply(direction.apply(current));
            final int x = point.x();
            final int y = point.y();
            if (x < 0 || y < 0 || x >= this.maze().length() || y >= this.maze().width()) continue;
            return direction;
        }
        return null;
    }

    /**
     * Marks this point as 'empty' (a path).
     *
     * @param point The point to draw
     */
    default void drawDot(Point point) {
        this.maze().set(point, State.EMPTY);
    }

    /**
     * After all necessary paths have been drawn (e.g. the correct path)
     * 'scribbling' the maze will fill it with random (incorrect) paths.
     */
    void scribble();

    /**
     * Resets this maze to all walls.
     */
    default void reset() {
        for (byte[] bytes : this.maze().layout()) Arrays.fill(bytes, (byte) 0);
    }

    /**
     * Creates an absolute path between one point and another.
     * This does not need to be subject to regular maze generation (a straight line is valid)
     * and it can intersect other paths.
     *
     * @param point The start point
     * @param other The end point
     * @return A path joining the two
     */
    default Path join(Point point, Point other) {
        if (point.equals(other)) return new FixedPath(point);
        final int fromX = Math.min(point.x(), other.x()), toX = Math.max(point.x(), other.x());
        final int fromY = Math.min(point.y(), other.y()), toY = Math.max(point.y(), other.y());
        final DrawingPath path = new DrawingPath();
        for (int x = fromX; x < toX; x++) {
            path.add(new Point(x, fromY));
        }
        for (int y = fromY; y < toY; y++) {
            path.add(new Point(toX, y));
        }
        return path;
    }

    /**
     * Draws and cuts a path between two {@link Point}s by making two random paths and then joining them with {@link Generator#join(Point, Point)}. This modifies the maze.
     * */
    default Path drawPath(Point from, Point to) {
        final Point end = to.correct();
        this.drawDot(to);
        this.drawDot(end);
        final Path path = this.cutPath(from);
        Point last = path.last();
        if (last.equals(end)) return path.union(this.join(end, to));
        final Path reverse = this.cutPath(end);
        return path.union(reverse).union(this.join(end, to));
    }

    /**
     * Draws a path using a random walk which starts at the given {@link Point} and ends either when
     * it hits a wall or when it hits an existing part of the maze. This does not modify the maze.
     * */
    default Path drawPath(Point from) {
        final Point start = from.correct();
        this.maze().set(start, State.WALL);
        final DrawingPath path = new DrawingPath();
        Point current = start;
        while (true) {
            path.add(current);
            path.chop(current);
            if (this.maze().get(current) != State.WALL) break;
            final Direction next = this.nextPoint(current);
            if (next == null) break;
            current = next.apply(current);
            path.add(current);
            current = next.apply(current);
        }
        if (!start.equals(from)) path.addAll(this.join(from, start).collect());
        return path;
    }

    /**
     * Draws and cuts a random path into the {@link Maze}. This does not modify the maze.
     * @see #drawPath(Point)
     * */
    default Path cutPath(Point from) {
        final Path path = this.drawPath(from);
        path.cut(this.maze());
        return path;
    }

}
