package mx.kenzie.maze;

import mx.kenzie.maze.generator.*;
import mx.kenzie.maze.random.Seed;

import java.io.*;

public class ExampleBacktrackingMaze {
    public static void main(final String... args) throws IOException {
        final int length = 1025, width = 1025;
        final Maze maze = new Maze(length, width);
        final Generator generator = new BacktrackingGenerator(maze, Seed.of("test :)".hashCode()));
        final Point start, end;
        start = new Point(0, 0);
        end = new Point(length - 1, width - 1);
        final Path correct = generator.fill();
        correct.cut(maze, State.CORRECT);
        start.cut(maze, State.START);
        end.cut(maze, State.END);

        final Printer printer = new Printer(maze);
        printer.draw();

        final File file = new File("maze.png");
        try (final OutputStream stream = new FileOutputStream(file)) {
            printer.print(stream);
        }
    }
}
