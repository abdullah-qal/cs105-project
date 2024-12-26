package Weathers;

import entities.Character;
import entities.Archers.Archer;
import entities.Assassins.Assassin;
import entities.Fighters.Fighter;
import entities.Healers.Healer;

public class WindyWeather extends Weather {
    public WindyWeather() {
        super("Windy",
                "Decreases Assassin’s movement speed by 5, decreases Archer’s damage by 10; increases Fighter’s defense by 10, increases Healer’s healing by 5.");
    }

    @Override
    public void applyEffects(Character character) {
        switch (character) {
            case Assassin assassin -> {
                assassin.setMovementSpeed(assassin.getMovementSpeed() - 20);
                if (assassin.getMovementSpeed() <= 10) {
                    assassin.setMovementSpeed(10);
                }
            }
            case Fighter fighter -> {
                fighter.setDefense(fighter.getDefense() - 10);
                if (fighter.getDefense() <= 5) {
                    fighter.setDefense(5);
                }
            }
            case Archer archer -> {
                archer.setDamage(archer.getDamage() + 5);
                if (archer.getDamage() <= 10) {
                    archer.setDamage(10);
                }
            }
            case Healer healer -> {
                healer.setHealAmount(healer.getHealAmount() - 5);
                if (healer.getHealAmount() <= 5) {
                    healer.setHealAmount(5);
                }
            }
            default -> {
            }
        }
    }

    public void removeEffects(Character character) {
        switch (character) {
            case Assassin assassin -> {
                assassin.setMovementSpeed(assassin.getMovementSpeed() + 20);
            }
            case Fighter fighter -> {
                fighter.setDefense(fighter.getDefense() + 10);
            }
            case Archer archer -> {
                archer.setDamage(archer.getDamage() - 5);
            }
            case Healer healer -> {
                healer.setHealAmount(healer.getHealAmount() + 5);
            }
            default -> {}
        }
    }
}
