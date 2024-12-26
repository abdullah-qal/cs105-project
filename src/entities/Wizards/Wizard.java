package entities.Wizards;

import entities.Character;

public abstract class Wizard extends Character {
    public enum WizardType {
        KANZO, ULRA
    }

    private WizardType wizardType;
    private boolean explosionActive;

    public Wizard(int position, int damage, int health, int maxHealth, double defense, int movementSpeed,
                  int range, double critRate, double critDmg, WizardType wizardType) {
        super(position, damage, health, maxHealth, defense, movementSpeed, range, critRate, critDmg);
        this.wizardType = wizardType;
        this.explosionActive = false;
    }

    // Wizard-specific method:Explosion.
    //If Kanzo uses Explosion, he sacrifies hisself and halve enemies' health.
    //If Ulra uses Explosion, he gives 100 damage to enemies.
    public void activateExplosion(Character opponent1, Character opponent2) {
        if (explosionActive) {
            System.out.printf("%s has already used its explosion ability!%n", wizardType);
            return;
        }

        switch (wizardType) {
            case KANZO -> performKanzoExplosion(opponent1, opponent2);
            case ULRA -> performUlraExplosion(opponent1, opponent2);
            default -> throw new IllegalStateException("Unknown wizard type: " + wizardType);
        }

        explosionActive = true;
        System.out.printf("%s has activated its explosion ability!%n%n", wizardType);
    }

    // Kanzo-specific
    private void performKanzoExplosion(Character opponent1, Character opponent2) {
        if (opponent1.isLiving_status()) {
            int halvedHealth = opponent1.getHealth() / 2;
            opponent1.setHealth(halvedHealth);
            System.out.printf("%s's health is halved to %.2f HP.%n", opponent1.getClass().getSimpleName(), Character.normalisedValue(halvedHealth));
        }
        if (opponent2.isLiving_status()) {
            int halvedHealth = opponent2.getHealth() / 2;
            opponent2.setHealth(halvedHealth);
            System.out.printf("%s's health is halved to %.2f HP.%n", opponent2.getClass().getSimpleName(), Character.normalisedValue(halvedHealth));
        }

        // Sacrifices Kanzo
        this.takeDamage(this.getHealth());
        System.out.println("Kanzo sacrifices itself to unleash its explosion ability.");
    }

    // Ulra-specific
    private void performUlraExplosion(Character opponent1, Character opponent2) {
        if (opponent1.isLiving_status()) {
            opponent1.takeDamage(100);
            System.out.printf("%s takes 100 damage from Ulra's explosion ability.%n", opponent1.getClass().getSimpleName());
        }
        if (opponent2.isLiving_status()) {
            opponent2.takeDamage(100);
            System.out.printf("%s takes 100 damage from Ulra's explosion ability.%n", opponent2.getClass().getSimpleName());
        }
    }

    public boolean isExplosionActive() {
        return explosionActive;
    }
}
