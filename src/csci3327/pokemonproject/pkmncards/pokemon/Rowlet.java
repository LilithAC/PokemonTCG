package csci3327.pokemonproject.pkmncards.pokemon;

import csci3327.pokemonproject.pkmncards.Attack;
import csci3327.pokemonproject.pkmncards.energy.EnergyType;

import java.util.ArrayList;

public class Rowlet extends Pokemon {

     public Rowlet() {
         energyRes = new ArrayList<>();
         moveSet = new ArrayList<>();

         hp = 60;
         type = EnergyType.GRASS;
         retreatCost = 1;

         Attack tackle = new Attack("Tackle", 10, 10, 1, 0, EnergyType.ANY, "[ 1 TOTAL energy, 1 ANY energy ]");
         Attack leafBlade = new Attack("Leaf Blade", 10, 40, 2, 1, EnergyType.GRASS, "[ 2 TOTAL energy, 1 GRASS energy ]");
         leafBlade.desc = "Flip a coin. If heads, this attack does 30 more damage.";

         moveSet.add(tackle);
         moveSet.add(leafBlade);
     }

    @Override
    public String toString() {
        return "Rowlet";
    }
}
