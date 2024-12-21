package entities;

import java.util.Random;

import game.Game;

public abstract class Archer extends Character{
    protected boolean visionActive; //Whether vision skill is active or not.
    public Archer(int position, double damage, double health, double maxHealth, double defense) {
        super(position, damage, health, maxHealth, defense, 20, 25, 10, 30);
        // Archers have lower crit rate (10%) and crit damage (30%) compared to Assassins and Fighters.
        this.visionActive = false;
    }
    protected double calculateDamage(Character target, double bonusMultiplier) {
        Random random = new Random();
        double randomValue = random.nextDouble() * 100; // Random number between 0 and 100
        boolean isCritRate = randomValue < critRate; // Checks if crit rate happens

        double baseDamage = isCritRate ? damage * (1 + critDmg * 0.01) : damage;
        if (isCritRate) {
            Game.clearScreen();
            System.out.println(this.getClass().getSimpleName() + " landed a crit! It resulted in " + baseDamage + " dmg.");
        } else {
            Game.clearScreen();
            System.out.println(this.getClass().getSimpleName() + " landed a normal hit yielding " + baseDamage + " dmg.");
        }

        return baseDamage * bonusMultiplier;
    }

    public double attack(Character target) {
        return calculateDamage(target, 1); // No bonus multiplier for a regular attack
    }

    public void takeDamage(double damage) {
        double mitigatedDamage = damage * (1 - defense * 0.01);
        this.health -= mitigatedDamage;
        if (health <= 0) {
            health = 0;
            living_status = false;
        }
        System.out.println(this.getClass().getSimpleName() + " takes " + mitigatedDamage + " HP loss as result. Their health now is " + health);
    }
    //Archer specific methods
    //vision doubles Archer's range temporarily.
    public void activateVision(){
    if(!visionActive){
    visionActive=true;
    range*=2;
        System.out.println(this.getClass().getSimpleName() + " has activated Vision! Range is now " + range);
    } else {
        System.out.println(this.getClass().getSimpleName() + "'s Vision is already active.");
    }
    }
    public void deactivateVision() {
        if (visionActive) {
            visionActive = false;
            range /= 2; // Reverts range to its original value
            System.out.println(this.getClass().getSimpleName() + " has deactivated Vision. Range reverted to " + range);
        } else {
            System.out.println(this.getClass().getSimpleName() + "'s Vision is not active.");
        }
    }
    public boolean isVisionActive() {
        return visionActive;
    }

}