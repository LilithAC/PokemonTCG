package csci3327.pokemonproject.pkmncards.pokemon;

import csci3327.pokemonproject.pkmncards.Attack;
import csci3327.pokemonproject.pkmncards.energy.EnergyType;

import java.util.ArrayList;

public class Pumpkaboo extends Pokemon {

    public Pumpkaboo() {
        energyRes = new ArrayList<>();
        moveSet = new ArrayList<>();

        hp = 60;
        type = EnergyType.PSYCHIC;
        retreatCost = 2;

        Attack seedBomb = new Attack("Seed Bomb", 10, 10, 1,1, EnergyType.ANY, "[ 1 TOTAL energy, 1 ANY energy ]");
        Attack recklessCharge = new Attack("Reckless Charge", 70, 70, 3,3, EnergyType.ANY, "[ 3 TOTAL energy, 3 PSYCHIC energy ]");

        moveSet.add(seedBomb);
        moveSet.add(recklessCharge);
    }

    @Override
    public String toString() {
        return "Pumpkaboo";
    }
}
