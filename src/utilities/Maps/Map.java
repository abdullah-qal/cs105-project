package utilities;
import entities.Character;

public abstract class Map {
    private String name;
    private String description;

    public Map(String name,String description){
        this.name=name;
        this.description=description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public abstract void applyEffects(Character[] characters);
}
