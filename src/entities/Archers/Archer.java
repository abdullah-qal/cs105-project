package entities.Archers;

import entities.Character;

public abstract class Archer extends Character {
    protected boolean visionActive; // Whether vision skill is active or not.

    public Archer(int position, double damage, double health, double maxHealth, double defense, int movementSpeed,
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
            System.out.println(this.getClass().getSimpleName()
                    + " slowly opens his eyes with incredible sharpness! His attack range is now " + range + "m\n");
        } else {
            System.out.println(this.getClass().getSimpleName()
                    + "already sees sharply\n");
        }
    }

    public void deactivateVision() {
        visionActive = false;
        range /= 2; // Reverts range to its original value
        System.out.println(this.getClass().getSimpleName()
                + "'s sharp sight goes away. His attacking range is reverted back to " + range + "m\n");
    }

    public boolean isVisionActive() {
        return visionActive;
    }

}