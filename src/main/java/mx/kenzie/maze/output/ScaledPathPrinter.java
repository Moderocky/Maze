package mx.kenzie.maze.output;

import mx.kenzie.maze.Maze;

import java.awt.image.BufferedImage;

public class ScaledPathPrinter extends ScaledPrinter {

    public ScaledPathPrinter(Maze source, int factor) {
        super(source, factor, 1);
    }

    public ScaledPathPrinter(Maze source, BufferedImage image, int factor) {
        super(source, image, factor, 1);
    }

    public ScaledPathPrinter(Maze maze, BufferedImage image, int startX, int startY, int factor) {
        super(maze, image, startX, startY, factor, 1);
    }

    public static Printer bordered(Maze maze, int factor) {
        return new ScaledPathPrinter(maze, createBackground(maze), 1, 1, factor);
    }

}
