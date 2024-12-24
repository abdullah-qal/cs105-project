package entities.Archers;

import entities.Character;

public abstract class Archer extends Character {
    protected boolean visionActive; // Whether vision skill is active or not.

    public Archer(int position, int damage, int health, int maxHealth, double defense, int movementSpeed,
            int range, double critRate, double critDmg) {
        super(position, damage, health, maxHealth, defense, movementSpeed, range, critRate, critDmg);
        visionActive = false;
    }

    // Archer specific methods
    // Great Sight doubles Archer's range temporarily.
    public void activateVision() {
        if (!visionActive) {
            visionActive = true;
            range *= 2;
            System.out.printf(
                    "%s slowly opens his eyes with incredible sharpness! His attack range is now %d m.%n%n",
                    this.getClass().getSimpleName(),
                    range);
        } else {
            System.out.printf(
                    "%s already sees sharply.%n%n",
                    this.getClass().getSimpleName());
        }
    }

    public void deactivateVision() {
        visionActive = false;
        range /= 2; // Reverts range to its original value
        System.out.printf("%s's sharp sight goes away. His attacking range is reverted back to %d m.%n%n",
                this.getClass().getSimpleName(), range);
    }

    public boolean isVisionActive() {
        return visionActive;
    }
}
