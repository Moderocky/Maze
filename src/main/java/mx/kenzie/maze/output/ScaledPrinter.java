package mx.kenzie.maze.output;

import mx.kenzie.maze.Maze;
import mx.kenzie.maze.Point;

import java.awt.*;
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
        final Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.GREEN);
        graphics.fillRect(startY, startX,
            scaleSide(source.width(), pathFactor, wallFactor) + startY,
            scaleSide(source.length(), pathFactor, wallFactor) + startX);
        for (Point point : source) {
            final int x = scalePoint(point.y(), pathFactor, wallFactor);
            final int y = scalePoint(point.x(), pathFactor, wallFactor);
            switch (source.get(point)) {
                case WALL:
                    if (point.x() % 2 == 1 && point.y() % 2 == 1) {
                        graphics.setColor(this.getColor(Mode.WALL));
                        graphics.fillRect(x, y, wallFactor, wallFactor);
                    } else switch (point.y() % 2) {
                        case 0 -> {
                            graphics.setColor(this.getColor(Mode.WALL_HORIZONTAL));
                            graphics.fillRect(x, y, pathFactor, wallFactor);
                        }
                        case 1 -> {
                            graphics.setColor(this.getColor(Mode.WALL_VERTICAL));
                            graphics.fillRect(x, y, wallFactor, pathFactor);
                        }
                    }
                    continue;
                case START:
                    graphics.setColor(this.getColor(Mode.PATH_START));
                    break;
                case END:
                    graphics.setColor(this.getColor(Mode.PATH_END));
                    break;
                case CORRECT:
                    graphics.setColor(this.getColor(Mode.PATH_CORRECT));
                    break;
                case INTERSECTION:
                    graphics.setColor(this.getColor(Mode.PATH_INTERSECTION));
                    break;
                case EMPTY:
                    graphics.setColor(this.getColor(Mode.PATH_EMPTY));
                    break;
                case null, default:
                    continue;
            }
            if (point.x() % 2 == 0 && point.y() % 2 == 0) graphics.fillRect(x, y, pathFactor, pathFactor);
            else switch (point.x() % 2) {
                case 1 -> graphics.fillRect(x, y, pathFactor, wallFactor);
                case 0 -> graphics.fillRect(x, y, wallFactor, pathFactor);
            }
        }
        graphics.dispose();
    }

}
