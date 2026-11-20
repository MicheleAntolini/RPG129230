package it.unicam.cs.mpgc.rpg129230;

import it.unicam.cs.mpgc.rpg129230.Enum.RoomType;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private static final int STEPS = 4;       // stanze per livello
    private static final int DOORS = 3;       // porte tra cui scegliere

    private final int number;                 // 1, 2 o 3
    private int currentStep = 0;              // quante stanze ha già fatto il giocatore
    private final List<Door> doors = new ArrayList<>();

    public Level(int number) {
        this.number = number;
        generateDoors();                      // appena nasce il livello, ci sono già 3 porte
    }

    private void generateDoors() {
        doors.clear();
        for (int i = 0; i < DOORS; i++) {
            doors.add(new Door());
        }
    }

    public RoomType chooseDoor(int index) {
        RoomType room = doors.get(index).open();
        currentStep++;
        generateDoors();
        return room;
    }

    public boolean isCompleted() {
        return currentStep >= STEPS;
    }

    public int getNumber() {
        return number;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public List<Door> getDoors() {
        return List.copyOf(doors);
    }
}

