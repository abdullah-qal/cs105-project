package entities;

import java.util.Random;

import game.Game;

public abstract class Assassin extends Character {
    protected boolean stealth;
    
    public Assassin(int position, double damage, double health, double maxHealth, double defense) {
        super(position, damage, health, maxHealth,defense, 30, 10, 20, 50);
        this.stealth = false; // If Assassin is currently in stealth-mode, enables sneakAttack method.
    }

    // Basic methods from Character
    protected double calculateDamage(Character target, double bonusMultiplier) {
        Random random = new Random();
        double randomValue = random.nextDouble() * 100; // Random number between 0 and 100
        boolean isCritRate = randomValue < critRate; // Checks if crit rate happens

        double baseDamage = isCritRate ? damage * (1 + critDmg * 0.01) : damage;
        if (isCritRate) {
            Game.clearScreen();
            System.out.println(this.getClass().getSimpleName() + " landed a crit! It resulted in " + baseDamage + " dmg.");
        } else {
            Game.clearScreen();
            System.out.println(this.getClass().getSimpleName()  + " landed a normal hit yielding " + baseDamage + " dmg.");
        }

        return baseDamage * bonusMultiplier;
    }

    public double attack(Character target) {
        return calculateDamage(target, 1); // No bonus multiplier for a regular attack
    }

    public void takeDamage(double damage) {
        double mitigatedDamage = damage * (1 - defense * 0.01);
        this.health -= mitigatedDamage;
        if (health <= 0) {
            health = 0;
            living_status = false;
        }
        System.out.println(this.getClass().getSimpleName()  + " takes " + mitigatedDamage + " HP loss as result. Their health now is " + health);
    }

    // Assassin-specific methods
    // Sneak attack deals 50% more damage than a regular attack, and can only be used if the Assassin is in stealth-mode.
    public double sneakAttack(Character target) {
        double bonusMultiplier = 1.5; // Sneak attacks deal 50% more damage
        System.out.println(this.getClass().getSimpleName()  + " attempts a sneak attack!");
        return calculateDamage(target, bonusMultiplier);
    }
    public void setStealth(boolean stealth) {
        this.stealth = stealth;
    }
    public boolean isStealth() {
        return stealth;
    }
}
