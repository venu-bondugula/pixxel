package org.example.pixxel;

import java.util.*;

public class Board {
    private final int SIZE;
    private final List<List<Status>> cells;
    private final LinkedHashSet<Position> freePositions;

    public Board(int size, List<Position> obstacles) {
        SIZE = size;
        cells = new ArrayList<>(SIZE);
        freePositions = new LinkedHashSet<>(SIZE * SIZE);
        initBoard();
        addObstacles(obstacles);
    }

    private void addObstacles(List<Position> obstructions) {
        for (Position obstruction : obstructions) {
            cells.get(obstruction.getROW()).set(obstruction.getCOL(), Status.OBSTACLE);
            freePositions.remove(Position.getInstance(obstruction.getROW(), obstruction.getCOL()));
        }
    }

    private void initBoard() {
        for (int i = 0; i < SIZE; i++) {
            ArrayList<Status> row = new ArrayList<>(SIZE);
            for (int j = 0; j < SIZE; j++) {
                row.add(Status.EMPTY);
                freePositions.add(Position.getInstance(i, j));
            }
            cells.add(row);
        }
    }

    public boolean isHittingSomething(Position nextPosition) {
        return isHittingWall(nextPosition)
                || isHittingObstacle(nextPosition)
                || isHittingSnake(nextPosition);
    }

    private boolean isHittingSnake(Position nextPosition) {
        return getStatus(nextPosition) == Status.SNAKE;
    }

    private boolean isHittingObstacle(Position nextPosition) {
        return getStatus(nextPosition) == Status.OBSTACLE;
    }

    public Status getStatus(Position nextPosition) {
        if (isHittingWall(nextPosition))
            return Status.WALL;
        return cells.get(nextPosition.getROW()).get(nextPosition.getCOL());
    }

    private boolean isHittingWall(Position nextPosition) {
        int row = nextPosition.getROW();
        int col = nextPosition.getCOL();

        return isWall(row) || isWall(col);
    }

    private boolean isWall(int position) {
        return !(0 <= position && position < SIZE);
    }

    public void setStatus(Position position, Status status) {
        cells.get(position.getROW()).set(position.getCOL(), status);
        if (status == Status.EMPTY)
            freePositions.add(position);
        else
            freePositions.remove(position);
    }

    public void print() {
        for (List<Status> row : cells) {
            for (Status status : row) {
                switch (status) {
                    case EMPTY:
                        System.out.print(". ");
                        break;
                    case SNAKE:
                        System.out.print("S ");
                        break;
                    case FRUIT:
                        System.out.print("* ");
                        break;
                    case OBSTACLE:
                        System.out.print("| ");
                        break;
                }
            }
            System.out.println();
        }
    }

    public int getSize() {
        return SIZE;
    }

    public LinkedHashSet<Position> getFreePositions() {
        return freePositions;
    }

    public enum Status {
        EMPTY,
        OBSTACLE,
        SNAKE,
        FRUIT,
        WALL
    }

}
