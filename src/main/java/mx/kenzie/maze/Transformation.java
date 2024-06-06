package mx.kenzie.maze;

/**
 * An operation that, when applied to a point, yields another point, such as applying a vector or a rotation.
 */
@FunctionalInterface
public interface Transformation {

    Point apply(Point point);

}
