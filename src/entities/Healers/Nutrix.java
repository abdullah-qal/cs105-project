package entities.Healers;

public class Nutrix extends Healer {
    public Nutrix(int position) {
        super(position, 15, 275, 275, 20, 15, 60, 0, 0, 35, 20);
    }

    @Override
    public String toString() {
        return "The gentle healer, Nutrix, is a beacon of hope on the battlefield. Her divine touch%n" +
                "restores life and bolsters her allies when they need it most.%n%n";
    }
}
