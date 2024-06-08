package mx.kenzie.maze;

import mx.kenzie.maze.output.Printed;

import java.io.PrintStream;

public enum State implements Printed {
    WALL,
    EMPTY,
    INTERSECTION,
    CORRECT,
    START,
    END;
    private static final State[] VALUES = State.values();

    public static State valueOf(int state) {
        return VALUES[state];
    }

    @Override
    public void print(PrintStream stream) {
        switch (this) {
            case EMPTY -> stream.print(' ');
            case WALL -> stream.print('█');
            case CORRECT -> stream.print('.');
            case START -> stream.print('A');
            case END -> stream.print('B');
            default -> stream.print('-');
        }
    }
}
