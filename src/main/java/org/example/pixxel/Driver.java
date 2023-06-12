package org.example.pixxel;

import java.util.Collections;

public class Driver {
    public static void main(String[] args) throws InterruptedException {
        Game game = Game.create(5, Collections.emptyList());
        int timeInSecs = 0;
        while (true) {
            game.onTick();
            if (timeInSecs % 5 == 0)
                game.changeSnakeDirection(Direction.randomDirection());
            if (GameStatus.WON == game.getGameStatus()) {
                System.out.println("YOU WIN");
                return;
            }
            if (GameStatus.GAME_OVER == game.getGameStatus()) {
                System.out.println("You lost because you " + game.getGameOverReason());
                System.out.println("Your score is " + game.getScore());
                return;
            }
            game.printBoard(timeInSecs);
            timeInSecs++;
            Thread.sleep(1000);
        }
    }
}
