package mx.kenzie.maze;

import mx.kenzie.maze.generator.BacktrackingGenerator;
import mx.kenzie.maze.generator.Generator;
import mx.kenzie.maze.output.Mode;
import mx.kenzie.maze.output.Printer;
import mx.kenzie.maze.output.ScaledPrinter;
import mx.kenzie.maze.random.Seed;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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

        final Printer printer = new ScaledPrinter(maze, 2, 1) {
            @Override
            protected Color getColor(Mode mode) {
                if (mode == Mode.PATH_CORRECT) return Color.CYAN;
                return super.getColor(mode);
            }
        };
        printer.draw();

        final File file = new File("maze.png");
        try (final OutputStream stream = new FileOutputStream(file)) {
            printer.print(stream);
        }
    }

}
