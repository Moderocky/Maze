package mx.kenzie.maze;

import mx.kenzie.maze.generator.Generator;
import mx.kenzie.maze.generator.SimpleGenerator;
import mx.kenzie.maze.output.Printer;
import mx.kenzie.maze.output.ScaledPathPrinter;
import mx.kenzie.maze.random.Seed;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ExampleWidePathMaze {

    public static void main(String... args) throws IOException {
        final int length = 51, width = 51;
        final Maze maze = new Maze(length, width);
        final Generator generator = new SimpleGenerator(maze, Seed.of());
        final Point start, end;
        start = new Point(0, 0);
        end = new Point(length - 1, width - 1);
        final Path correct = generator.drawPath(start, end);
        generator.scribble();
        correct.cut(maze, State.CORRECT);
        start.cut(maze, State.START);
        end.cut(maze, State.END);
        maze.print(System.out);
        final Printer printer = new ScaledPathPrinter(maze, 4);
        printer.draw();
        final File file = new File("maze.png");
        try (OutputStream stream = new FileOutputStream(file)) {
            printer.print(stream);
        }
    }

}
