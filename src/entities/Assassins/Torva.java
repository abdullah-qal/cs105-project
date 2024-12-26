package entities.Assassins;

public class Torva extends Assassin {
    public Torva(int position) {
        super(position, 65, 250, 250, 35, 45, 10, 15, 45);
    }

    @Override
    public String toString() {
        return "Deadly and elusive, Torva is the assassin who never misses her mark.%n" +
                "Her sneak attacks are the stuff of legend, and her foes tremble in fear before they see her coming.%n%n";

    }
}
