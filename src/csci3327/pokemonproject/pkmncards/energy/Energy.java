package csci3327.pokemonproject.pkmncards.energy;

import csci3327.pokemonproject.pkmncards.PkmnCard;

public abstract class Energy extends PkmnCard {

    protected EnergyType type;

    public Energy() {

    }

    public EnergyType getType() {
        return type;
    }

}
