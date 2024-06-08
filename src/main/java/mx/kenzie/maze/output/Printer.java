package mx.kenzie.maze.output;

import java.io.IOException;
import java.io.OutputStream;

public interface Printer {

    void finish();

    void draw(int x, int y, int width, int height, Mode mode);

    void draw();

    void print(OutputStream stream) throws IOException;

}
