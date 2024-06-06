package mx.kenzie.maze;

import mx.kenzie.maze.generator.Generator;
import mx.kenzie.maze.generator.SimpleGenerator;
import mx.kenzie.maze.random.Seed;

public class ExampleSimpleMaze {

    public static void main(String... args) {
        final int length = 19, width = 45;
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
    }

}
