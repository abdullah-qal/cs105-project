package game;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AsciiArt {
    // ANSI escape codes for coloring
    final static String RESET = "\033[0m";
    final static String RED = "\033[31m";
    final static String GREEN = "\033[32m";

    final static int SIZE = 200; // Size of the battlefield

    public static void makeArt(Team team1, Team team2) {

        entities.Character[] chars = { team1.getChar1(), team1.getChar2(), team2.getChar1(), team2.getChar2() };
        Map<entities.Character, Integer> charMap = new HashMap<>();
        // Maps characters to positions
        for (entities.Character c : chars) {
            charMap.put(c, c.getPosition());
        }

        // This basically inverts the map
        Map<Integer, List<entities.Character>> repeatedPosition = findDuplicatePositions(charMap);

        int DOTS = SIZE / 5;
        int maxSize = 0;

        // Find the biggest sized position list. Basically, which position has the most
        //
        //
        //
        // characters
        for (Map.Entry<Integer, List<entities.Character>> entry : repeatedPosition.entrySet()) {
            int currentSize = entry.getValue().size();
            if (currentSize > maxSize) {
                maxSize = currentSize;
            }
            //
        }
        //

        //
        // This prints the positions of all the characters, if they're on the same spot,
        // they get stacked
        for (int j = maxSize - 1; j >= 0; --j) {
            System.out.print("|");
            for (int i = 0; i <= DOTS; ++i) {
                if (charMap.containsValue(i * 5)) {
                    try {
                        entities.Character currentCharacter = repeatedPosition.get(i * 5).get(j);

                        if (currentCharacter.isLiving_status()) {
                            if (currentCharacter.getTeam().equals(team1)) {
                                System.out.print(RED + currentCharacter.getClass().getSimpleName().charAt(0) + RESET);
                            } else {
                                System.out.print(GREEN + currentCharacter.getClass().getSimpleName().charAt(0) + RESET);
                            }
                        } else {
                            System.out.print(" ");
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.print(" ");
                    }
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print("|\n");
        }

        // This prints @'s where characters are
        System.out.print("|");
        for (int i = 0; i <= DOTS; ++i) {
            if (charMap.containsValue(i * 5)) {
                for (entities.Character aliveChars : repeatedPosition.get(i * 5)) {
                    if (aliveChars.isLiving_status()) {
                        System.out.print("@");
                        break;
                    } else {
                        Random random = new Random();
                        int randomValue = random.nextInt(10);
                        if (randomValue == 6) {
                            System.out.print(".");
                        } else {
                            System.out.print(" ");
                        }
                    }
                }
            } else {
                Random random = new Random();
                int randomValue = random.nextInt(10);
                if (randomValue == 6) {
                    System.out.print(".");
                } else {
                    System.out.print(" ");
                }
            }
        }
        System.out.print("|\n");

        // Prints a bunch of +'s'
        System.out.print("+");
        for (int i = 0; i <= DOTS; ++i) {
            System.out.print("-");
        }
        System.out.print("+\n");
    }

    // Method to invert the hashmap
    private static Map<Integer, List<entities.Character>> findDuplicatePositions(
            Map<entities.Character, Integer> charMap) {
        Map<Integer, List<entities.Character>> repeatedPosition = new HashMap<>();

        for (Map.Entry<entities.Character, Integer> entry : charMap.entrySet()) {
            Integer position = entry.getValue();
            entities.Character character = entry.getKey();

            repeatedPosition.computeIfAbsent(position, k -> new ArrayList<>()).add(character);
        }
        return repeatedPosition;
    }
}
