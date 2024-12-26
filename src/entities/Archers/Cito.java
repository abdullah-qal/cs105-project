package entities.Archers;

public class Cito extends Archer {
    public Cito(int position) {
        super(position, 60, 150, 150, 15, 35, 50, 25, 50);
    }

    @Override
    public String toString() {
        return "Cito, the eagle-eyed archer, reigns supreme from a distance. His arrows fly true, striking%n" +
                "with uncanny accuracy and leaving his enemies in awe.%n%n";
    }
}