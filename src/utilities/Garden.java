package utilities;

import entities.Character;
public class Garden extends Map{
    public Garden(){
        super("Garden","Increases every champion's armor by 20.");
    }
    public void applyEffects(Character[] characters) {
        for (Character character : characters) {
            character.setArmor(character.getArmor() + 20);
        }
    }
}
//Garden subclass for the map. The map garden increases every champion's armor by 20 by feeling them at home!