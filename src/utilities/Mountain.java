package utilities;
import entities.Character;
public class Mountain extends Map{
    public Mountain(){
        super("Mountain","Decreases every champion's damage by 20.");
    }
    public void applyEffects(Character[] characters) {
        for (Character character : characters) {
            character.setDamage(character.getDamage() - 20);
        }
    }
}

