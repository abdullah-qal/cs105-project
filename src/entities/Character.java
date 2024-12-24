package entities;

import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

// TODO: Clean up this class and implement all the subclasses of Character
// TODO: Fix the order of the constructors' parameters in the subclasses and make them uniform
import game.*;

public abstract class Character {
    public final static int HEALTH_MULTIPLIER = 20; // Using 20 to avoid floating-point manipulation. All rationals with
                                                    // denominators 5^m 2^n are guaranteed to be terminating.

    protected int position;
    protected int damage;
    protected int health;
    protected int maxHealth;
    protected double defense;
    protected int movementSpeed;
    protected int range;
    protected boolean living_status;
    protected double critRate;
    protected double critDmg;
    protected int coolDown;
    private game.Team team; // Reference to the Team it is in

    public Character(int position, int damage, int health, int maxHealth, double defense, int movementSpeed,
            int range, double critRate, double critDmg) {
        this.position = position;
        this.damage = damage * HEALTH_MULTIPLIER;
        this.health = health * HEALTH_MULTIPLIER;
        this.maxHealth = maxHealth * HEALTH_MULTIPLIER;
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
        System.out.printf(
                "By how much would you like to move? (Choose a value that is a multiple of 5 between 0 and %d m): ",
                character.movementSpeed);
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
            System.out.printf("%s moved to position %d m.%n%n",
                    character.getClass().getSimpleName(),
                    character.position);
            return true;
        } catch (InputMismatchException e) {
            Game.clearScreen();
            System.out.println("Invalid input! Please enter a valid integer.\n");
            input.nextLine(); // Consume the invalid input
            return false;
        }
    }

    // Outgoing DMG calculator from any sort of attack with a specified bonus
    // multiplier
    protected int calculateDamage(Character target, double bonusMultiplier) {
        Random random = new Random();
        double randomValue = random.nextDouble() * 100; // Random number between 0 and 100
        boolean isCritRate = randomValue < critRate; // Checks if crit rate happens

        double baseDamageFloat = isCritRate ? damage * (1 + critDmg * 0.01) : damage;
        int baseDamage = (int) (baseDamageFloat * bonusMultiplier);

        if (isCritRate) {
            Game.clearScreen();
            System.out.printf("%s landed a crit! It resulted in %.2f dmg.%n",
                    this.getClass().getSimpleName(),
                    normalisedValue(baseDamage));
        } else {
            Game.clearScreen();
            System.out.printf("%s landed a normal hit yielding %.2f dmg.%n",
                    this.getClass().getSimpleName(),
                    normalisedValue(baseDamage));
        }

        return (int) (baseDamageFloat * bonusMultiplier);
    }

    // Regular attack without any additional DMG bonus
    public int attack(Character target) {
        return calculateDamage(target, 1);
    }

    // Attempts to attack the target character
    public static boolean attemptAttack(Character attacker, Character target) {
        if (!target.isLiving_status()) {
            Game.clearScreen();
            System.out.printf(
                    "%s is already dead!%n%n",
                    target.getClass().getSimpleName());
            return false;
        }
        if (attacker.getRange() < Math.abs(attacker.getPosition() - target.getPosition())) {
            Game.clearScreen();
            System.out.printf(
                    "%s is out of range for an attack to be performed!%n%n",
                    target.getClass().getSimpleName());
            return false;
        }
        return true;
    }

    // Incoming DMG calculator
    public void takeDamage(int damage) {
        int mitigatedDamage = (int) (damage * (1 - defense * 0.01));
        this.health -= mitigatedDamage;

        if (health <= 0) {
            health = 0;
            living_status = false;
        }

        System.out.printf(
                "%s takes %.2f HP loss as result. Their health now is %.2f%n",
                this.getClass().getSimpleName(),
                normalisedValue(mitigatedDamage),
                normalisedValue(health));

        if (!living_status) {
            System.out.printf(
                    "As a result of this decisive attack, %s has fallen.%n",
                    this.getClass().getSimpleName());
        }
    }

    // Getters and Setters
    public int getPosition() { return position; }
    public int getDamage() { return damage; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public double getDefense() { return defense; }
    public int getMovementSpeed() { return movementSpeed; }
    public int getRange() { return range; }
    public double getCritRate() { return critRate; }
    public double getCritDmg() { return critDmg; }
    public boolean isLiving_status() { return living_status; }
    public int getCoolDown() { return coolDown; }
    public game.Team getTeam() { return team; }

    public void setHealth(int health) { this.health = health; }
    public void setCoolDown(int coolDown) { this.coolDown = coolDown; }
    public void setTeam(game.Team team) { this.team = team; }
    public void decrementCooldown() { if (coolDown > 0) coolDown--; }


    // Gets the actual value back for printing purposes
    public static double normalisedValue(int x) {
        return (double) x / (double) HEALTH_MULTIPLIER;
    }
}
