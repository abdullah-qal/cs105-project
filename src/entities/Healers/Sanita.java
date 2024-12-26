package entities.Healers;

public class Sanita extends Healer {
    public Sanita(int position) {
        super(position, 15, 200, 200, 20, 15, 60, 0, 0, 50, 5);
    }

    @Override
    public String toString() {
        return " Sanita wields the power of healing with unmatched expertise. Her restorative %n" +
                "abilities turn the tide of battle, making her an indispensable ally.%n%n";
    }
}
