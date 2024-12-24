package entities.Fighters;

import entities.Character;

public abstract class Fighter extends Character {
    protected double baseDefense;
    protected double angerDefense;
    protected boolean anger;

    public Fighter(int position, int damage, int health, int maxHealth, double defense, int movementSpeed,
            int range, double critRate, double critDmg) {
        super(position, damage, health, maxHealth, defense, movementSpeed, range, critRate, critDmg);
        baseDefense = defense;
        angerDefense = defense * 1.3;
        anger = false;
    }

    // Fighter-specific methods
    // Anger increases Fighters' defense by 30% and it can only be used if Fighters
    // are in rage.
    public void activateAnger() {
        if (!anger) {
            anger = true;
            defense = angerDefense; // Increases defense by 30%
            System.out.printf(
                    "%s howls in anger as berserker rage overcomes him! His Defense has increased to %.2f DEF.%n%n",
                    this.getClass().getSimpleName(), defense);
        } else {
            System.out.printf(
                    "%s is already enraged!%n%n",
                    this.getClass().getSimpleName());
        }
    }

    public void deactivateAnger() {
        anger = false;
        defense = baseDefense; // Revert defense to its original value
        System.out.printf(
                "%s has calmed down. His Defense is reverted back to %.2f DEF.%n%n",
                this.getClass().getSimpleName(), defense);
    }

    public boolean isAngerActive() {
        return anger;
    }
}
