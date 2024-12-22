package entities;

import game.Game;

public abstract class Assassin extends Character {
    protected boolean stealth;

    public Assassin(int position, double damage, double health, double maxHealth, double defense) {
        super(position, damage, health, maxHealth, defense, 30, 10, 20, 50);
        this.stealth = false; // If Assassin is currently in stealth-mode, enables sneakAttack method.
    }

    // Assassin-specific methods
    // Sneak attack deals 50% more damage than a regular attack, and can only be
    // used if the Assassin is in stealth-mode.
    public double sneakAttack(Character target) {
        double bonusMultiplier = 1.5; // Sneak attacks deal 50% more damage
        System.out.println(
                this.getClass().getSimpleName() + " suddenly appears from the darkness and does a sneak attack on "
                        + target.getClass().getSimpleName() + "!\n");
        return calculateDamage(target, bonusMultiplier);
    }

    public boolean activateStealth() {
        Game.clearScreen();
        if (!stealth) {
            stealth = true;
            System.out.println(this.getClass().getSimpleName() + " is now in Stealth mode! \n");
            return true;
        }
        System.out.println(this.getClass().getSimpleName() + " is already in Stealth mode. \n");
        return false;
    }

    public void deactivateStealth() {
        stealth = false;
        System.out.println(this.getClass().getSimpleName() + " has lost his stealth. \n");

    }

    public boolean isStealthActive() {
        return stealth;
    }
}
