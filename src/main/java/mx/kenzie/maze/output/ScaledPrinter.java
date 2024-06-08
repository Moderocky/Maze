package mx.kenzie.maze.output;

import mx.kenzie.maze.Maze;
import mx.kenzie.maze.Point;
import mx.kenzie.maze.State;

import java.awt.image.BufferedImage;

public class ScaledPrinter extends AbstractPrinter {

    protected final int pathFactor, wallFactor;

    static int scaleSide(int value, int pathFactor, int wallFactor) {
        assert value % 2 == 1;
        return ((value / 2 + 1) * pathFactor) + ((value / 2) * wallFactor);
    }

    static int scalePoint(int value, int pathFactor, int wallFactor) {
        if (value == 0) return value;
        if (value % 2 == 1) return scaleSide(value, pathFactor, wallFactor);
        return scaleSide(value - 1, pathFactor, wallFactor) + wallFactor;
    }

    public ScaledPrinter(Maze source, int pathFactor, int wallFactor) {
        this(source, new BufferedImage(scaleSide(source.width(), pathFactor, wallFactor),
            scaleSide(source.length(), pathFactor, wallFactor),
            BufferedImage.TYPE_INT_ARGB), pathFactor, wallFactor);
    }

    public ScaledPrinter(Maze source, BufferedImage image, int pathFactor, int wallFactor) {
        super(source, image, 0, 0);
        this.pathFactor = pathFactor;
        this.wallFactor = wallFactor;
    }

    public ScaledPrinter(Maze maze, BufferedImage image, int startX, int startY, int pathFactor, int wallFactor) {
        super(maze, image, startX, startY);
        this.pathFactor = pathFactor;
        this.wallFactor = wallFactor;
    }

    public static Printer bordered(Maze maze, int pathFactor, int wallFactor) {
        return new ScaledPrinter(maze, createBackground(maze), 1, 1, pathFactor, wallFactor);
    }

    @Override
    public void draw() {
        for (Point point : source) {
            final int x = scalePoint(point.y(), pathFactor, wallFactor);
            final int y = scalePoint(point.x(), pathFactor, wallFactor);
            final State state = source.get(point);
            if (state == State.WALL) {
                if (point.x() % 2 == 1 && point.y() % 2 == 1) this.draw(x, y, wallFactor, wallFactor, Mode.WALL);
                else switch (point.y() % 2) {
                    case 0 -> this.draw(x, y, pathFactor, wallFactor, Mode.WALL_HORIZONTAL);
                    case 1 -> this.draw(x, y, wallFactor, pathFactor, Mode.WALL_VERTICAL);
                }
                continue;
            }
            final Mode mode = switch (state) {
                case START -> Mode.PATH_START;
                case END -> Mode.PATH_END;
                case CORRECT -> Mode.PATH_CORRECT;
                case INTERSECTION -> Mode.PATH_INTERSECTION;
                case null, default -> Mode.PATH_EMPTY;
            };
            if (point.x() % 2 == 0 && point.y() % 2 == 0) this.draw(x, y, pathFactor, pathFactor, mode);
            else switch (point.x() % 2) {
                case 1 -> this.draw(x, y, pathFactor, wallFactor, mode);
                case 0 -> this.draw(x, y, wallFactor, pathFactor, mode);
            }
        }
        this.finish();
    }

}
