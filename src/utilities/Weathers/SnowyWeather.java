package Weathers;

import entities.Character;
import entities.Archers.Archer;
import entities.Assassins.Assassin;
import entities.Fighters.Fighter;
import entities.Healers.Healer;

public class SnowyWeather extends Weather {
    public SnowyWeather() {
        super("Snowy",
                "Increases Assassin’s damage by 20, Fighter’s armor by 20; decreases Archer’s movement speed by 10, Healer’s damage by 20.");
    }

    @Override
    public void applyEffects(Character character) {
        switch (character) {
            case Assassin assassin -> {
                assassin.setDamage(assassin.getDamage() + 20);
                if (assassin.getDamage() < 10) {
                    assassin.setDamage(10);
                }
            }
            case Fighter fighter -> {
                fighter.setDefense(fighter.getDefense() + 20);
                if (fighter.getDefense() < 5) {
                    fighter.setDefense(5);
                }
            }
            case Archer archer -> {
                archer.setMovementSpeed(archer.getMovementSpeed() - 10);
                if (archer.getMovementSpeed() < 10) {
                    archer.setMovementSpeed(10);
                }
            }
            case Healer healer -> {
                healer.setDamage(healer.getDamage() - 20);
                if (healer.getDamage() < 10) {
                    healer.setDamage(10);
                }
            }
            default -> {}
        }
    }

    @Override
    public void removeEffects(Character character) {
        switch (character) {
            case Assassin assassin -> assassin.setDamage(assassin.getDamage() - 20);
            case Fighter fighter -> fighter.setDefense(fighter.getDefense() - 20);
            case Archer archer -> archer.setMovementSpeed(archer.getMovementSpeed() + 10);
            case Healer healer -> healer.setDamage(healer.getDamage() + 20);
            default -> {}
        }
    }

}
