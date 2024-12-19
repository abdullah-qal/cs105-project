package game;

// This class manages the overall gameplay, including team setup, battle progresion, and result determination.

import java.util.Scanner;

public class Game {
    public static void startGame() {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome! Would you like to start a new game? (y/n)");
        if (input.nextLine().equalsIgnoreCase("y")) {
            System.out.println("Team 1, please input your characters:");
            Team team1 = Team.createTeam(input, 0);
            System.out.println("Team 2, please input your characters:");
            Team team2 = Team.createTeam(input, 1200);
            return;
        }
        System.out.println("Maybe another time!");
    }
    public static void gameCommences(Team team1, Team team2) {
        
    }
}
