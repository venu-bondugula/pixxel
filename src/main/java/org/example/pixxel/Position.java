package org.example.pixxel;

import java.util.HashMap;
import java.util.Map;

class Position {
    private static final Map<Integer, Map<Integer, Position>> cache = new HashMap<>();
    private final int ROW;
    private final int COL;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (ROW != position.ROW) return false;
        return COL == position.COL;
    }

    @Override
    public int hashCode() {
        int result = ROW;
        result = 31 * result + COL;
        return result;
    }

    public static Position getInstance(int row, int col) {
        return cache.computeIfAbsent(row, k -> new HashMap<>())
                .computeIfAbsent(col, k -> new Position(row, col));
    }

    private Position(int row, int col) {
        ROW = row;
        COL = col;
    }

    public static Position getNextPosition(Position currentPosition, Direction direction) {
        int row = currentPosition.getROW();
        int col = currentPosition.getCOL();
        switch (direction) {
            case LEFT:
                return new Position(row, col - 1);
            case RIGHT:
                return new Position(row, col + 1);
            case UP:
                return new Position(row - 1, col);
            case DOWN:
                return new Position(row + 1, col);
        }
        throw new RuntimeException("Invalid direction");
    }

    public int getROW() {
        return ROW;
    }

    public int getCOL() {
        return COL;
    }
}
