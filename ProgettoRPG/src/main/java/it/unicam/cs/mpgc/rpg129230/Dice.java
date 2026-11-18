package it.unicam.cs.mpgc.rpg129230;


public class Dice {

    public static int roll(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }
}
