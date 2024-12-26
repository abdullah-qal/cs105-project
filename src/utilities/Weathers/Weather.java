package Weathers;
import entities.Character;
import java.util.Random;


public abstract class Weather {
    private String name;
    private String description;

    public Weather(String name,String description){
        this.name=name;
        this.description=description;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public abstract void applyEffects(Character character);
    public abstract void removeEffects(Character character);

    // Static method to randomly select a weather condition
    public static Weather getRandomWeather() {
        Weather[] weathers = {new SnowyWeather(), new RainyWeather(), new SunnyWeather(), new WindyWeather(), new FoggyWeather()};
        Random random = new Random();
        return weathers[random.nextInt(weathers.length)];
    }
    


}

