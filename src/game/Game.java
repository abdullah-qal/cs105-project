package game;

import java.util.Scanner;

import entities.Assassin;

public class Game {

    public static void startGame() {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome! Would you like to start a new game? (y/n)");
        if (input.nextLine().equalsIgnoreCase("y")) {
            System.out.println("Team 1, please select your characters.");
            Team team1 = Team.createTeam(input, 0);
            System.out.println("Team 2, please select your characters.");
            Team team2 = Team.createTeam(input, 1200);
            gameCommences(team1, team2);
        } else {
            System.out.println("Maybe another time!");
        }
    }

    public static void gameCommences(Team team1, Team team2) {
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
        }
    }

    private static void takeTurn(Scanner input, Team currentTeam, Team opponentTeam, int direction) {
        entities.Character selectedChar = selectCharacter(input, currentTeam);
        performAction(input, selectedChar, opponentTeam, direction);
    }

    private static entities.Character selectCharacter(Scanner input, Team team) {
        System.out.println("Select a character:");
        System.out.println("(1) " + team.getChar1().getClass().getSimpleName());
        System.out.println("(2) " + team.getChar2().getClass().getSimpleName());

        int choice = input.nextInt();
        input.nextLine();
        return choice == 1 ? team.getChar1() : team.getChar2();
    }

    private static void performAction(Scanner input, entities.Character character, Team opponentTeam, int direction) {
        boolean validAction = false;

        while (!validAction) {
            System.out.println("Choose an action for " + character.getClass().getSimpleName() + ":");
            System.out.println("(1) Move");
            System.out.println("(2) Attack " + opponentTeam.getChar1().getClass().getSimpleName());
            System.out.println("(3) Attack " + opponentTeam.getChar2().getClass().getSimpleName());
            System.out.println("(4) Perform a special ability");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1 -> {
                    entities.Character.moveCharacter(character, direction);
                    validAction = true;
                }
                case 2 -> validAction = attemptAttack(character, opponentTeam.getChar1(), input);
                case 3 -> validAction = attemptAttack(character, opponentTeam.getChar2(), input);
                case 4 -> {
                    performSpecialAbility(character, opponentTeam.getChar1());
                    validAction = true;
                }
                default -> System.out.println("Invalid action. Try again.");
            }
        }
    }

    private static boolean attemptAttack(entities.Character attacker, entities.Character target, Scanner input) {
        if (!target.isLiving_status()) {
            System.out.println(target.getClass().getSimpleName() + " is already dead!");
            return false;
        }

        if (Math.abs(attacker.getPosition() - target.getPosition()) < attacker.getRange()) {
            double dmg = attacker.attack(target);
            target.takeDamage((int) dmg);
            if (!target.isLiving_status()) {
                System.out.println(target.getClass().getSimpleName() + " has been defeated!");
            }
            return true;
        }
        System.out.println("Target is out of range! Move closer to attack.");
        return false;
    }

    // TODO: Properly implement this method
    private static void performSpecialAbility(entities.Character character, entities.Character target) {
        if (character instanceof Assassin) {
            System.out.println("Which of the following special moves would you like to perform?");
        }
    }
}
