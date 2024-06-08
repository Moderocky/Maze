package mx.kenzie.maze.output;

import mx.kenzie.maze.Maze;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public abstract class AbstractPrinter implements Printer {

    protected final Maze source;
    protected final BufferedImage image;
    protected final int startX;
    protected final int startY;
    protected transient Graphics2D graphics;
    private transient Mode mode;

    public AbstractPrinter(Maze source, BufferedImage image, int startX, int startY) {
        this.source = source;
        this.image = image;
        this.startX = startX;
        this.startY = startY;
    }

    static BufferedImage createBackground(Maze maze) {
        final BufferedImage image = new BufferedImage(maze.width() + 2, maze.length() + 2, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.dispose();
        return image;
    }

    @Override
    public void finish() {
        if (graphics == null) return;
        this.graphics.dispose();
        this.graphics = null;
    }

    @Override
    public void draw(int x, int y, int width, int height, Mode mode) {
        if (graphics == null) graphics = image.createGraphics();
        if (mode != this.mode) graphics.setColor(this.getColor(this.mode = mode));
        this.graphics.fillRect(x, y, width, height);
    }

    @Override
    public abstract void draw();

    protected Color getColor(Mode mode) {
        return switch (mode) {
            case WALL, WALL_VERTICAL, WALL_HORIZONTAL, WALL_OUTSIDE -> Color.BLACK;
            case PATH_EMPTY, PATH_CORRECT, PATH_INTERSECTION -> Color.WHITE;
            case PATH_START -> Color.GREEN;
            case PATH_END -> Color.RED;
        };
    }

    @Override
    public void print(OutputStream stream) throws IOException {
        this.image.flush();
        ImageIO.write(image, "PNG", stream);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, image, startX, startY);
    }

    @Override
    public String toString() {
        return "AbstractPrinter[" +
            "source=" + source + ", " +
            "image=" + image + ", " +
            "startX=" + startX + ", " +
            "startY=" + startY + ']';
    }

}
