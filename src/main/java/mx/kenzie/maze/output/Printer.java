package mx.kenzie.maze.output;

import java.io.IOException;
import java.io.OutputStream;

public interface Printer {

    void draw();

    void print(OutputStream stream) throws IOException;

}
