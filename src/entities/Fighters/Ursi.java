package entities.Fighters;

public class Ursi extends Fighter {
    public Ursi(int position) {
        super(position, 20, 450, 450, 50, 30, 15, 15, 50);
    }

    @Override
    public String toString() {
        return " Ursi, the indomitable warrior, is a bastion of brute strength. With unyielding force and an %n" +
                "iron will, he crushes his foes in relentless combat.%n%n";
    }
}
