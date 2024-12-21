package entities;

import java.util.Random;

import game.Game;

public abstract class Fighter extends Character{
    protected boolean anger;

    public Fighter(int position,double damage,double health,double maxHealth,double defense){
        super(position, damage, health, maxHealth,defense, 15, 50, 20, 50);//We want to ensure that the crit rate for fighters will be less than assassins but their crit damage will be more than assassins.
        this.anger=false // If fighter is in anger mode, enables anger mode which increases it's defense.
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
            System.out.println(this.getClass().getSimpleName()  + " landed a normal hit yielding " + baseDamage + " dmg.");
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
        System.out.println(this.getClass().getSimpleName()  + " takes " + mitigatedDamage + " HP loss as result. Their health now is " + health);
    }
    //Fighter-specific methods
    //anger increases Fighters' defense by %30 and it can only be used if Fighters' are in anger mode.
    public void activateAnger(){
        if (!anger){
            anger=true;
            defense*=1.3;//increases defense by %30.
            System.out.println(this.getClass().getSimpleName() + " is now in Anger mode! Defense increased to " + defense);
        } else {
            System.out.println(this.getClass().getSimpleName() + " is already in Anger mode.");
        }
    }
    public void deactivateAnger() {
        if (anger) {
            anger = false;
            defense /= 1.3; // Revert defense to its original value
            System.out.println(this.getClass().getSimpleName() + " has calmed down. Defense reverted to " + defense);
        } else {
            System.out.println(this.getClass().getSimpleName() + " is not in Anger mode.");
        }
    }
    public boolean isAngry(){
        return anger;
        }
    }

}