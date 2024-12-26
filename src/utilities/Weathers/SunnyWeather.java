package Weathers;

import entities.Character;

public class SunnyWeather extends Weather {
    public SunnyWeather() {
        super("Sunny", "Default weather condition with no effects.");
    }

    @Override
    public void applyEffects(Character character) {
        // No effects applied due to default weather condition.
    }
    @Override
    public void removeEffects(Character character) {
        // No effects applied due to default weather condition.
    }
}
