package game;

import java.util.Arrays;
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
                    System.out.println("Team 1, please select your characters. The currently supported characters are: " + Arrays.toString(entities.Character.SUPPORTED_CHARACTERS));
                    Team team1 = Team.createTeam(input, 0);
                    clearScreen();
                    System.out.println("Team 2, please select your characters. The currently supported characters are:" + Arrays.toString(entities.Character.SUPPORTED_CHARACTERS));
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
            takeTurn(input, team1, team2);
            if (!team2.isTeamAlive()) {
                System.out.println("Team 1 wins!");
                break;
            }
            System.out.println("Team 2 to play.");
            takeTurn(input, team2, team1);
            if (!team1.isTeamAlive()) {
                System.out.println("Team 2 wins!");
            }
            reduceHealCooldown(team1.getChar1());
            reduceHealCooldown(team1.getChar2());
            reduceHealCooldown(team2.getChar1());
            reduceHealCooldown(team2.getChar2());

            turn++;
            displayGameState(team1, team2);
            outerLoop: while (true) {
                switch (input.nextLine().toLowerCase()) {
                    case "y" -> {
                        clearScreen();
                        break outerLoop;
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
    private static void takeTurn(Scanner input, Team currentTeam, Team opponentTeam) {
        entities.Character selectedChar = selectCharacter(input, currentTeam);
        entities.Character teamMate = (selectedChar == currentTeam.getChar1()) ? currentTeam.getChar2()
                : currentTeam.getChar1();
        performAction(input, teamMate, selectedChar, opponentTeam);
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
    private static void performAction(Scanner input, entities.Character teamMate, entities.Character character,
            Team opponentTeam) {
        while (true) {
            printActionOptions(character, opponentTeam);

            String choice = input.nextLine();
            switch (choice) {
                case "1" -> {
                    if (entities.Character.moveCharacter(character, 1)) {
                        return;
                    }
                }
                case "2" -> {
                    if (entities.Character.moveCharacter(character, -1)) {
                        return;
                    }
                }
                case "3" -> {
                    if (attemptAttack(character, opponentTeam.getChar1())) {
                        double dmg = character.attack(opponentTeam.getChar1());
                        opponentTeam.getChar1().takeDamage(dmg);
                        return;
                    }
                }
                case "4" -> {
                    if (attemptAttack(character, opponentTeam.getChar2())) {
                        double dmg = character.attack(opponentTeam.getChar2());
                        opponentTeam.getChar2().takeDamage(dmg);
                        return;
                    }
                }
                case "5" -> {
                    if (performSpecialAbility(character, teamMate, opponentTeam.getChar1(), input)) {
                        return;
                    }
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
        return true;
    }

    private static boolean performSpecialAbility(entities.Character character, entities.Character teamMate,
            entities.Character target, Scanner input) {
        clearScreen();

        if (character instanceof Assassin assassin) {
            return assassinSpecialAbility(assassin, target, input);
        } else if (character instanceof Healer healer) {
            return healerSpecialAbility(healer, teamMate, input);
        }

        return true;
    }

    // Deals with assassins' special ability(ies)
    private static boolean assassinSpecialAbility(Assassin assassin, entities.Character target, Scanner input) {
        while (true) {
            System.out.println("Which of the following special moves would you like to perform?");
            System.out.println("(1) Sneak Attack");
            System.out.println("(2) Go into stealth mode");
            System.out.println("(3) Return to Action menu");

            String choice = input.nextLine();
            clearScreen();
            switch (choice) {
                case "1" -> {
                    if (assassin.isStealth()) {
                        if (attemptAttack(assassin, target)) {
                            assassin.sneakAttack(target);
                            return true;
                        }
                    } else {
                        System.out.println("You are not currently in stealth mode!");
                    }
                }
                case "2" -> {
                    assassin.setStealth(true);
                    System.out.println("You are now in stealth mode!");
                    return true;
                }
                case "3" -> {
                    return false;
                }
                default -> System.out.println("Invalid choice. Please select a valid action.");
            }
        }
    }

    // Deals with the healers' special ability(ies)
    private static boolean healerSpecialAbility(Healer healer, entities.Character teamMate, Scanner input) {
        while (true) {
            System.out.println("Would you like to heal your teammate?");
            System.out.println("(1) Yes");
            System.out.println("(2) No");

            String choice = input.nextLine();
            clearScreen();
            switch (choice) {
                case "1" -> {
                    if (healer.getHealCooldown() == 0) {
                        healer.heal(teamMate);
                        healer.setHealCooldown(2);
                        return true;
                    }
                    System.out.println("You can't heal this turn!");
                    return false;
                }
                case "2" -> {
                    return false;
                }
                default -> System.out.println("Invalid choice. Please select a valid action.");
            }
        }
    }

    // ------------------HELPER METHODS------------------//
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
        System.out.println("(1) Move to the right (Current position: " + character.getPosition() + "m)");
        System.out.println("(2) Move to the left (Current position: " + character.getPosition() + "m)");
        System.out.println("(3) Attack " + opponentTeam.getChar1().getClass().getSimpleName());
        System.out.println("(4) Attack " + opponentTeam.getChar2().getClass().getSimpleName());
        System.out.println("(5) Perform a special ability");
    }

    // Checks if the character is a healer and reduces the heal cooldown if they
    // healed recently
    private static void reduceHealCooldown(entities.Character character) {
        if (character instanceof Healer healer && healer.getHealCooldown() > 0) {
            healer.setHealCooldown(healer.getHealCooldown() - 1);
        }
    }

    // Just for flushing the output on the terminal and making it look nicer
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
