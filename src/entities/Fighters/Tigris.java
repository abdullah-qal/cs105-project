package entities.Fighters;

public class Tigris extends Fighter {
    public Tigris(int position) {
        super(position, 35, 350, 350, 40, 35, 15, 10, 65);
    }

    @Override
    public String toString() {
        return "A juggernaut of strength and resilience, Tigris charges into battle with relentless fury. His sheer%n" +
                "power and endurance make him a force to be reckoned with.%n%n";
    }
}
