package utilities;
import entities.Character;
public class Mountain extends Map{
    public Mountain(){
        super("Mountain","Decreases every champion's damage by 20 and decreases champion's health by 0.1, also reduces healer's healing by 0.2.");
    }
    public void applyEffects(Character[] characters) {
        for (Character character : characters) {
            //Decreasing the damage of each character due to harsh battle conditions.
            character.setDamage(character.getDamage() - 20);
            //Decreasing the HP of each character due to harsh battle conditions.
            character.setHealth(character.getHealth() * 0.9);
            if (character.getHealth() < 0) {
                character.setHealth(0);
                character.setLiving_status(false); // Mark character as dead if health is 0
            }
            if (character instanceof Healer healer) {
                healer.setHealing(healer.getHealing() * 0.8); // Reduce healing
                System.out.println(healer.getClass().getSimpleName() + " struggles to heal in the thin air.");
            }
        }
    }
}

