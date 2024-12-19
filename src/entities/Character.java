package entities;

public abstract class Character {
    protected int position; // Will start off on either 0 or 1200 depending on the team.
    protected double damage; // Will be from 0 to 200 (tentative)
    protected double health; // Will be from 0 to 500 (tentative)
    protected double defense; // Will be from 0 to 100 (tentative)
    protected int movementSpeed; // Will be from 0 to 20 (tentative)
    protected int range; // Will be from 0 to 50 (tentative)
    protected int healing; // Will be from 0 to 50 (tentative)
    protected boolean living_status; // true represents them being alive; false they are dead

    public Character(String name, int position, double damage, double health, double defense, int movementSpeed,
            int range) {
        this.name = name;
        this.position = position;
        this.damage = damage;
        this.health = health;
        this.defense = defense;
        this.movementSpeed = movementSpeed;
        this.range = range;
        this.living_status = true;
    }

    // public abstract void move(int distance);
    // I think it's probably better to implement the above method in Game
    protected abstract double calculateDamage(Character target, double bonusMultiplier); // Usually is the same as attack,
                                                                                      // just in case there are other
                                                                                      // attack methods, e.g.,
                                                                                      // sneakAttack

    public abstract double attack(Character target); // How the character attacks, yields the pure DMG applied

    public abstract void takeDamage(int damage); // How the character takes damage, yields the net health loss
}
