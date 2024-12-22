package entities;

import java.util.Random;

import game.Game;

public abstract class Healer extends Character {
    protected double healRate;
    protected double divineHealChance;

    public Healer(int position, double health, double maxHealth, double healRate, double divineHealChance) {
        super(position, 20, health, maxHealth, 5, 15, 30, 0, 0);
        this.healRate = healRate; // regular healing rate
        this.divineHealChance = divineHealChance; // chance of divine heal. Heals triple the amount.
    }

    // Healer-specific methods
    public void heal(Character target) {
        Random random = new Random();
        double randomValue = random.nextDouble() * 100; // Random number between 0 and 100
        boolean isDivineHeal = randomValue < divineHealChance; // Checks if divine heal happens

        double baseHeal = isDivineHeal ? healRate * 3 : healRate;
        if (isDivineHeal) {
            Game.clearScreen();

            if (target == this) {
                System.out.println(this.getClass().getSimpleName() + " landed a divine heal! It healed themselves by "
                        + baseHeal + " HP. Their health is now "
                        + target.getHealth() + " HP\n");
            } else {
                System.out.println(this.getClass().getSimpleName() + " landed a divine heal! It healed "
                        + target.getClass().getSimpleName() + " by " + baseHeal + " HP. Their health is now "
                        + target.getHealth() + " HP\n");
            }
        } else {
            Game.clearScreen();
            if (target == this) {
                System.out.println(this.getClass().getSimpleName() + " landed a regular heal. It healed themselves by "
                        + baseHeal + " HP. Their health is now "
                        + target.getHealth() + " HP\n");
            } else {
                System.out.println(this.getClass().getSimpleName() + " landed a regular heal. It healed "
                        + target.getClass().getSimpleName() + " by " + baseHeal + " HP. Their health is now "
                        + target.getHealth() + " HP\n");
            }
        }
        if (target.getHealth() + baseHeal > target.getMaxHealth()) {
            target.setHealth(target.getMaxHealth());
            return;
        }
        target.setHealth(target.getHealth() + baseHeal);
    }
}
