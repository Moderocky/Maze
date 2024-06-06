package mx.kenzie.maze;

import mx.kenzie.maze.random.Seed;

public enum Direction implements Transformation {
    NORTH(0, -1), EAST(1, 0), SOUTH(0, 1), WEST(-1, 0);
    private static final Direction[] values = values();
    public final int x, y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Direction random(Seed seed) {
        return values[seed.random(0, 0, values.length)];
    }

    public static Direction[] randomOrder(Seed seed) {
        final Direction[] directions = Direction.values();
        for (int i = directions.length - 1; i > 0; --i) {
            int index = seed.random(i + 1);
            // Simple swap
            final Direction take = directions[index];
            directions[index] = directions[i];
            directions[i] = take;
        }
        return directions;
    }

    public static Direction valueOf(int x, int y) {
        for (Direction dir : Direction.values()) {
            if (dir.x == x && dir.y == y) {
                return dir;
            }
        }
        return null;
    }

    public Direction opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case SOUTH -> NORTH;
            case WEST -> EAST;
        };
    }

    @Override
    public Point apply(Point point) {
        return new Point(point.x() + x, point.y() + y);
    }

}
