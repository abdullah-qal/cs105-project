package entities.Archers;

public class Sagitta extends Archer {
    public Sagitta(int position) {
        super(position, 45, 150, 150, 20, 25, 75, 35, 40);
    }

    @Override
    public String toString() {
        return "Sagitta is a sharpshooter of unparalleled skill. With vision as sharp as her aim, she%n" +
                "dominates the battlefield from incredible distances, where no enemy is safe.%n%n";
    }
}
