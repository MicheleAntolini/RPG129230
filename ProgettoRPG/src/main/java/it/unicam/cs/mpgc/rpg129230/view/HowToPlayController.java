package it.unicam.cs.mpgc.rpg129230.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Controller della schermata "How to play" (HowToPlay.fxml): mostra le regole del gioco.
 */
public class HowToPlayController {

    @FXML private TextArea rulesArea;

    /**
     * Chiamato da JavaFX appena la schermata è caricata: scrive le regole nel riquadro.
     */
    @FXML
    private void initialize() {
        rulesArea.setText("""
                GOAL
                Explore the dungeon, survive 3 levels and defeat the final boss.

                CLASSES
                Knight: lots of health and defense, low attack. Uses swords.
                Mage: high attack, low health and defense. Uses wands.

                THE DUNGEON
                Each level has 4 rooms. For every room you choose one of 3 doors.
                Each door shows the chance of finding an enemy, a chest or a fountain, but it is still a gamble!
                After the third level you face a random boss.

                ROOMS
                Enemy: a turn-based battle.
                Chest: a random item goes into your backpack.
                Fountain: choose health, attack or defense and increase it by 10%.

                BATTLE
                Attack: choose between
                  - Basic attack: always hits.
                  - Power attack: 180% damage, 70% hit chance, 3 turns cooldown.
                  - Stun attack: 50% damage, 60% hit chance, the enemy skips 2 turns,
                    4 turns cooldown.
                Defend: the next enemy attack deals 30% less damage.
                Dodge: the next enemy attack has a 30% chance to miss.
                Potion: drink one of the potions in your potion slots.

                ITEMS
                Weapons increase attack, rings increase defense, necklaces increase max health, potions restore health.
                Open the Inventory to equip items. You can carry up to 2 potions in your potion slots: the others stay in the backpack.

                AFFINITIES
                Items can be Blessed, Vital or Protection.
                A weapon with the right affinity deals 30% more damage to enemies weak to it (Vampire: Blessed, Werewolf: Protection).
                Each boss is weak to one affinity:
                  - Demon: Blessed
                  - Lich: Vital
                  - Fallen Angel: Protection
                A ring with the boss's affinity reduces its damage by 25%.

                NECKLACE EFFECTS
                When a basic attack hits, your necklace has a 50% chance to activate:
                  - Blessed: your next basic attack deals 20% more damage.
                  - Vital: you heal 5% of your max health.
                  - Protection: a shield blocks the next enemy attack.

                BOSSES
                When a boss drops below half health, it starts using its special power.
                  - Demon: raises a barrier that halves damage (Blessed weapons ignore it).
                  - Lich: curses you, dealing 10% of your max health every other turn.
                  - Fallen Angel: launches mental attacks that deal 30% of your max health.

                LEVEL UP
                Defeat enemies to gain experience. When you level up your stats grow and your health is fully restored.

                SAVING
                Press Save in the doors screen. Use Load game in the main menu to continue.
                """);
    }

    /**
     * Bottone "Back": torna al menu principale.
     */
    @FXML
    private void onBack() {
        RpgApp.showScene("MainMenu.fxml");
    }
}
