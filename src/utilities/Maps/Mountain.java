package Maps;

import entities.Character;
import entities.Healers.Healer;

public class Mountain extends Map {
    public Mountain() {
        super("Mountain",
                "Decreases every champion's damage by 10 and decreases champion's health by 0.1, also reduces healer's healing by 0.2.");
    }

    public void applyEffects(Character character) {
        // Decreasing the damage of each character due to harsh battle conditions.
        character.setDamage(character.getDamage() - 10);
        // Decreasing the HP of each character due to harsh battle conditions.
        character.setMaxHealth((int) (character.getMaxHealth() * 0.9));

        if (character instanceof Healer healer) {
            healer.setHealAmount((int) (healer.getHealAmount() * 0.8)); // Reduce healing
        }
    }
}
