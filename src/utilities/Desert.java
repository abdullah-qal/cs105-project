package utilities;
import entities.Character;
public Desert extends Map{
    public Desert() {
        super("Desert", "Movement speed decrease 5,Range increase 5, Defense decrease by 0.9.");
    }
    public void applyEffects(Character[] characters) {
        for (Character character : characters) {
            // Decrease movement speed 5 due to the sand.
            character.setMovementSpeed((int) (character.getMovementSpeed() -5));

            // Increase range by 5 because of the open terrain.
            character.setRange((int) (character.getRange() +5 ));

            // Decrease defense by 0.9 due to the lack of cover.
            character.setDefense((int) (character.getDefense() * 0.9));
        }
    }
}
//Desert subclass for the map. It decreases character's stats due to the harsh conditions. Remember that the weak ones are  eliminated!

