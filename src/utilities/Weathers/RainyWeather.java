package Weathers;

import entities.Character;
import entities.Archers.Archer;
import entities.Assassins.Assassin;
import entities.Fighters.Fighter;
import entities.Healers.Healer;

public class RainyWeather extends Weather {
    public RainyWeather() {
        super("Rainy",
                "Decreases Assassin’s armor by 20, Fighter’s damage by 10; increases Archer’s damage by 10, Healer’s healing by 20.");
    }

    @Override
    public void applyEffects(Character character) {
        switch (character) {
            case Assassin assassin -> {
                assassin.setDefense(assassin.getDefense() - 20);
                if (assassin.getDefense() < 5) {
                    assassin.setDefense(5);
                }
            }
            case Fighter fighter -> {
                fighter.setDamage(fighter.getDamage() - 10);
                if (fighter.getDamage() < 10) {
                    fighter.setDamage(10);
                }
            }
            case Archer archer -> {
                archer.setDamage(archer.getDamage() + 10);
                if (archer.getDamage() < 10) {
                    archer.setDamage(10);
                }
            }
            case Healer healer -> {
                healer.setHealAmount(healer.getHealAmount() + 20);
                if (healer.getHealAmount() < 5) {
                    healer.setHealAmount(5);
                }
            }
            default -> {}
        }
    }

    @Override
    public void removeEffects(Character character) {
        switch (character) {
            case Assassin assassin -> assassin.setDefense(assassin.getDefense() + 20);
            case Fighter fighter -> fighter.setDamage(fighter.getDamage() + 10);
            case Archer archer -> archer.setDamage(archer.getDamage() - 10);
            case Healer healer -> healer.setHealAmount(healer.getHealAmount() - 20);
            default -> {}
        }
    }

}
