package mx.kenzie.maze.output;

/**
 * The type of tile the printer is drawing currently.
 */
public enum Mode {
    WALL(false),
    WALL_HORIZONTAL(false),
    WALL_VERTICAL(false),
    /**
     * This should never be reached in a regular printer: it is called when a point outside the maze is printed.
     */
    WALL_OUTSIDE(false),
    PATH_EMPTY(true),
    PATH_CORRECT(true),
    PATH_INTERSECTION(true),
    PATH_START(true),
    PATH_END(true);
    public final boolean path;

    Mode(boolean path) {
        this.path = path;
    }

}
