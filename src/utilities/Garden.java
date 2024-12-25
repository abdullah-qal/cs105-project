package utilities;

import entities.Character;
public class Garden extends Map{
    public Garden(){
        super("Garden","Increases every champion's armor by 20, healing zones and overgowth may occur");
    }
    public void applyEffects(Character[] characters) {
        Random random = new Random();
        for (Character character : characters) {
            character.setArmor(character.getArmor() + 20);
            if (character instanceof Healer healer && random.nextInt(100) < 15) { // 20% chance of healing zone boost
                healer.setHealing(healer.getHealing() * 1.5);
                System.out.println("A healing zone boosts " + healer.getClass().getSimpleName() + "'s healing!");
            }
            if (random.nextInt(100) < 20) { // 20% chance for slow due to overgrowth
                character.setMovementSpeed((int) (character.getMovementSpeed() -5));
                System.out.println(character.getClass().getSimpleName() + " is slowed by dense flowers.");
            }
        }
    }
}
//Garden subclass for the map. The map garden increases every champion's armor by 20 by feeling them at home, by 15% change of feeling zone boosts healing by half and by %20 change of overgrowth reduces the movement speed by 5!