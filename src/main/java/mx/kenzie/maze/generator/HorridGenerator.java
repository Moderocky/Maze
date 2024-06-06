package mx.kenzie.maze.generator;

import mx.kenzie.maze.*;
import mx.kenzie.maze.random.Seed;

import java.util.ArrayList;
import java.util.List;

/**
 * A maze generator that prioritises longer and more annoying incorrect paths.
 * This can be (exponentially) slower than regular generation, since it will re-attempt paths multiple times
 * to find longer options.
 *
 * @param maze The source to draw on
 * @param seed The random provider for the source
 */
public record HorridGenerator(Maze maze, Seed seed, int difficulty) implements Generator {

    private Path drawMeanPath(Point point, int difficulty) {
        Path path = new FixedPath();
        for (int i = 0; i < difficulty; i++) {
            Path test = this.drawPath(point);
            if (test.length() > path.length()) path = test;
        }
        path.cut(this.maze());
        return path;
    }

    @Override
    public void scribble() {
        final int halfX = maze.length() / 2, halfY = maze.width() / 2;
        int total = (maze.length() / 2) * (maze.width() / 2);
        while (total > 2048) { // don't make a list of six billion points please
            final Point point = new Point(seed.random(0, 0, halfX) * 2, seed.random(halfY) * 2);
            --total;
            if (maze.get(point) != State.WALL) continue;
            this.drawMeanPath(point, difficulty);
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
        total = list.size();
        int size = total;
        while (!list.isEmpty()) {
            final Point point = list.remove(seed.random(list.size()));
            if (maze.get(point) != State.WALL) continue;
            this.drawMeanPath(point, Math.max(1, difficulty * (size / total)));
            --size;
        }
    }

    @Override
    public Path drawPath(Point from, Point to) {
        final Point end = to.correct();
        this.drawDot(to);
        this.drawDot(end);
        final Path path = this.drawMeanPath(from, difficulty);
        Point last = path.last();
        if (last.equals(end)) return path.union(this.join(end, to));
        final Path reverse = this.drawMeanPath(end, difficulty);
        return path.union(reverse).union(this.join(end, to));
    }

}
