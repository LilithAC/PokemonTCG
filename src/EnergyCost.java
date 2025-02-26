import java.util.List;

enum EnergyType {
    FIRE,
    GRASS,
    WATER,
    ELECTRIC,
    ANY
}

class EnergyCost {

    protected  EnergyType type;
    protected int cost;

    public EnergyCost(EnergyType type, int cost) {
        this.type = type;
        this.cost = cost;
    }
}