package utilities;
import entities.Character;
public Desert extends Map{
    public Desert() {
        super("Desert", "Movement speed decrease by 0.8,Range increase by 1.2, Defense decrease by 0.9.");
    }
    public void applyEffects(Character[] characters) {
        for (Character character : characters) {
            // Decrease movement speed by 0.8 due to the sand.
            character.setMovementSpeed((int) (character.getMovementSpeed() * 0.8));

            // Increase range by 1.2 because of the open terrain.
            character.setRange((int) (character.getRange() * 1.2));

            // Decrease defense by 0.9 due to the lack of cover.
            character.setDefense((int) (character.getDefense() * 0.9));
        }
    }
}
//Desert subclass for the map. It decreases character's stats due to the harsh conditions. Remember that the weak ones are  eliminated!

