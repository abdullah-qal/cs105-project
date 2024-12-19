package entities;

import java.util.Random;

public abstract class Assassin extends Character {
    protected boolean stealth;
    protected double critRate, critDmg;

    public Assassin(int position, double damage, double health, double defense) {
        super(position, damage, health, defense, 50, 10);
        this.stealth = false; // If Assassin is currently in stealth-mode, enables sneakAttack method.
        this.critRate = 20; // between 0 to 100
        this.critDmg = 50;
    }

    // Basic methods from Character
    protected double calculateDamage(Character target, double bonusMultiplier) {
        Random random = new Random();
        double randomValue = random.nextDouble() * 100; // Random number between 0 and 100
        boolean isCritRate = randomValue < critRate; // Checks if crit rate happens

        if (!(Math.abs(position - target.position) < range)) { // Check if target is within range
            System.out.println("Character is not within range! Try to move closer.");
            return 0;
        }

        double baseDamage = isCritRate ? damage * (1 + critDmg * 0.01) : damage;
        if (isCritRate) {
            System.out.println(this.getClass().getSimpleName() + " landed a crit! It resulted in " + baseDamage + " dmg.");
        } else {
            System.out.println(this.getClass().getSimpleName()  + " landed a normal hit yielding " + baseDamage + " dmg.");
        }

        return baseDamage * bonusMultiplier;
    }

    public double attack(Character target) {
        return calculateDamage(target, 1); // No bonus multiplier for a regular attack
    }

    public void takeDamage(int damage) {
        int mitigatedDamage = (int) (damage * (1 - defense * 0.01));
        this.health -= mitigatedDamage;
        System.out.println(this.getClass().getSimpleName()  + " takes " + mitigatedDamage + ". Their health now is " + health);
    }

    // Assassin-specific methods
    public double sneakAttack(Character target) {
        double bonusMultiplier = 1.5; // Sneak attacks deal 50% more damage
        System.out.println(this.getClass().getSimpleName()  + " attempts a sneak attack!");
        return calculateDamage(target, bonusMultiplier);
    }
}
