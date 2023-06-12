package org.example.pixxel;

import java.util.ArrayDeque;
import java.util.Deque;

class Snake {
    private Direction direction;
    Deque<Position> snakePosition;
    Board board;

    public Snake(Position start, Board board, Direction direction) {
        this.board = board;
        this.direction = direction;
        snakePosition = new ArrayDeque<>();
        snakePosition.addFirst(start);
        board.setStatus(start, Board.Status.SNAKE);
    }

    public void onTick() throws CrashException {
        Position nextPosition = Position.getNextPosition(snakePosition.getFirst(), direction);
        if (nextPosition.equals(snakePosition.getLast())) {
            snakePosition.addFirst(snakePosition.removeLast());
        } else if (board.isHittingSomething(nextPosition)) {
            throw new CrashException("crashed hitting " + board.getStatus(nextPosition));
        }
        else if (board.getStatus(nextPosition) == Board.Status.FRUIT) {
            snakePosition.addFirst(nextPosition);
            board.setStatus(nextPosition, Board.Status.SNAKE);
        } else if (board.getStatus(nextPosition) == Board.Status.EMPTY) {
            snakePosition.addFirst(nextPosition);
            board.setStatus(nextPosition, Board.Status.SNAKE);
            Position lastPosition = snakePosition.removeLast();
            board.setStatus(lastPosition, Board.Status.EMPTY);
        }
    }

    public void changeDirection(Direction direction) {
        this.direction = direction;
    }

    public int getLength() {
        return this.snakePosition.size();
    }
}
