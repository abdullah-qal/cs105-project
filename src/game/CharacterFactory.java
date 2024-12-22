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
            case "sagitta":
                return new Sagitta(position);
            case "cito":   
                return new Cito(position);
            case "ursi": 
                return new Ursi(position);
            case "tigris":
                return new Tigris(position);
            default:
                throw new IllegalArgumentException("Invalid character: " + name);
        }
    }
}
