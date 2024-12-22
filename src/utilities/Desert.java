package utilities;
import entities.Character;
public Desert extends Map{
    public Desert() {
        super("Desert", "Halves every champions movement speed.");
    }
    public void applyEffects(Character[] characters) {
        for (Character character : characters) {
            character.setMovementSpeed(character.getMovementSpeed() / 2);
        }
    }
}

