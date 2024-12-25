package utilities;

import entities.Character;

public class SnowyWeather extends Weather {
    public SnowyWeather() {
        super("Snowy",
                "Increases Assassin’s damage by 20, Fighter’s armor by 20; decreases Archer’s movement speed by 10, Healer’s damage by 20.");
    }
    //Assassin's damage increased by 20, Fighter's armor increased by 20, Archer's movement speed decreased by its half and Healer's damage decreased by 20.

    @Override
    public void applyEffects(Character[] characters) {
        for (Character character : characters) {
            switch (character.getType()) {
                case "Assassin" -> character.setDamage(character.getDamage() + 20);
                case "Fighter" -> character.setArmor(character.getArmor() + 20);
                case "Archer" -> character.setMovementSpeed(character.getMovementSpeed() -10);
                case "Healer" -> character.setDamage(character.getDamage() - 20);
            }
        }
    }
}
