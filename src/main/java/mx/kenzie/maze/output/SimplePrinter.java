package mx.kenzie.maze.output;

import mx.kenzie.maze.Maze;
import mx.kenzie.maze.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SimplePrinter extends AbstractPrinter {

    public SimplePrinter(Maze source) {
        this(source, new BufferedImage(source.width(), source.length(), BufferedImage.TYPE_INT_ARGB));
    }

    public SimplePrinter(Maze source, BufferedImage image) {
        super(source, image, 0, 0);
    }

    public SimplePrinter(Maze maze, BufferedImage image, int startX, int startY) {
        super(maze, image, startX, startY);
    }

    public static Printer bordered(Maze maze) {
        return new SimplePrinter(maze, createBackground(maze), 1, 1);
    }

    @Override
    public void draw() {
        final Graphics2D graphics = image.createGraphics();
        Color color = Color.WHITE;
        graphics.setColor(color);
        for (Point point : source) {
            final int y = point.x() + startX, x = point.y() + startY;
            final Mode mode = switch (source.get(point)) {
                case WALL -> Mode.WALL;
                case EMPTY -> Mode.PATH_EMPTY;
                case INTERSECTION -> Mode.PATH_INTERSECTION;
                case CORRECT -> Mode.PATH_CORRECT;
                case START -> Mode.PATH_START;
                case END -> Mode.PATH_END;
                case null -> Mode.WALL_OUTSIDE;
            };
            final Color ours = this.getColor(mode);
            if (color != ours) graphics.setColor(color = ours);
            graphics.drawLine(x, y, x, y);
        }
        graphics.dispose();
    }

}
