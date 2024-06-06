package mx.kenzie.maze;

import mx.kenzie.maze.generator.Generator;
import mx.kenzie.maze.generator.HorridGenerator;
import mx.kenzie.maze.generator.SimpleGenerator;
import mx.kenzie.maze.random.Seed;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ExampleHorridMaze {

    public static void main(String... args) throws IOException {
        // this will be quite slow because we're drawing and comparing 2080800 paths
        final int length = 255, width = 255;
        final Maze maze = new Maze(length, width);
        final Generator generator = new HorridGenerator(maze, Seed.of(), 128);
        final Point start, end;
        start = new Point(0, 0);
        end = new Point(length - 1, width - 1);
        final Path correct = generator.drawPath(start, end);
        generator.scribble();
        correct.cut(maze, State.CORRECT);
        start.cut(maze, State.START);
        end.cut(maze, State.END);
        final Printer printer = new Printer(maze);
        printer.draw();
        final File file = new File("maze.png");
        try (OutputStream stream = new FileOutputStream(file)) {
            printer.print(stream);
        }
    }

}
