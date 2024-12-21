package game;

import java.util.Scanner;

import entities.*;

public class Game {
    // ------------------GAMEPLAY METHODS------------------//
    // Starts the game
    public static void startGame() {
        Scanner input = new Scanner(System.in);
        clearScreen();
        System.out.println("Welcome! Would you like to start a new game? (y/n)");
        while (true) {
            switch (input.nextLine().toLowerCase()) {
                case "y" -> {
                    clearScreen();
                    System.out.println("Team 1, please select your characters.");
                    Team team1 = Team.createTeam(input, 0);
                    clearScreen();
                    System.out.println("Team 2, please select your characters.");
                    Team team2 = Team.createTeam(input, 200);
                    clearScreen();
                    gameCommences(team1, team2);
                    return;
                }
                case "n" -> {
                    System.out.println("Maybe another time!");
                    return;
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }

    // Manages the game loop
    private static void gameCommences(Team team1, Team team2) {
        Scanner input = new Scanner(System.in);
        int turn = 1;

        while (team1.isTeamAlive() && team2.isTeamAlive()) {
            System.out.println("|-------- TURN " + turn + " --------|");

            System.out.println("Team 1 to play.");
            takeTurn(input, team1, team2, 1);
            if (!team2.isTeamAlive()) {
                System.out.println("Team 1 wins!");
                break;
            }
            System.out.println("Team 2 to play.");
            takeTurn(input, team2, team1, -1);
            if (!team1.isTeamAlive()) {
                System.out.println("Team 2 wins!");
            }
            turn++;
            displayGameState(team1, team2);
            while (true) {
                switch (input.nextLine().toLowerCase()) {
                    case "y" -> {
                        clearScreen();
                        break;
                    }
                    case "n" -> {
                        System.out.println("Thanks for playing!");
                        return;
                    }
                    default -> System.out.println("Invalid input. Please try again.");
                }
            }
        }
    }

    // Manages turns in the game
    private static void takeTurn(Scanner input, Team currentTeam, Team opponentTeam, int direction) {
        entities.Character selectedChar = selectCharacter(input, currentTeam);
        performAction(input, selectedChar, opponentTeam, direction);
    }

    private static entities.Character selectCharacter(Scanner input, Team team) {
        System.out.println("Select a character:");
        System.out.println("(1) " + team.getChar1().getClass().getSimpleName());
        System.out.println("(2) " + team.getChar2().getClass().getSimpleName());

        while (true) {
            String choice = input.nextLine();

            switch (choice) {
                case "1" -> {
                    clearScreen();
                    return team.getChar1();
                }
                case "2" -> {
                    clearScreen();
                    return team.getChar2();
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Performs an action based on the user's choice
    private static void performAction(Scanner input, entities.Character character, Team opponentTeam, int direction) {
        while (true) {
            printActionOptions(character, opponentTeam);

            String choice = input.nextLine();
            switch (choice) {
                case "1" -> {
                    entities.Character.moveCharacter(character, direction);
                    return;
                }
                case "2" -> {
                    if (attemptAttack(character, opponentTeam.getChar1())) {
                        return;
                    }
                }
                case "3" -> {
                    if (attemptAttack(character, opponentTeam.getChar2())) {
                        return;
                    }
                }
                case "4" -> {
                    performSpecialAbility(character, opponentTeam.getChar1());
                    return;
                }
                default -> System.out.println("Invalid choice. Please select a valid action.");
            }
        }
    }

    // ------------------UTILITY METHODS------------------//
    // Attempts to attack the target character
    private static boolean attemptAttack(entities.Character attacker, entities.Character target) {
        if (!target.isLiving_status()) {
            clearScreen();
            System.out.println(target.getClass().getSimpleName() + " is already dead!");
            return false;
        }
        if (attacker.getRange() < Math.abs(attacker.getPosition() - target.getPosition())) {
            clearScreen();
            System.out.println(target.getClass().getSimpleName() + " is out of range!");
            return false;
        }
        attacker.attack(target);
        target.takeDamage(attacker.getDamage());
        return true;
    }
    
    // TODO: Properly implement this method
    private static void performSpecialAbility(entities.Character character, entities.Character target) {
        if (character instanceof Assassin) {
            System.out.println("Which of the following special moves would you like to perform?");
        }
    }

    // ------------------HELPERS METHODS------------------//
    // Displays the current state of the game
    private static void displayGameState(Team team1, Team team2) {
        System.out.println("The turn has concluded! The following is the current state of the game:");
        System.out.println("Team 1:");
        System.out.println(team1.getChar1().getClass().getSimpleName() + " - " + team1.getChar1().getHealth() + "HP"
                + " - " + team1.getChar1().getPosition() + "m");
        System.out.println(
                team1.getChar2().getClass().getSimpleName() + " - " + team1.getChar2().getHealth() + "HP" + " - "
                        + team1.getChar2().getPosition() + "m\n");
        System.out.println("Team 2:");
        System.out.println(team2.getChar1().getClass().getSimpleName() + " - " + team2.getChar1().getHealth() + "HP"
                + " - " + team2.getChar1().getPosition() + "m");
        System.out.println(
                team2.getChar2().getClass().getSimpleName() + " - " + team2.getChar2().getHealth() + "HP" + " - "
                        + team2.getChar2().getPosition() + "m\n");
        System.out.println("Would you like to continue? (y/n)");
    }

    // Prints the action options for the character
    private static void printActionOptions(entities.Character character, Team opponentTeam) {
        System.out.println("Choose an action for " + character.getClass().getSimpleName() + ":");
        System.out.println("(1) Move");
        System.out.println("(2) Attack " + opponentTeam.getChar1().getClass().getSimpleName());
        System.out.println("(3) Attack " + opponentTeam.getChar2().getClass().getSimpleName());
        System.out.println("(4) Perform a special ability");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush(); // Just for flushing the output and making it look nicer
    }
}
