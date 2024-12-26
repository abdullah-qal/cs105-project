package entities.Assassins;

public class Mortem extends Assassin {
    public Mortem(int position) {
        super(position, 85, 200, 200, 20, 45, 10, 5, 60);
    }

    @Override
    public String toString() {
        return "A shadow on the battlefield, Mortem strikes swiftly and silently, leaving enemies no chance to react.%n"
                + "With unmatched agility and lethal precision, he dominates the night.%n%n";
    }
}
