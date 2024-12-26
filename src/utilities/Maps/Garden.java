package Maps;

import entities.Character;
import entities.Healers.Healer;

import java.util.Random;
public class Garden extends Map{
    public Garden(){
        super("Garden","Increases every champion's armor by 20, healing zones and overgowth may occur");
    }
    public void applyEffects(Character character) {
        Random random = new Random();
            character.setDefense(character.getDefense() + 20);
            if (character instanceof Healer healer && random.nextInt(100) < 15) { // 20% chance of healing zone boost
                healer.setHealAmount(healer.getHealAmount() * 1.5);
                System.out.println("A healing zone boosts " + healer.getClass().getSimpleName() + "'s healing!");
            }
            if (random.nextInt(100) < 20) { // 20% chance for slow due to overgrowth
                character.setMovementSpeed((int) (character.getMovementSpeed() -5));
                System.out.println(character.getClass().getSimpleName() + " is slowed by dense flowers.");
            }
    }
}
//Garden subclass for the map. The map garden increases every champion's armor by 20 by feeling them at home, by 15% change of feeling zone boosts healing by half and by %20 change of overgrowth reduces the movement speed by 5!