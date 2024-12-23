package entities.Fighters;

import entities.Character;

public abstract class Fighter extends Character {
    protected boolean anger;
    public Fighter(int position, double damage, double health, double maxHealth, double defense, int movementSpeed,
            int range, double critRate, double critDmg) {
        super(position, damage, health, maxHealth, defense, movementSpeed, range, critRate, critDmg);
        anger = false;
    }

    // Fighter-specific methods
    // anger increases Fighters' defense by %30 and it can only be used if Fighters
    // are in rage.
    public void activateAnger() {
        if (!anger) {
            anger = true;
            defense *= 1.3;// increases defense by %30.
            System.out.println(
                    this.getClass().getSimpleName()
                            + " howls in anger as berserker rage overcomes him! His Defense has increased to "
                            + defense + "\n");
        } else {
            System.out.println(this.getClass().getSimpleName() + " is already enraged. \n");
        }
    }

    public void deactivateAnger() {
        anger = false;
        defense /= 1.3; // Revert defense to its original value
        System.out.println(this.getClass().getSimpleName() + " has calmed down. His Defense is reverted back to " + defense + " DEF\n");
    }

    public boolean isAngerActive() {
        return anger;
    }
}
