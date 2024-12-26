package entities.Healers;

import java.util.Random;

import entities.Character;
import game.Game;

public abstract class Healer extends Character {
    protected double healAmount;
    protected int divineHealChance;

    public Healer(int position, int damage, int health, int maxHealth, double defense, int movementSpeed,
            int range, double critRate, double critDmg, double healAmount, int divineHealChance) {
        super(position, damage, health, maxHealth, defense, movementSpeed, range, critRate, critDmg);
        this.healAmount = healAmount * HEALTH_MULTIPLIER;
        this.divineHealChance = divineHealChance;
    }

    // Healer-specific methods
    public boolean heal(Character target) {
        Random random = new Random();
        double randomValue = random.nextDouble() * 100; // Random number between 0 and 100
        boolean isDivineHeal = randomValue < divineHealChance; // Checks if divine heal happens
        if (!target.isLiving_status()) {
            System.out.println("You can't heal a dead character!\n");
            return false;
        }
        if (this.range < Math.abs(this.position - target.getPosition())) {
            System.out.printf(
                    "%s is too far away to be healed!%n%n",
                    target.getClass().getSimpleName());
            return false;
        }
        int baseHeal = (int) (isDivineHeal ? healAmount * 1.75 : healAmount);
        if (target.getHealth() + baseHeal > target.getMaxHealth()) {
            target.setHealth(target.getMaxHealth());
        } else {
            target.setHealth(target.getHealth() + baseHeal);
        }
        if (isDivineHeal) {
            Game.clearScreen();
            if (target == this) {
                System.out.printf(
                        "%s landed a divine heal! It healed themselves by %s HP. Their health is now %s HP.%n%n",
                        this.getClass().getSimpleName(),
                        normalisedValue(baseHeal),
                        normalisedValue(target.getHealth()));
            } else {
                System.out.printf(
                        "%s landed a divine heal! It healed %s by %s HP. Their health is now %s HP.%n%n",
                        this.getClass().getSimpleName(),
                        target.getClass().getSimpleName(),
                        normalisedValue(baseHeal),
                        normalisedValue(target.getHealth()));
            }
        } else {
            Game.clearScreen();
            if (target == this) {
                System.out.printf(
                        "%s landed a regular heal. It healed themselves by %s HP. Their health is now %s HP.%n%n",
                        this.getClass().getSimpleName(),
                        normalisedValue(baseHeal),
                        normalisedValue(target.getHealth()));
            } else {
                System.out.printf(
                        "%s landed a regular heal. It healed %s by %s HP. Their health is now %s HP.%n%n",
                        this.getClass().getSimpleName(),
                        target.getClass().getSimpleName(),
                        normalisedValue(baseHeal),
                        normalisedValue(target.getHealth()));
            }
        }
        return true;
    }
    public double getHealAmount() { return healAmount; }
    public void setHealAmount(double healAmount) { this.healAmount = healAmount; }
}
