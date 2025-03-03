package csci3327.pokemonproject.pkmncards;

import csci3327.pokemonproject.pkmncards.pokemon.Pokemon;

import java.util.ArrayList;

public class AttackCost {
    protected ArrayList<EnergyCost> costs;

    public AttackCost(ArrayList<EnergyCost> costs) {
        this.costs = costs;
    }

    //checks if attacks energy cost is met
    public boolean isMet(Pokemon pokemon) {

        for (EnergyCost cost : costs) {

            if (pokemon.energyCheck() < cost.cost && pokemon.energyCheckType(cost.type) < cost.cost) {
                return false;
            }
        }
        return true;
    }
}
