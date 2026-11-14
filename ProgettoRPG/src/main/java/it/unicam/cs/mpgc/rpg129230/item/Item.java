package it.unicam.cs.mpgc.rpg129230.item;

public abstract class Item {

    private final String name;
    private final int level;

    protected Item(String name, int level) {

        if (level < 1 || level > 3) {
            throw new IllegalArgumentException("Item level must be between 1 and 3.");
        }

        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }
}
