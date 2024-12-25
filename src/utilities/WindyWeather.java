public class WindyWeather extends Weather {
    public WindyWeather() {
        super("Windy", "Decreases Assassin’s movement speed by 5, decreases Archer’s damage by 10; increases Fighter’s defense by 10, increases Healer’s healing by 5.");
    }
    @Override
    public void applyEffects(Character[] characters) {
        for (Character character : characters) {
            switch (character.getType()) {
                case "Assassin" -> character.setMovementSpeed(character.getMovementSpeed() -5);
                case "Fighter" -> character.setDefense(character.getDefense() +10);
                case "Archer" -> character.setDamage(character.getDamage() -10);
                case "Healer" -> character.setHealing(character.getHealing() +5);
            }
        }
    }
}
