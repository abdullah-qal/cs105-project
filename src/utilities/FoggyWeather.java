package utilities;

import entities.Character;

public class FoggyWeather extends Weather {
    public FoggyWeather() {
        super("Foggy", "Increases Assassin’s damage by 10, increases Archer’s defense by 5; decreases Fighter’s defense by 10, decreases Healer’s healing by 5.");
    }
    @Override
    public void applyEffects(Character[] characters) {
        for (Character character : characters) {
            switch (character.getType()) {
                case "Assassin" -> character.setDamage(character.getDamage() +10);
                case "Fighter" -> character.setDefense(character.getDefense() -10);
                case "Archer" -> character.setDefense(character.getDefense() +5);
                case "Healer" -> character.setHealing(character.getHealing() -5);
            }
        }
    }
}
