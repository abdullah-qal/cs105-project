package entities;

import java.util.Random;

import game.Game;

public abstract class Healer extends Character {
    protected double healRate;
    protected double divineHealChance;
    private int healCooldown;

    public Healer(int position, double health, double maxHealth, double healRate, double divineHealChance) {
        super(position, 20, health, maxHealth, 5, 15, 30, 0, 0);
        this.healRate = healRate; // regular healing rate
        this.divineHealChance = divineHealChance; // chance of divine heal. Heals triple the amount.
        this.healCooldown = 0; // Healers can heal once every two turns
    }

    // Basic methods from Character
    protected double calculateDamage(Character target, double bonusMultiplier) {
        return damage * bonusMultiplier;
    }

    public double attack(Character target) {
        return calculateDamage(target, 1); // No bonus multiplier or crit rate for healers
    }

    public void takeDamage(double damage) {
        double mitigatedDamage = damage * (1 - defense * 0.01);
        this.health -= mitigatedDamage;
        if (health <= 0) {
            health = 0;
            living_status = false;
        }
        System.out.println(this.getClass().getSimpleName() + " takes " + mitigatedDamage
                + " HP loss as result. Their health now is " + health);
    }

    // Healer-specific methods
    public void heal(Character target) {
        Random random = new Random();
        double randomValue = random.nextDouble() * 100; // Random number between 0 and 100
        boolean isDivineHeal = randomValue < divineHealChance; // Checks if divine heal happens 

        double baseHeal = isDivineHeal ? healRate * 3 : healRate;
        if (isDivineHeal) {
            Game.clearScreen();
            System.out.println(this.getClass().getSimpleName() + " landed a divine heal! It healed "
                    + target.getClass().getSimpleName() + " by " + baseHeal + " HP. Their health is now "
                    + target.getHealth() + "\n");
        } else {
            Game.clearScreen();
            System.out.println(this.getClass().getSimpleName() + " landed a regular heal. It healed "
                    + target.getClass().getSimpleName() + " by " + baseHeal + " HP. Their health is now "
                    + target.getHealth() + "\n");
        }
        if (target.getHealth() + baseHeal > target.getMaxHealth()) {
            target.setHealth(target.getMaxHealth());
            return;
        }
        target.setHealth(target.getHealth() + baseHeal);
    }

    public void decrementCooldown() {
        if (healCooldown > 0) {
            healCooldown--;
        }
    }

    public int getHealCooldown() {
        return healCooldown;
    }

    public void setHealCooldown(int healCooldown) {
        this.healCooldown = healCooldown;
    }
}
