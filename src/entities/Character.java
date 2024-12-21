package entities;
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

    public Character(int position, double damage, double health,double maxHealth, double defense, int movementSpeed, int range,
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
    }

    public static final String[] SUPPORTED_CHARACTERS = {"Mortem", "Trova", "Sanita", "Nutrix"};

    protected abstract double calculateDamage(Character target, double bonusMultiplier); // Calculates pure DMG applied

    public abstract double attack(Character target); // How the character attacks, yields the pure DMG applied

    public abstract void takeDamage(double damage); // How the character takes damage, yields the net health loss

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

    public static boolean moveCharacter(Character character, int direction) {
        int newPosition = character.getPosition() + (character.movementSpeed * direction);
        if (newPosition < 0 || newPosition > 200) {
            Game.clearScreen();
            System.out.println("You can't move out of the bounds of the field. Try again.");
            return false;
        }
        character.setPosition(newPosition);
        Game.clearScreen();
        System.out.println(character.getClass().getSimpleName() + " has moved to position: " + newPosition + "\n");
        return true;
    }
}
