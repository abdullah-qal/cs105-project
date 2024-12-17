package entities;
// Abstract base class for characters in the game

public abstract class Character {
    // This class serves as the blueprint for all character types (Assassin, Archer,
    // Fighter, Healer)
    // Subclasses should implement specific behaviors and properties for each
    // character type

    // Attributes common to all characters
    protected String name; // The character's name (e.g., Torva, Mortem)
    protected int damage;
    protected int health;
    protected int armor;
    protected int movementSpeed;
    protected int range;
    protected int healing; // Default to 0 unless it's a Healer

    public Character(String name, int damage, int health, int armor, int movementSpeed, int range, int healing) {
        this.name = name;
        this.damage = damage;
        this.health = health;
        this.armor = armor;
        this.movementSpeed = movementSpeed;
        this.range = range;
        this.healing = healing;
    }

    // Those will be the abstract methods you will be implementing for each
    // character
    public abstract void move(int distance); // Defines how the character moves

    public abstract void attack(Character target); // Defines how the character attacks

    public abstract void takeDamage(int damage); // Handles damage calculation

    // Optional method for healing (default: no healing capability except for
    // healers)
    public void heal(Character ally) {
        // Can be overridden if you are a healer
        throw new UnsupportedOperationException("This character cannot heal.");
    }

    // Getter and Setter methods for subclasses. You can remove those if you want later on 
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    // Add extra stuff here if you want
}
