package mx.kenzie.maze.generator;

import mx.kenzie.maze.Point;
import mx.kenzie.maze.*;
import mx.kenzie.maze.random.Seed;

import java.util.List;
import java.util.*;

/**
 * An incredibly simple maze generator that uses a backtracking algorithm to
 * fill the maze with a single path. This has the interesting property that there
 * will always be exactly one path between any two points in the maze.
 * <p>
 * This generator generally performs well even for large mazes, as long as it does not run
 * out of memory.
 *
 * @param maze The source to draw on
 * @param seed The random provider for the source
 */
public record BacktrackingGenerator(Maze maze, Seed seed) implements Generator {

    @Override
    public Path fill() {
        final Set<Point> visited = new HashSet<>();
        final Map<Point, Point> discoveryMap = new HashMap<>();
        final ArrayDeque<Point> stack = new ArrayDeque<>();

        final Point startingCell = new Point(0, 0);
        discoveryMap.put(startingCell, null);
        stack.push(startingCell);

        while (!stack.isEmpty()) {
            final Point currentCell = stack.peek();
            visited.add(currentCell);

            final List<Point> neighbours = new ArrayList<>();
            for (final Direction direction : Direction.randomOrder(seed)) {
                final Point neighbour = direction.apply(direction.apply(currentCell));

                final int x = neighbour.x();
                final int y = neighbour.y();
                if (x < 0 || y < 0 || x >= this.maze().length() || y >= this.maze().width()) continue;

                if (visited.contains(neighbour)) continue;
                neighbours.add(neighbour);
            }

            if (neighbours.isEmpty()) {
                stack.pop();
            } else {
                final Point nextCell = neighbours.get(seed.random(neighbours.size()));
                assert nextCell.x() == currentCell.x() || nextCell.y() == currentCell.y();
                join(currentCell, nextCell).cut(maze, State.EMPTY);
                nextCell.cut(maze, State.EMPTY);
                discoveryMap.put(nextCell, currentCell);
                stack.push(nextCell);
            }
        }

        return this.getPathTo(new Point(maze.length() - 1, maze.width() - 1), discoveryMap);
    }

    private DrawingPath getPathTo(final Point target, final Map<Point, Point> discoveryMap) {
        final DrawingPath path = new DrawingPath();
        path.add(target);

        Point current = discoveryMap.get(target);
        if (current == null) return null;

        while (current != null) {
            final int x = current.x();
            final int y = current.y();

            final int lastX = path.last().x();
            final int lastY = path.last().y();
            final int dx = x - lastX;
            final int dy = y - lastY;
            final int sx = Integer.signum(dx);
            final int sy = Integer.signum(dy);
            for (int i = lastX + sx;; i += sx) {
                path.add(new Point(i, y));
                if (i == x) break;
            }
            for (int i = lastY + sy;; i += sy) {
                path.add(new Point(x, i));
                if (i == y) break;
            }

            current = discoveryMap.get(current);
        }

        return path;
    }

    @Override
    public void scribble() {
        this.fill();
    }
}
