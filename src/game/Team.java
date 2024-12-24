package game;

import java.util.Scanner;

public class Team {
    private entities.Character char1, char2;

    Team(entities.Character char1, entities.Character char2) {
        this.char1 = char1;
        this.char2 = char2;

        char1.setTeam(this);
        char2.setTeam(this);
    }

    static Team createTeam(Scanner input, int position) {

        entities.Character char1 = getCharacter(input, "Enter the first character: ", position);
        entities.Character char2 = getCharacter(input, "Enter the second character: ", position);

        return new Team(char1, char2);
    }

    static entities.Character getCharacter(Scanner scanner, String prompt, int position) {
        System.out.println(prompt);
        while (true) {
            String input = scanner.nextLine();
            try {
                return CharacterFactory.createCharacter(input, position);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid character. Please try again.");
            }
        }
    }

    public entities.Character getChar1() {
        return char1;
    }

    public entities.Character getChar2() {
        return char2;
    }

    public boolean isTeamAlive() {
        return char1.isLiving_status() || char2.isLiving_status();
    }
}
