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
                    System.out.println(
                            "Team 1, please select your characters. The currently supported characters are: \n");
                    System.out.println("Assassins: Mortem, Trova");
                    System.out.println("Archers: Cito, Sagitta");
                    System.out.println("Fighters: Tigris, Ursi");
                    System.out.println("Healers: Nutrix, Sanita\n");
                    Team team1 = Team.createTeam(input, 0);
                    clearScreen();
                    System.out.println(
                            "Team 2, please select your characters. The currently supported characters are:\n");
                    System.out.println("Assassins: Mortem, Trova");
                    System.out.println("Archers: Cito, Sagitta");
                    System.out.println("Fighters: Tigris, Ursi");
                    System.out.println("Healers: Nutrix, Sanita\n");
                    Team team2 = Team.createTeam(input, 200);
                    selectedMap.applyEffects(new Character[]{
                            team1.getChar1(), team1.getChar2(), team2.getChar1(), team2.getChar2()
                    });
                    clearScreen();
                    gameCommences(team1, team2);
                    return;
                }
                case "n" -> {
                    System.out.println("Maybe another time!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please select a valid input.");
            }
        }
    }
    //Map selection
    private static Map selectMap(Scanner input) {
        System.out.println("Please select the map for the game:");
        System.out.println("(1) Mountain (Decreases each champion's damage by 20)");
        System.out.println("(2) Desert (Halves each champion's movement speed)");
        System.out.println("(3) Garden (Increases each champion's armor by 20)");

        while (true) {
            switch (input.nextLine()) {
                case "1" -> {
                    System.out.println("You have selected the Mountain map!,Now every champion's damage will be decreased by 20.");
                    return new Mountain();
                }
                case "2" -> {
                    System.out.println("You have selected the Desert map!,Now every champion's movement speed will be halved.");
                    return new Desert();
                }
                case "3" -> {
                    System.out.println("You have selected the Garden map!,Now every champion's armor will be increased by 20.");
                    return new Garden();
                }
                default -> System.out.println("Invalid choice. Please select a valid input.");
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
            reduceCooldown(team1.getChar1());
            reduceCooldown(team1.getChar2());
            reduceCooldown(team2.getChar1());
            reduceCooldown(team2.getChar2());

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
                    default -> System.out.println("Invalid choice. Please select a valid input.");
                }
            }
        }
    }

    // Manages turns in the game
    private static void takeTurn(Scanner input, Team currentTeam, Team opponentTeam) {
        entities.Character selectedChar = selectCharacter(input, currentTeam);

        performAction(input, selectedChar, currentTeam, opponentTeam);
    }

    private static entities.Character selectCharacter(Scanner input, Team team) {
        while (true) {
            System.out.println("Select a character:");
            System.out.println("(1) " + team.getChar1().getClass().getSimpleName());
            System.out.println("(2) " + team.getChar2().getClass().getSimpleName());
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
                default -> {
                    clearScreen();
                    System.out.println("Invalid choice. Please select a valid input.\n");
                }
            }
        }
    }

    // Performs an action based on the user's choice
    private static void performAction(Scanner input, entities.Character character,
            Team allyTeam, Team opponentTeam) {
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
                    if (performSpecialAbility(input, character, allyTeam, opponentTeam)) {
                        return;
                    }
                }
                default -> {
                    clearScreen();
                    System.out.println("Invalid choice. Please select a valid input.\n");
                }
            }
        }
    }

    // ------------------UTILITY METHODS------------------//
    // Attempts to attack the target character
    private static boolean attemptAttack(entities.Character attacker, entities.Character target) {
        if (!target.isLiving_status()) {
            clearScreen();
            System.out.println(target.getClass().getSimpleName() + " is already dead!\n");
            return false;
        }
        if (attacker.getRange() < Math.abs(attacker.getPosition() - target.getPosition())) {
            clearScreen();
            System.out.println(target.getClass().getSimpleName() + " is out of range for an attack to be performed!\n");
            return false;
        }
        return true;
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
            System.out.println("Which opponent would you like to attack?");
            System.out.println("(1) " + opponentTeam.getChar1().getClass().getSimpleName());
            System.out.println("(2) " + opponentTeam.getChar2().getClass().getSimpleName());
            System.out.println("(3) Return to Attack menu");

            String choice = input.nextLine();
            clearScreen();
            switch (choice) {
                case "1" -> {
                    if (attemptAttack(assassin, opponentTeam.getChar1())) {
                        double dmg = assassin.sneakAttack(opponentTeam.getChar1());
                        opponentTeam.getChar1().takeDamage(dmg);
                        return true;
                    }
                }
                case "2" -> {
                    if (attemptAttack(assassin, opponentTeam.getChar2())) {
                        double dmg = assassin.sneakAttack(opponentTeam.getChar2());
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
                    clearScreen();
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

    private static void healerHeal(Scanner input, Healer healer, Team allyTeam) {
        while (true) {
            System.out.println("Which character would you like to heal?");
            System.out.println("(1) Yourself");
            entities.Character teamMate = allyTeam.getChar1() != healer ? allyTeam.getChar1() : allyTeam.getChar2();
            System.out.println("(2) " + teamMate.getClass().getSimpleName());
            System.out.println("(3) return to special actions menu");

            String choice = input.nextLine();
            clearScreen();
            switch (choice) {
                case "1" -> {
                    healer.heal(healer);
                    healer.setCoolDown(2);
                    return;
                }
                case "2" -> {
                    healer.heal(teamMate);
                    healer.setCoolDown(2);
                    return;
                }
                case "3" -> {
                    clearScreen();
                    return;
                }
                default -> System.out.println("Invalid choice. Please select a valid action.\n");
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

    // Just for flushing the output on the terminal and making it look nicer
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
