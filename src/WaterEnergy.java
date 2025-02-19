public class WaterEnergy extends Energy {

    private String type;
    private Pokemon attached;

    public WaterEnergy() {
        type = "Water";
    }

    public void setAttached(Pokemon poke) {
        attached = poke;
    }

    public String getType() {
        return type;
    }
}
