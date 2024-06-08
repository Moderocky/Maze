package mx.kenzie.maze;

import mx.kenzie.maze.generator.Generator;
import mx.kenzie.maze.generator.SimpleGenerator;
import mx.kenzie.maze.output.Printer;
import mx.kenzie.maze.output.SimplePrinter;
import mx.kenzie.maze.random.Seed;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Example4PathMaze {

    public static void main(String... args) throws IOException {
        final Maze maze = generateMaze4(127, 127, Seed.of());
        final File file = new File("maze.png");
        try (FileOutputStream stream = new FileOutputStream(file)) {
            final Printer printer = SimplePrinter.bordered(maze);
            printer.draw();
            printer.print(stream);
        }
    }

    private static Maze generateMaze4(int length, int width, Seed seed) {
        final Maze maze = new Maze(length, width);
        final Generator generator = new SimpleGenerator(maze, seed);
        final Point a, b, c, d;
        a = new Point(0, width / 2).correct();
        b = new Point(length / 2, 0).correct();
        c = new Point(length - 1, width / 2).correct();
        d = new Point(length / 2, width - 1).correct();
        final Path correct = generator.drawPath(a, b);
        final Path alternate = generator.drawPath(c, d);
        generator.scribble();
        correct.cut(maze, State.CORRECT);
        alternate.cut(maze, State.CORRECT);
        a.cut(maze, State.START);
        c.cut(maze, State.START);
        b.cut(maze, State.END);
        d.cut(maze, State.END);
        return maze;
    }

}
