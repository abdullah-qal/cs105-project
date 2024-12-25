package utilities;

import entities.Character;

public class RainyWeather extends Weather {
    public RainyWeather() {
        super("Rainy",
                "Decreases Assassin’s armor by 20, Fighter’s damage by 20; increases Archer’s damage by 10, Healer’s healing by 20.");
    }
    //Assassin's armor decreased by 20, Fighter's damage decreased by 20, Archer's damage increased by 10, Healer's healing increased by 20.

    @Override
    public void applyEffects(Character[] characters) {
        for (Character character : characters) {
            switch (character.getType()) {
                case "Assassin" -> character.setArmor(character.getArmor() - 20);
                case "Fighter" -> character.setDamage(character.getDamage() - 20);
                case "Archer" -> character.setDamage(character.getDamage() + 10);
                case "Healer" -> character.setHealing(character.getHealing() + 20);
            }
        }
    }
}
