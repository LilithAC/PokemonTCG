import java.util.List;

class EnergyCost {

    protected  EnergyType type;
    protected int cost;

    public EnergyCost(EnergyType type, int cost) {
        this.type = type;
        this.cost = cost;
    }

    public String toString() {
        return (cost + " " + type + " energy");
    }
}