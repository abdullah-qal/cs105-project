package entities;

import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

// TODO: Clean up this class and implement all the subclasses of Character
// TODO: Fix the order of the consturctors' parameters in the subclasses and make them uniform
import game.*;

public abstract class Character {
    protected int position;
    protected double damage;
    protected double health;
    protected double maxHealth;
    protected double defense;
    protected int movementSpeed;
    protected int range;
    protected boolean living_status;
    protected double critRate, critDmg;
    protected int coolDown;

    public Character(int position, double damage, double health, double maxHealth, double defense, int movementSpeed,
            int range, double critRate, double critDmg) {
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
    public static boolean moveCharacter(Scanner input, Character character, int direction) {
        Game.clearScreen();
        System.out.println(
                "By how much would you like to move? (Choose a value that is a multiple of 5 between 0 and "
                        + character.movementSpeed + "m)");
        int newPosition;
        try {
            int number = input.nextInt();
            input.nextLine(); // Consume the newline character
            if (number < 0 || number > character.movementSpeed) {
                Game.clearScreen();
                System.out.println(
                        "Invalid input! You can't move faster than your movement speed or by a negative value.\n");
                return false;
            }
            if (number % 5 != 0) {
                Game.clearScreen();
                System.out.println("Invalid input! You can only move in increments of 5.\n");
                return false;
            }
            newPosition = character.position + direction * number;
            if (newPosition < 0 || newPosition > 200) {
                Game.clearScreen();
                System.out.println("You can't move beyond the bounds of the arena!\n");
                return false;
            }
            character.position = newPosition;
            Game.clearScreen();

            System.out
                    .println(
                            character.getClass().getSimpleName() + " moved to position " + character.position + "m.\n");
            return true;
        } catch (InputMismatchException e) {
            Game.clearScreen();
            System.out.println("Invalid input! Please enter a valid integer.\n");
            input.nextLine();
            return false;
        }
    }

    // Outgoing DMG calculator from any sort of attack with a specified bonus
    // multiplier
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

    // Attempts to attack the target character
    public static boolean attemptAttack(entities.Character attacker, entities.Character target) {
        if (!target.isLiving_status()) {
            Game.clearScreen();
            System.out.println(target.getClass().getSimpleName() + " is already dead!\n");
            return false;
        }
        if (attacker.getRange() < Math.abs(attacker.getPosition() - target.getPosition())) {
            Game.clearScreen();
            System.out.println(target.getClass().getSimpleName() + " is out of range for an attack to be performed!\n");
            return false;
        }
        return true;
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
                + " HP loss as result. Their health now is " + health + "\n");
        if (!living_status) {
            System.out.println(
                    "As a result of this decisive attack, " + this.getClass().getSimpleName() + " has fallen.\n");
        }
    }

    // Getters and Setters
    public int getPosition() {
        return position;
    }

    public double getDamage() {
        return damage;
    }

    public double getHealth() {
        return health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getDefense() {
        return defense;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public int getRange() {
        return range;
    }

    public double getCritRate() {
        return critRate;
    }

    public double getCritDmg() {
        return critDmg;
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
