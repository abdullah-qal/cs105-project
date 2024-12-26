package entities;

import java.util.Scanner;

import entities.Archers.*;
import entities.Fighters.*;
import entities.Healers.*;
import entities.Wizards.*;
import entities.Assassins.*;

import game.*;

public class CharacterInfo {

    public static void getInfo(Scanner input) {
        while (true) {
            supportedCharacters();
            System.out.println("Which character would you like to learn more about? (Type q to return)");
            String choice = input.nextLine().toLowerCase();
    
            if (choice.equals("q")) {
                Game.clearScreen();
                break; 
            }
    
            Character[] characters = { new Mortem(0), new Torva(0), new Cito(0), new Sagitta(0), new Tigris(0),
                    new Ursi(0), new Nutrix(0), new Sanita(0), new Kanzo(0), new Ulra(0) };
    
            boolean found = false; 
            for (Character character : characters) {
                if (character.getClass().getSimpleName().toLowerCase().equals(choice)) {
                    Game.clearScreen();
                    System.out.printf(character.toString());
                    System.out.printf("Damage: %.2f DMG%n", Character.normalisedValue(character.getDamage()));
                    System.out.printf("Max Health: %.2f HP%n", Character.normalisedValue(character.getMaxHealth()));
                    System.out.printf("Defense: %.2f DEF%n", character.getDefense());
                    System.out.printf("Movement Speed: %d m/turn%n", character.getMovementSpeed());
                    System.out.printf("Range: %d m%n", character.getRange());
                    System.out.printf("Crit Rate: %.2f%%%n", character.getCritRate());
                    System.out.printf("Crit Damage: %.2f%%%n", character.getCritDmg());
                    if (character instanceof Healer healer) {
                        System.out.printf("Healing Amount: %.2f HP%n", Character.normalisedValue(healer.getHealAmount()));
                        System.out.printf("Divine Healing Rate: %d%%%n", healer.getDivineHealChance());
                    }
                    found = true; 
                    break;
                }
            }
    
            if (!found) {
                Game.clearScreen();
                System.out.println("Invalid choice. Please select a valid character.\n");
            }
    
            System.out.println("Would you like to view another character's stats? (y/n)");
            String anotherChoice = input.nextLine().toLowerCase();
            Game.clearScreen();
    
            if (anotherChoice.equals("n")) {
                break; 
            } else if (!anotherChoice.equals("y")) {
                System.out.println("Invalid input. Returning to character selection.\n");
            }
        }
    }
    

    public static void supportedCharacters() {
        System.out.println("The currently supported characters are: \n");
        System.out.println("Assassins: Mortem, Torva");
        System.out.println("Archers: Cito, Sagitta");
        System.out.println("Fighters: Tigris, Ursi");
        System.out.println("Healers: Nutrix, Sanita");
        System.out.println("Wizards: Kanzo, Ulra\n");
    }
}
