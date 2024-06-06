package mx.kenzie.maze.random;

import java.util.Random;

/**
 * A number provider based on the current X and Y coordinates.
 */
@FunctionalInterface
public interface Seed {

    static Seed of(Random random) {
        return (x, y, range) -> random.nextInt(range);
    }

    static Seed of(long seed) {
        return of(new Random(seed));
    }

    static Seed of() {
        return of(new Random());
    }

    int random(int x, int y, int range);

    default int random(int range) {
        return this.random(0, 0, range);
    }

}
