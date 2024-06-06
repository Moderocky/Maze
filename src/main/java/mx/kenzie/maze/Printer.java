package mx.kenzie.maze;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

public record Printer(Printed source, BufferedImage image, int startX, int startY) {

    public Printer(Printed source) {
        this(source, new BufferedImage(source.width(), source.length(), BufferedImage.TYPE_INT_ARGB));
    }

    public Printer(Printed source, BufferedImage image) {
        this(source, image, 0, 0);
    }

    public Printer {
        assert startX >= 0 && startX + source.length() <= image.getHeight();
        assert startY >= 0 && startY + source.width() <= image.getWidth();
    }

    public static Printer bordered(Maze maze) {
        final BufferedImage image = new BufferedImage(maze.width() + 2, maze.length() + 2, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.dispose();
        return new Printer(maze, image, 1, 1);
    }

    public void draw() {
        final Graphics2D graphics = image.createGraphics();
        Color color = Color.WHITE;
        graphics.setColor(color);
        for (Point point : source) {
            final int y = point.x() + startX, x = point.y() + startY;
            final Color ours = switch (source.get(point)) {
                case EMPTY -> Color.WHITE;
                case CORRECT -> Color.CYAN;
                case START -> Color.GREEN;
                case END -> Color.RED;
                case INTERSECTION -> Color.YELLOW;
                case null, default -> Color.BLACK;
            };
            if (color != ours) graphics.setColor(color = ours);
            graphics.drawLine(x, y, x, y);
        }
        graphics.dispose();
    }

    public void print(OutputStream stream) throws IOException {
        this.image.flush();
        ImageIO.write(image, "PNG", stream);
    }

}
