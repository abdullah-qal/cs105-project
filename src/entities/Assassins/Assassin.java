package entities.Assassins;

import entities.Character;
import game.Game;

public abstract class Assassin extends Character {
    protected boolean stealth;

    public Assassin(int position, int damage, int health, int maxHealth, double defense, int movementSpeed,
            int range, double critRate, double critDmg) {
        super(position, damage, health, maxHealth, defense, movementSpeed, range, critRate, critDmg);
        stealth = false;
    }

    // Assassin-specific methods
    // Sneak attack deals 50% more damage than a regular attack, and can only be
    // used if the Assassin is in stealth-mode.
    public int sneakAttack(Character target) {
        double bonusMultiplier = 1.5; // Sneak attacks deal 50% more damage
        System.out.printf(
            this.getClass().getSimpleName(),
            target.getClass().getSimpleName());

        return calculateDamage(target, bonusMultiplier);
    }

    public boolean activateStealth() {
        Game.clearScreen();
        if (!stealth) {
            stealth = true;
            System.out.printf(
                "%s is now in Stealth mode!%n%n",
                this.getClass().getSimpleName());
            return true;
        }
        System.out.printf(
            "%s is already in Stealth mode.%n%n",
            this.getClass().getSimpleName());
        return false;
    }

    public void deactivateStealth() {
        stealth = false;
        System.out.printf(
            "%s has lost his stealth.%n%n",
            this.getClass().getSimpleName());

    }

    public boolean isStealthActive() {
        return stealth;
    }
}
