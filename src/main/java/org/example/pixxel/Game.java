package org.example.pixxel;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {
    private final Direction DEFAULT_SNAKE_DIRECTION = Direction.RIGHT;
    private final int DEFAULT_BOARD_SIZE = 5;
    private final Position DEFAULT_SNAKE_POSITION = Position.getInstance(0, 0);
    private final Position DEFAULT_FRUIT_POSITION = Position.getInstance(0, 0);
    private final Board board;
    private final Snake snake;
    private Position fruit;

    private GameStatus gameStatus;
    private String gameOverReason;

    private Game(int boardSize, List<Position> obstacles) {
        this.board = new Board(boardSize, obstacles);
        this.snake = new Snake(DEFAULT_SNAKE_POSITION, board, DEFAULT_SNAKE_DIRECTION);
        this.fruit = DEFAULT_FRUIT_POSITION;
        gameStatus = GameStatus.RUNNING;
    }

    public static Game create(int boardSize, List<Position> obstacles) {
        // Sanitize boardSize and obstacles
        return new Game(boardSize, obstacles);
    }

    public String getGameOverReason() {
        return gameOverReason;
    }

    public int getScore() {
        return snake.getLength() - 1;
    }

    public void printBoard(int timeInSecs) {
        System.out.println("After " + timeInSecs + " secs");
        board.print();
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void changeSnakeDirection(Direction direction) {
        snake.changeDirection(direction);
    }

    public void onTick() {
        addFruitIfEaten();
        try {
            snake.onTick();
            if (snake.getLength() == board.getSize() * board.getSize())
                gameStatus = GameStatus.WON;
        } catch (CrashException e) {
            gameOverReason = e.getMessage();
            gameStatus = GameStatus.GAME_OVER;
        }


        // snake update
        // is gameover
        // add fruit if not present
        // print board
    }

    private void addFruitIfEaten() {
        if (board.getStatus(fruit) == Board.Status.SNAKE) {
            fruit = nextFruitPosition();
            board.setStatus(fruit, Board.Status.FRUIT);
        }
    }

    private Position nextFruitPosition() {
        Random random = new Random();
        int randomIndex = random.nextInt(board.getFreePositions().size());
        return board.getFreePositions().toArray(new Position[0])[randomIndex];
    }
}
