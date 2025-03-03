package csci3327.pokemonproject.pkmncards;

import csci3327.pokemonproject.pkmncards.energy.EnergyType;

class EnergyCost {

    protected EnergyType type;
    protected int cost;
    protected String name;

    public EnergyCost(EnergyType type, int cost) {
        this.type = type;
        this.cost = cost;
    }

    public String toString() {
        name = (cost + " " + type + " energy");
        return name;
    }
}