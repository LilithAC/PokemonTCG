public class WaterEnergy extends Energy {

    private EnergyType type;
    private Pokemon attached;

    public WaterEnergy() {
        type = EnergyType.WATER;
    }

    public void setAttached(Pokemon poke) {
        attached = poke;
    }

    public EnergyType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Water Energy";
    }
}
