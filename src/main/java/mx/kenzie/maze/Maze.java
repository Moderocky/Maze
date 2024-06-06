package mx.kenzie.maze;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public record Maze(byte[][] layout, int length, int width) implements Printed, Path {

    public Maze(int length, int width) {
        this(new byte[length][width], length, width);
        final byte wall = (byte) State.WALL.ordinal();
        for (byte[] row : layout) Arrays.fill(row, wall);
    }

    public Maze {
        assert length > 0 && width > 0;
        assert layout.length == length && layout[0].length == width;
    }

    public void set(Point point, State state) {
        if (point.x() < 0 || point.x() >= length || point.y() < 0 || point.y() >= width) return;
        layout[point.x()][point.y()] = (byte) state.ordinal();
    }

    public void print(PrintStream stream) {
        stream.print('+');
        for (int i = 0; i < width; i++) stream.print('-');
        stream.print('+');
        System.out.println();
        for (int x = 0; x < length; x++) {
            stream.print('|');
            for (int y = 0; y < width; y++) {
                State.valueOf(layout[x][y]).print(stream);
            }
            stream.print('|');
            stream.println();
        }
        stream.print('+');
        for (int i = 0; i < width; i++) stream.print('-');
        stream.print('+');
        System.out.println();
    }

    public int length() {
        return layout.length;
    }

    public int width() {
        return layout[0].length;
    }

    public State get(Point point) {
        if (point.x() < 0 || point.y() < 0) return null;
        if (point.x() >= length || point.y() >= width) return null;
        return State.valueOf(layout[point.x()][point.y()]);
    }

    @Override
    public Path union(Path other) {
        return new FixedPath();
    }

    @Override
    public Path intersect(Path other) {
        return new FixedPath();
    }

    @Override
    public void cut(Maze maze) {
        final int
            length = Math.min(maze.length(), this.length),
            width = Math.min(maze.width(), this.width);
        for (int x = 0; x < length; x++)
            System.arraycopy(layout[x], 0, maze.layout[x], 0, Math.max(0, width));
    }

    @Override
    public void cut(Maze maze, State state) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Point last() {
        return new Point(length - 1, width - 1);
    }

    @Override
    public Point first() {
        return new Point(0, 0);
    }

    @Override
    public Collection<Point> collect() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Point point) {
        return point.x() >= 0 && point.x() < length && point.y() >= 0 && point.y() < width;
    }

    @Override
    public Iterator<Point> iterator() {

        return new Iterator<>() {
            int x, y;

            @Override
            public boolean hasNext() {
                return x < length - 1 || y < width;
            }

            @Override
            public Point next() {
                if (y < width) return new Point(x, y++);
                return new Point(++x, y = 0);
            }
        };
    }

}
