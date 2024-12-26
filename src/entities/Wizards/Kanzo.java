package entities.Wizards;

public class Kanzo extends Wizard {
    public Kanzo(int position) {
        super(position, 40, 350, 350, 30, 20, 15, 10, 50, WizardType.KANZO);
    }

    @Override
    public String toString() {
        return "  A wizard with the ultimate sacrificial ability, Kanzo unleashes devastating power%n" +
                "at the cost of his own life, halving the health of all enemies.%n%n";
    }
}