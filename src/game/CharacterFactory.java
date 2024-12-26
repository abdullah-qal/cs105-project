package game;

import entities.Archers.Cito;
import entities.Archers.Sagitta;
import entities.Assassins.Mortem;
import entities.Assassins.Torva;
import entities.Fighters.Tigris;
import entities.Fighters.Ursi;
import entities.Healers.Nutrix;
import entities.Healers.Sanita;
import entities.Wizards.Kanzo;
import entities.Wizards.Ulra;

public class CharacterFactory {

    static entities.Character createCharacter(String name, int position) {
        switch (name.toLowerCase()) {
            case "mortem":
                return new Mortem(position);
            case "torva":
                return new Torva(position);
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
            case "kanzo":
                return new Kanzo(position);
            case "ulra":
                return new Ulra(position);
            default:
                throw new IllegalArgumentException("Invalid character: " + name);
        }
    }
}
