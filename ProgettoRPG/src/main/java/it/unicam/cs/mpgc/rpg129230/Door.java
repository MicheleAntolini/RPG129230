package it.unicam.cs.mpgc.rpg129230;

import it.unicam.cs.mpgc.rpg129230.Enum.RoomType;

public class Door {

    private int treasure;
    private int fountain;
    private int enemy;

    public Door(int treasure, int fountain, int enemy) {

        if (treasure + fountain + enemy != 100) throw new IllegalArgumentException("Percentuali sbagliate");
        this.treasure = treasure;
        this.fountain = fountain;
        this.enemy = enemy;
    }

    public Door(){

        treasure = Dice.roll(25, 35);
        enemy = Dice.roll(35, 55);
        fountain = 100 - treasure - enemy;
    }

    public RoomType open(){

        int roll = Dice.roll(1, 100);

        if (roll <= enemy){
            return RoomType.ENEMY;
        } else if (roll <= enemy + treasure){
            return RoomType.TREASURE;
        } else {
            return RoomType.FOUNTAIN;
        }
    }
}
