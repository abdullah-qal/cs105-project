package entities;

import java.util.Random;

// TODO: Clean up this class and implement all the subclasses of Character
// TODO: Fix the order of the consturctors' parameters in the subclasses and make them uniform
import game.*;

public abstract class Character {
    protected int position; // Will start off on either 0 or 200 depending on the team.
    protected double damage; // Will be from 0 to 200 (tentative)
    protected double health; // Will be from 0 to 500 (tentative)
    protected double maxHealth; // Will be from 0 to 500 (tentative)
    protected double defense; // Will be from 0 to 100 (tentative)
    protected int movementSpeed; // Will be from 0 to 50 (tentative)
    protected int range; // Will be from 0 to 50 (tentative)
    protected int healing; // Will be from 0 to 50 (tentative)
    protected boolean living_status; // true represents them being alive; false they are dead
    protected double critRate, critDmg;
    protected int coolDown;

    public Character(int position, double damage, double health, double maxHealth, double defense, int movementSpeed,
            int range,
            double critRate, double critDmg) {
        this.position = position;
        this.damage = damage;
        this.health = health;
        this.maxHealth = maxHealth;
        this.defense = defense;
        this.movementSpeed = movementSpeed;
        this.range = range;
        this.critRate = critRate;
        this.critDmg = critDmg;
        this.living_status = true;
        this.coolDown = 0;
    }

    // Defines movement of characters
    public static boolean moveCharacter(Character character, int direction) {
        int newPosition = character.getPosition() + (character.movementSpeed * direction);
        if (newPosition < 0 || newPosition > 200) {
            Game.clearScreen();
            System.out.println("You can't move out of the bounds of the field. Try again.\n");
            return false;
        }
        character.setPosition(newPosition);
        Game.clearScreen();
        System.out.println(character.getClass().getSimpleName() + " has moved to position: " + newPosition + "\n");
        return true;
    }

    // Outgoing DMG calculator from any sort of attack with a specified bonus multiplier
    protected double calculateDamage(Character target, double bonusMultiplier) { //
        Random random = new Random();
        double randomValue = random.nextDouble() * 100; // Random number between 0 and 100
        boolean isCritRate = randomValue < critRate; // Checks if crit rate happens

        double baseDamage = isCritRate ? damage * (1 + critDmg * 0.01) : damage;
        if (isCritRate) {
            Game.clearScreen();
            System.out.println(
                    this.getClass().getSimpleName() + " landed a crit! It resulted in " + baseDamage + " dmg.");
        } else {
            Game.clearScreen();
            System.out
                    .println(this.getClass().getSimpleName() + " landed a normal hit yielding " + baseDamage + " dmg.");
        }

        return baseDamage * bonusMultiplier;
    }

    // Regular attack without any additional DMG bonus
    public double attack(Character target) {
        return calculateDamage(target, 1);
    }
    // Incoming DMG calculator
    public void takeDamage(double damage) { 
        double mitigatedDamage = damage * (1 - defense * 0.01);
        this.health -= mitigatedDamage;
        if (health <= 0) {
            health = 0;
            living_status = false;
        }
        System.out.println(this.getClass().getSimpleName() + " takes " + mitigatedDamage
                + " HP loss as result. Their health now is " + health +"\n");
    }

    // Getters and Setters
    public int getPosition() {
        return position;
    }

    public double getHealth() {
        return health;
    }

    public int getRange() {
        return range;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public boolean isLiving_status() {
        return living_status;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public void decrementCooldown() {
        if (coolDown > 0) {
            coolDown--;
        }
    }
}
