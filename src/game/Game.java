package game;

import java.util.Scanner;

import entities.Archers.Archer;
import entities.Assassins.Assassin;
import entities.Fighters.Fighter;
import entities.Healers.Healer;

public class Game {
    final static String RESET = "\033[0m";
    final static String RED = "\033[31m";
    final static String GREEN = "\033[32m"; // ANSI escape codes for colouring purposes
    // ------------------GAMEPLAY METHODS------------------//
    // Starts the game

    public static void startGame() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome! Would you like to start a new game? (y/n)");
            System.out.println("If you want to see the supported characters with their stats and abilities, type 'I'");
            switch (input.nextLine()) {
                case "y" -> {
                    clearScreen();

                    // Map Selection
                    Map selectedMap = selectMap(input);

                    // Selecting weather randomly
                    Weather currentWeather = Weather.getRandomWeather();
                    System.out.println("Selected Map: " + selectedMap.getName());
                    System.out.println("Weather Condition: " + currentWeather.getName() + " - " + currentWeather.getDescription());
                    System.out.println("Team 1, please select your characters.\n");
                    System.out.println("The currently supported characters are: \n");
                    System.out.println("Assassins: Mortem, Torva");
                    System.out.println("Archers: Cito, Sagitta");
                    System.out.println("Fighters: Tigris, Ursi");
                    System.out.println("Healers: Nutrix, Sanita\n");

                    Team team1 = Team.createTeam(input, 0);
                    clearScreen();
                    System.out.println("Team 2, please select your characters.\n");

                    System.out.println("The currently supported characters are: \n");
                    System.out.println("Assassins: Mortem, Torva");
                    System.out.println("Archers: Cito, Sagitta");
                    System.out.println("Fighters: Tigris, Ursi");
                    System.out.println("Healers: Nutrix, Sanita\n");

                    Team team2 = Team.createTeam(input, 200);
                    clearScreen();
                    // Implementing weather and map effects.
                    selectedMap.applyEffects(team1.getCharacters());
                    selectedMap.applyEffects(team2.getCharacters());
                    currentWeather.applyEffects(team1.getCharacters());
                    currentWeather.applyEffects(team2.getCharacters());
                    gameCommences(team1, team2);
                    return;
                }
                case "n" -> {
                    clearScreen();
                    System.out.println("Maybe another time!");
                    return;
                }
                case "I" -> {
                    clearScreen();
                    entities.CharacterInfo.getInfo(input);
                }
                default -> {
                    clearScreen();
                    System.out.println("Invalid choice. Please select a valid input.\n");
                }
            }
        }
    }
    // Map selection
    private static Map selectMap(Scanner input) {
        System.out.println("Select a map:");
        System.out.println("(1) Desert");
        System.out.println("(2) Garden");
        System.out.println("(3) Mountain");

        while (true) {
            switch (input.nextLine()) {
                case "1" -> {
                    return new Desert();
                }
                case "2" -> {
                    return new Garden();
                }
                case "3" -> {
                    return new Mountain();
                }
                default -> System.out.println("Invalid choice. Please select a valid map.");
            }
        }
    }

    // Manages the game loop
    private static void gameCommences(Team team1, Team team2) {
        Scanner input = new Scanner(System.in);
        int turn = 1;
    
        while (true) {
            System.out.println("|-------- TURN " + turn + " --------|");
    
            // Team 1 plays
            if (!takeTurn(input, team1, team2, "Team 1")) {
                System.out.println("Team 1 wins! The game lasted for " + turn + " turns.");
                break;
            }
    
            // Team 2 plays
            if (!takeTurn(input, team2, team1, "Team 2")) {
                System.out.println("Team 2 wins! The game lasted for " + turn + " turns.");
                break;
            }
    
            reduceCooldowns(team1, team2);
    
            displayGameState(team1, team2);
            if (!promptToContinue(input)) {
                System.out.println("Thanks for playing!");
                break;
            }
    
            turn++;
        }
    }
    
    private static boolean takeTurn(Scanner input, Team currentTeam, Team opponentTeam, String teamName) {
        System.out.println(teamName + " to play.");
        while (true) {

            System.out.println("Select a character:");
            System.out.printf("(1) %s (%s) - %.2f/%.2f HP%n",
                    currentTeam.getChar1().getClass().getSimpleName(),
                    currentTeam.getChar1().getClass().getSuperclass().getSimpleName(),
                    entities.Character.normalisedValue(currentTeam.getChar1().getHealth()),
                    entities.Character.normalisedValue(currentTeam.getChar1().getMaxHealth()));
            System.out.printf("(2) %s (%s) - %.2f/%.2f HP%n",
                    currentTeam.getChar2().getClass().getSimpleName(),
                    currentTeam.getChar2().getClass().getSuperclass().getSimpleName(),
                    entities.Character.normalisedValue(currentTeam.getChar2().getHealth()),
                    entities.Character.normalisedValue(currentTeam.getChar2().getMaxHealth()));
            
            String choice = input.nextLine();
            clearScreen();
    
            entities.Character selectedChar = switch (choice) {
                case "1" -> currentTeam.getChar1();
                case "2" -> currentTeam.getChar2();
                default -> null;
            };
    
            if (selectedChar == null) {
                System.out.println("Invalid choice. Please select a valid input.\n");
                continue;
            }
    
            if (!selectedChar.isLiving_status()) {
                System.out.println(selectedChar.getClass().getSimpleName() + " is already dead! Choose a different character\n");
                continue;
            }
    
            if (performAction(input, selectedChar, currentTeam, opponentTeam)) {
                break;
            }
        }
        return opponentTeam.isTeamAlive();
    }

    // Performs an action based on the user's choice
    private static boolean performAction(Scanner input, entities.Character character,
            Team allyTeam, Team opponentTeam) {
        while (true) {
            System.out.printf("Choose an action for %s:%n", character.getClass().getSimpleName());
            System.out.printf("(1) Move to the right (Current position: %d m)%n", character.getPosition());
            System.out.printf("(2) Move to the left (Current position: %d m)%n", character.getPosition());
            System.out.printf("(3) Attack %s%n", opponentTeam.getChar1().getClass().getSimpleName());
            System.out.printf("(4) Attack %s%n", opponentTeam.getChar2().getClass().getSimpleName());
            System.out.printf("(5) Perform a special ability%n");
            System.out.printf("(6) Retrieve current stats about a character%n");
            System.out.printf("(7) Return to character selection menu%n");
            

            String choice = input.nextLine();
            switch (choice) {
                case "1" -> {
                    if (entities.Character.moveCharacter(input, character, 1)) {
                        return true;
                    }
                }
                case "2" -> {
                    if (entities.Character.moveCharacter(input, character, -1)) {
                        return true;
                    }
                }
                case "3" -> {
                    if (entities.Character.attemptAttack(character, opponentTeam.getChar1())) {
                        int dmg = character.attack(opponentTeam.getChar1());
                        opponentTeam.getChar1().takeDamage(dmg);
                        return true;
                    }
                }
                case "4" -> {
                    if (entities.Character.attemptAttack(character, opponentTeam.getChar2())) {
                        int dmg = character.attack(opponentTeam.getChar2());
                        opponentTeam.getChar2().takeDamage(dmg);
                        return true;
                    }
                }
                case "5" -> {
                    if (performSpecialAbility(input, character, allyTeam, opponentTeam)) {
                        return true;
                    }
                }
                case "6" -> {
                    clearScreen();
                    if (retrieveInfo(input, character, allyTeam, opponentTeam)) {
                    }
                }
                case "7" -> {
                    clearScreen();
                    return false;
                }
                default -> {
                    clearScreen();
                    System.out.println("Invalid choice. Please select a valid input.\n");
                }
            }
        }
    }

    // ------------------UTILITY METHODS------------------//
    private static boolean retrieveInfo(Scanner input, entities.Character character, Team allyTeam, Team opponentTeam) {
        while (true) {
            entities.Character teamMate = allyTeam.getChar1() != character ? allyTeam.getChar1() : allyTeam.getChar2();

            System.out.printf("Which character's stats would you like to view?%n");
            System.out.printf("(1) Myself%n");
            System.out.printf("(2) My team's %s%n", teamMate.getClass().getSimpleName());
            System.out.printf("(3) %s%n", opponentTeam.getChar1().getClass().getSimpleName());
            System.out.printf("(4) %s%n", opponentTeam.getChar2().getClass().getSimpleName());
            System.out.printf("(5) Return to action menu%n");
            

            String choice = input.nextLine();
            clearScreen();
            switch (choice) {
                case "1" -> {
                    displayCharacterStats(input, character);
                    return true;
                }
                case "2" -> {
                    displayCharacterStats(input, teamMate);
                    return true;
                }
                case "3" -> {
                    displayCharacterStats(input, opponentTeam.getChar1());
                    return true;
                }
                case "4" -> {
                    displayCharacterStats(input, opponentTeam.getChar2());
                    return true;
                }
                case "5" -> {
                    return false;
                }
                default -> {
                    System.out.println("Invalid choice. Please select a valid input.\n");
                }
            }
        }
    }

    private static boolean performSpecialAbility(Scanner input, entities.Character character, Team allyTeam,
            Team opponentTeam) {
        clearScreen();

        if (character instanceof Assassin assassin) {
            return assassinSpecialAbility(input, assassin, opponentTeam);
        } else if (character instanceof Archer archer) {
            return archerSpecialAbility(input, archer);
        } else if (character instanceof Fighter fighter) {
            return fighterSpecialAbility(input, fighter);
        } else if (character instanceof Healer healer) {
            return healerSpecialAbility(input, healer, allyTeam);
        }

        return true;
    }

    // Deals with assassins' special ability(ies)
    private static boolean assassinSpecialAbility(Scanner input, Assassin assassin, Team opponentTeam) {
        while (true) {
            System.out.println("Which of the following special moves would you like to perform?");
            System.out.println("(1) Sneak Attack");
            System.out.println("(2) Go into stealth mode");
            System.out.println("(3) Return to Action menu");

            String choice = input.nextLine();
            clearScreen();
            switch (choice) {
                case "1" -> {
                    if (assassin.isStealthActive()) {
                        if (assassinSneakAttack(input, assassin, opponentTeam)) {
                            return true;
                        }
                    } else {
                        System.out.println("You are not currently in stealth mode!\n");
                    }
                }
                case "2" -> {
                    if (assassin.activateStealth() && assassin.getCoolDown() == 0) {
                        assassin.setCoolDown(3);
                        return true;
                    }
                }
                case "3" -> {
                    return false;
                }
                default -> {
                    System.out.println("Invalid choice. Please select a valid input.\n");
                }
            }
        }
    }

    private static boolean assassinSneakAttack(Scanner input, Assassin assassin, Team opponentTeam) {
        clearScreen();
        while (true) {
            System.out.printf("Which opponent would you like to attack?%n");
            System.out.printf("(1) %s%n", opponentTeam.getChar1().getClass().getSimpleName());
            System.out.printf("(2) %s%n", opponentTeam.getChar2().getClass().getSimpleName());
            System.out.printf("(3) Return to Attack menu%n");
            

            String choice = input.nextLine();
            clearScreen();
            switch (choice) {
                case "1" -> {
                    if (entities.Character.attemptAttack(assassin, opponentTeam.getChar1())) {
                        int dmg = assassin.sneakAttack(opponentTeam.getChar1());
                        opponentTeam.getChar1().takeDamage(dmg);
                        return true;
                    }
                }
                case "2" -> {
                    if (entities.Character.attemptAttack(assassin, opponentTeam.getChar2())) {
                        int dmg = assassin.sneakAttack(opponentTeam.getChar2());
                        opponentTeam.getChar2().takeDamage(dmg);
                        return true;
                    }
                }
                case "3" -> {
                    return false;
                }
                default -> System.out.println("Invalid choice. Please select a valid input.\n");
            }
        }
    }

    // Deals with archers' special ability(ies)
    private static boolean archerSpecialAbility(Scanner input, Archer archer) {
        while (true) {
            System.out.println("Which of the following special moves would you like to perform?");
            System.out.println("(1) Activate Great Sight");
            System.out.println("(2) Return to Action menu");

            String choice = input.nextLine();
            clearScreen();
            switch (choice) {
                case "1" -> {
                    if (archer.getCoolDown() == 0) {
                        archer.activateVision();
                        archer.setCoolDown(3);
                        return true;
                    }
                }
                case "2" -> {
                    return false;
                }
                default -> System.out.println("Invalid choice. Please select a valid input.\n");
            }
        }
    }

    private static boolean fighterSpecialAbility(Scanner input, Fighter fighter) {
        while (true) {
            System.out.println("Which of the following special moves would you like to perform?");
            System.out.println("(1) Activate Berserker Anger");
            System.out.println("(2) Return to Action menu");

            String choice = input.nextLine();
            clearScreen();
            switch (choice) {
                case "1" -> {
                    if (fighter.getCoolDown() == 0) {
                        fighter.activateAnger();
                        fighter.setCoolDown(3);
                        return true;
                    }
                }
                case "2" -> {
                    return false;
                }
                default -> System.out.println("Invalid choice. Please select a valid input.\n");
            }
        }
    }

    // Deals with the healers' special ability(ies)
    private static boolean healerSpecialAbility(Scanner input, Healer healer, Team allyTeam) {
        while (true) {
            System.out.println("Which of the following special moves would you like to perform?");
            System.out.println("(1) Heal");
            System.out.println("(2) Return to action menu");

            String choice = input.nextLine();
            clearScreen();

            switch (choice) {
                case "1" -> {
                    if (healer.getCoolDown() == 0) {
                        healerHeal(input, healer, allyTeam);
                        return true;
                    }
                    System.out.println("You can't heal this turn!");
                    return false;
                }
                case "2" -> {
                    return false;
                }
                default -> System.out.println("Invalid choice. Please select a valid input.\n");
            }
        }
    }

    private static boolean healerHeal(Scanner input, Healer healer, Team allyTeam) {
        entities.Character teamMate = allyTeam.getChar1() != healer ? allyTeam.getChar1() : allyTeam.getChar2();

        while (true) {
            System.out.printf("Which character would you like to heal?%n");
            System.out.printf("(1) Yourself%n");
            System.out.printf("(2) %s%n", teamMate.getClass().getSimpleName());
            System.out.printf("(3) Return to special actions menu%n");
            
            String choice = input.nextLine();
            clearScreen();
            switch (choice) {
                case "1" -> {
                    if (healer.heal(healer)) {
                        healer.setCoolDown(2);
                        return true;
                    }
                }
                case "2" -> {
                    if (healer.heal(teamMate)) {
                        healer.setCoolDown(2);
                        return true;
                    }
                }
                case "3" -> {
                    clearScreen();
                    return false;
                }
                default -> System.out.println("Invalid choice. Please select a valid action.\n");
            }
        }
    }

    // ------------------HELPER METHODS------------------//
    // Displays the current state of the game
    private static void displayGameState(Team team1, Team team2) {
        System.out.printf("The turn has concluded! The following is the current state of the game:%n%n");

        System.out.printf("%sTeam 1%s:%n", RED, RESET);
        System.out.printf("%s%c%s%s - %s/%s HP - %d m%n", RED, 
            team1.getChar1().getClass().getSimpleName().charAt(0), RESET, 
            team1.getChar1().getClass().getSimpleName().substring(1),
            entities.Character.normalisedValue(team1.getChar1().getHealth()), 
            entities.Character.normalisedValue(team1.getChar1().getMaxHealth()), 
            team1.getChar1().getPosition());
        System.out.printf("%s%c%s%s - %.2f/%.2f HP - %d m%n%n", RED, 
            team1.getChar2().getClass().getSimpleName().charAt(0), RESET,
            team1.getChar2().getClass().getSimpleName().substring(1),
            entities.Character.normalisedValue(team1.getChar2().getHealth()),
            entities.Character.normalisedValue(team1.getChar2().getMaxHealth()),
            team1.getChar2().getPosition());
        
        System.out.printf("%sTeam 2%s:%n", GREEN, RESET);
        System.out.printf("%s%c%s%s - %.2f/%.2f HP - %d m%n", GREEN, 
            team2.getChar1().getClass().getSimpleName().charAt(0), RESET, 
            team2.getChar1().getClass().getSimpleName().substring(1),
            entities.Character.normalisedValue(team2.getChar1().getHealth()), 
            entities.Character.normalisedValue(team2.getChar1().getMaxHealth()), 
            team2.getChar1().getPosition());
        System.out.printf("%s%c%s%s - %.2f/%.2f HP - %d m%n%n", GREEN, 
            team2.getChar2().getClass().getSimpleName().charAt(0), RESET, 
            team2.getChar2().getClass().getSimpleName().substring(1),
            entities.Character.normalisedValue(team2.getChar2().getHealth()),
            entities.Character.normalisedValue(team2.getChar2().getMaxHealth()),
            team2.getChar2().getPosition());
        
        AsciiArt.makeArt(team1, team2);
    }

    // Displays the current stats of a character
    private static void displayCharacterStats(Scanner input, entities.Character character) {
        System.out.printf("Current stats of %s:%n", character.getClass().getSimpleName());
        System.out.printf("Current Position: %d m%n", character.getPosition());
        System.out.printf("Health: %.2f/%.2f HP%n", 
                          entities.Character.normalisedValue(character.getHealth()), 
                          entities.Character.normalisedValue(character.getMaxHealth()));
        System.out.printf("Damage: %s DMG%n", 
                          entities.Character.normalisedValue(character.getDamage()));
        System.out.printf("Defense: %.2f DEF%n", character.getDefense());
        System.out.printf("Movement Speed: %d m/turn%n", character.getMovementSpeed());
        System.out.printf("Range: %d m%n", character.getRange());
        System.out.printf("Crit Rate: %.2f%%%n", character.getCritRate());
        System.out.printf("Crit Damage: %.2f%%%n", character.getCritDmg());
        System.out.printf("Living Status: %s%n", character.isLiving_status() ? "Alive" : "Dead");
        System.out.printf("Cooldown: %d turns%n%n", character.getCoolDown());
    }

    // Reduces the cooldown of the character if they recently activated a special
    // ability
    private static void reduceCooldown(entities.Character character) {
        if (character.getCoolDown() > 0) {
            character.setCoolDown(character.getCoolDown() - 1);
            if (character instanceof Archer archer && archer.getCoolDown() == 1) {
                if (archer.isVisionActive()) {
                    archer.deactivateVision();
                }
            } else if (character instanceof Fighter fighter && fighter.getCoolDown() == 1) {
                if (fighter.isAngerActive()) {
                    fighter.deactivateAnger();
                }
            } else if (character instanceof Assassin assassin && assassin.getCoolDown() == 1) {
                if (assassin.isStealthActive()) {
                    assassin.deactivateStealth();
                }
            }
        }
    }
    // prompts if you want to continue
    private static boolean promptToContinue(Scanner input) {
        while (true) {
            System.out.println("Do you want to continue? (y/n)");
            String choice = input.nextLine().toLowerCase();
            switch (choice) {
                case "y":
                    clearScreen();
                    return true;
                case "n":
                    clearScreen();
                    return false;
                default:
                    System.out.println("Invalid choice. Please select a valid input.");
            }
        }
    }
    // For maanging cooldowns
    private static void reduceCooldowns(Team team1, Team team2) {
        reduceCooldown(team1.getChar1());
        reduceCooldown(team1.getChar2());
        reduceCooldown(team2.getChar1());
        reduceCooldown(team2.getChar2());
    }

    // Just for flushing the output on the terminal and making it look nicer
    public static void clearScreen() {
        System.out.print("\033[H\033[2J\033[3J");
        System.out.flush();
    }
}
