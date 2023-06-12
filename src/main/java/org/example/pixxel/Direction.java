package org.example.pixxel;

import java.util.Random;

enum Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN;

    private static final Random RANDOM = new Random();

    public static Direction randomDirection() {
        Direction[] directions = values();
        return directions[RANDOM.nextInt(directions.length)];
    }
}
