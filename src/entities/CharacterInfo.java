package entities;

import java.util.Scanner;

public class CharacterInfo {

    public static void getInfo(Scanner input) {

        while (true) {
            System.out.println("The currently supported characters are: \n");
            System.out.println("Assassins: Mortem, Torva");
            System.out.println("Archers: Cito, Sagitta");
            System.out.println("Fighters: Tigris, Ursi");
            System.out.println("Healers: Nutrix, Sanita\n");

            System.out.println("Which character would you like to learn more about? (Type q to return)");
            String choice = input.nextLine().toLowerCase();

            if (choice.equals("q")) {
                clearScreen();
                return;
            }

            clearScreen();
            // TODO: make this more maintainable by defining constants for these values in
            // the respective Character classes
            switch (choice) {
                case "mortem":
                    System.out.println(
                            """
                            A shadow on the battlefield, Mortem strikes swiftly and silently, leaving enemies no chance to react.
                            With unmatched agility and lethal precision, he dominates the night.

                            Damage: 85 DMG
                            Max health: 200 HP
                            Defense: 20 DEF
                            Movement Speed: 45 m/turn
                            Range: 10 m
                            Crit Rate: 5%
                            Crit Damage: 60%
                            Special Abilities: Sneak Attack, Stealth Mode
                                        """);
                    break;

                case "torva":
                    System.out.println(
                            """
                            Deadly and elusive, Torva is the assassin who never misses her mark.
                            Her sneak attacks are the stuff of legend, and her foes tremble in fear before they see her coming.

                            Damage: 65 DMG
                            Max health: 250 HP
                            Defense: 35 DEF
                            Movement Speed: 45 m/turn
                            Range: 10 m
                            Crit Rate: 15%
                            Crit Damage: 45%
                            Special Abilities: Sneak Attack, Stealth Mode
                                    """);
                    break;

                case "cito":
                    System.out.println(
                            """
                            Cito, the eagle-eyed archer, reigns supreme from a distance. His arrows fly true, striking
                            with uncanny accuracy and leaving his enemies in awe.

                            Damage: 60 DMG
                            Max health: 150 HP
                            Defense: 15 DEF
                            Movement Speed: 35 m/turn
                            Range: 50 m
                            Crit Rate: 25%
                            Crit Damage: 50%
                            Special Abilities: Great Sight
                                                                """);
                    break;

                case "sagitta":
                    System.out.println("""
                            Sagitta is a sharpshooter of unparalleled skill. With vision as sharp as her aim, she
                            dominates the battlefield from incredible distances, where no enemy is safe.

                            Damage: 45 DMG
                            Max health: 150 HP
                            Defense: 20 DEF
                            Movement Speed: 25 m/turn
                            Range: 75 m
                            Crit Rate: 35%
                            Crit Damage: 40%
                            Special Abilities: Great Sight
                            """);
                    break;

                case "tigris":
                    System.out.println("""
                            A juggernaut of strength and resilience, Tigris charges into battle with relentless fury. His sheer
                            power and endurance make him a force to be reckoned with

                            Damage: 35 DMG
                            Max health: 350 HP
                            Defense: 40 DEF
                            Movement Speed: 35 m/turn
                            Range: 15 m
                            Crit Rate: 10%
                            Crit Damage: 65%
                            Special Abilities: Berserker Anger
                            """);
                    break;

                case "ursi":
                    System.out.println("""
                            Ursi, the indomitable warrior, is a bastion of brute strength. With unyielding force and an 
                            iron will, he crushes his foes in relentless combat.

                            Damage: 20 DMG
                            Max health: 450 HP
                            Defense: 50 DEF
                            Movement Speed: 30 m/turn
                            Range: 15 m
                            Crit Rate: 15%
                            Crit Damage: 50%
                            Special Abilities: Berserker Anger
                            """);
                    break;

                case "nutrix":
                    System.out.println("""
                           The gentle healer, Nutrix, is a beacon of hope on the battlefield. Her divine touch
                            restores life and bolsters her allies when they need it most.

                            Damage: 15 DMG
                            Max Health: 275 HP
                            Defense: 20 DEF
                            Movement Speed: 15 m/turn
                            Range: 60 m
                            Crit Rate: 0%
                            Crit Damage: 0%

                            Healing Amount: 35 HP
                            Divine Heal Rate: 20%
                            Special Abilities: Heal
                            """);
                    break;

                case "sanita":
                    System.out.println("""
                            Sanita wields the power of healing with unmatched expertise. Her restorative 
                            abilities turn the tide of battle, making her an indispensable ally.

                            Damage: 15 DMG
                            Max Health: 200 HP
                            Defense: 20 DEF
                            Movement Speed: 15 m/turn
                            Range: 60 m
                            Crit Rate: 0%
                            Crit Damage: 0%

                            Healing Amount: 50 HP
                            Divine Heal Rate: 5%
                            Special Abilities: Heal
                            """);
                    break;

                default:
                    clearScreen();
                    System.out.println("Invalid choice. Please select a valid character.\n");
                    continue;
            }

            System.out.println("Would you like to view another character's stats? (y/n)");
            String anotherChoice = input.nextLine().toLowerCase();
            clearScreen();

            if (anotherChoice.equals("n")) {
                return;
            } else if (!anotherChoice.equals("y")) {
                System.out.println("Invalid input. Returning to character selection.\n");
            }
        }
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J\033[3J");
        System.out.flush();
    }
}
