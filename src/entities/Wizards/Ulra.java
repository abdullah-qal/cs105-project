package entities.Wizards;

public class Ulra extends Wizard {
    public Ulra(int position) {
        super(position, 50, 250, 250, 20, 30, 25, 15, 40, WizardType.ULRA);
    }

    @Override
    public String toString() {
        return "Ulra wields powerful magic to unleash devastating explosions that deal significant%n" +
                "damage to all enemies without sacrificing herself.%n%n";
    }
}
