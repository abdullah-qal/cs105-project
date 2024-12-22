package entities;

public abstract class Archer extends Character {
    protected boolean visionActive; // Whether vision skill is active or not.

    public Archer(int position, double damage, int movementSpeed, double defense, int range) {
        super(position, damage, 150, 150, defense, movementSpeed, range, 10, 30);
        // Archers have lower crit rate (10%) and crit damage (30%) compared to
        // Assassins and Fighters.
        this.visionActive = false;
    }

    // Archer specific methods
    // vision doubles Archer's range temporarily.
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