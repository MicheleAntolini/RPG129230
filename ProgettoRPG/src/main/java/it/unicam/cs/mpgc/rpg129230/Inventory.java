package it.unicam.cs.mpgc.rpg129230;

import it.unicam.cs.mpgc.rpg129230.item.*;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private Ring ring;
    private Necklace necklace;
    private Weapon weapon;
    private static final int MAX_POTION_SLOT = 2;

    private final List<Item> backpack = new ArrayList<>();
    private final List<Potion> potionSlot = new ArrayList<>();

    public void equipRing(Ring newRing) {

        if (this.ring != null){
            backpack.add(this.ring);
        }

        backpack.remove(newRing);

        this.ring = newRing;
    }

    public void equipNecklace(Necklace newNecklace){

        if (this.necklace != null){
            backpack.add(this.necklace);
        }

        backpack.remove(newNecklace);

        this.necklace = newNecklace;
    }

    public void equipWeapon(Weapon newWeapon){

        if (this.weapon != null){
            backpack.add(this.weapon);
        }

        backpack.remove(newWeapon);

        this.weapon = newWeapon;
    }

    public boolean equipPotion(Potion newPotion){

        if(potionSlot.size() == MAX_POTION_SLOT){
            return false;
        } else {
            backpack.remove(newPotion);
            potionSlot.add(newPotion);
            return true;
        }
    }

    public void addItem(Item newItem){
        backpack.add(newItem);
    }

    public Ring getRing() {
        return ring;
    }

    public Necklace getNecklace() {
        return necklace;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public List<Item> getBackpack() {
        return List.copyOf(backpack);
    }

    public List<Potion> getPotionSlot() {
        return List.copyOf(potionSlot);
    }
}
