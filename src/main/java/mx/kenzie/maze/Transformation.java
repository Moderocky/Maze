package mx.kenzie.maze;

import java.util.function.UnaryOperator;

/**
 * An operation that, when applied to a point, yields another point, such as applying a vector or a rotation.
 */
@FunctionalInterface
public interface Transformation extends UnaryOperator<Point> {

}
