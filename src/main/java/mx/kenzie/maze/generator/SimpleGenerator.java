package mx.kenzie.maze.generator;

import mx.kenzie.maze.Maze;
import mx.kenzie.maze.Point;
import mx.kenzie.maze.State;
import mx.kenzie.maze.random.Seed;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple source generator using an adapted Wilson's algorithm.
 *
 * @param maze The source to draw on
 * @param seed The random provider for the source
 */
public record SimpleGenerator(Maze maze, Seed seed) implements Generator {

    @Override
    public void scribble() {
        final int halfX = maze.length() / 2, halfY = maze.width() / 2;
        int total = (maze.length() / 2) * (maze.width() / 2);
        while (total > 2048) {
            final Point point = new Point(seed.random(0, 0, halfX) * 2, seed.random(halfY) * 2);
            --total;
            if (maze.get(point) != State.WALL) continue;
            this.cutPath(point);
        }
        final List<Point> list = new ArrayList<>();
        for (int x = 0; x < maze().length(); x++) {
            if (x % 2 != 0) continue;
            for (int y = 0; y < maze().width(); y++) {
                if (y % 2 != 0) continue;
                final Point point = new Point(x, y);
                if (maze.get(point) != State.WALL) continue;
                list.add(point);
            }
        }
        while (!list.isEmpty()) {
            final Point point = list.remove(seed.random(list.size()));
            if (maze.get(point) != State.WALL) continue;
            this.cutPath(point);
        }
    }

}
