package net.florial.utils;

import java.util.Random;

public class GetChance {

    public static boolean getChance(int minimalChance) {
        Random random = new Random();
        return random.nextInt(99) + 1 >= minimalChance;
    }

}
