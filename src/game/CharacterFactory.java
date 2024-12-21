package game;

import entities.*;

public class CharacterFactory {

    static entities.Character createCharacter(String name, int position) {
        switch (name.toLowerCase()) {
            case "mortem":
                return new Mortem(position);
            case "trova":
                return new Trova(position);
            case "sanita":
                return new Sanita(position);
            case "nutrix":
                return new Nutrix(position);
            default:
                throw new IllegalArgumentException("Invalid character: " + name);
        }
    }
}
