package mx.kenzie.maze;

import java.io.PrintStream;

/**
 * Something that can be printed out.
 */
public interface Printed extends Iterable<Point> {

    void print(PrintStream stream);

    int length();

    int width();

    State get(Point point);

}
