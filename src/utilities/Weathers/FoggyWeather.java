package Weathers;

import entities.Character;
import entities.Healers.Healer;
import entities.Assassins.Assassin;
import entities.Archers.Archer;
import entities.Fighters.Fighter;

public class FoggyWeather extends Weather {
    public FoggyWeather() {
        super("Foggy",
                "Increases Assassin’s damage by 10, increases Archer’s defense by 5; decreases Fighter’s defense by 10, decreases Healer’s healing by 5.");
    }

    @Override
    public void applyEffects(Character character) {
        switch (character) {
            case Assassin assassin -> {
                assassin.setDamage(assassin.getDamage() + 10);
                if (assassin.getDamage() < 10) {
                    assassin.setDamage(10);
                }
            }
            case Fighter fighter -> {
                fighter.setDefense(fighter.getDefense() - 10);
                if (fighter.getDefense() < 5) {
                    fighter.setDefense(5);
                }
            }
            case Archer archer -> {
                archer.setDefense(archer.getDefense() + 5);
                if (archer.getDefense() < 5) {
                    archer.setDefense(5);
                }
            }
            case Healer healer -> {
                healer.setHealAmount(healer.getHealAmount() - 5);
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
            case Assassin assassin -> assassin.setDamage(assassin.getDamage() - 10);
            case Fighter fighter -> fighter.setDefense(fighter.getDefense() + 10);
            case Archer archer -> archer.setDefense(archer.getDefense() - 5);
            case Healer healer -> healer.setHealAmount(healer.getHealAmount() + 5);
            default -> {}
        }
    }

}
