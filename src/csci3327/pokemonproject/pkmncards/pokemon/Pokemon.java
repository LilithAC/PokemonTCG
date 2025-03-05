package csci3327.pokemonproject.pkmncards.pokemon;

import csci3327.pokemonproject.pkmncards.Attack;
import csci3327.pokemonproject.pkmncards.energy.Energy;
import csci3327.pokemonproject.pkmncards.energy.EnergyType;
import csci3327.pokemonproject.pkmncards.PkmnCard;

import java.util.ArrayList;

public abstract class Pokemon extends PkmnCard {

    protected int hp;
    protected int maxHp = hp;
    protected int retreatCost;
    protected EnergyType type;
    protected ArrayList<Energy> energyRes;
    protected ArrayList<Attack> moveSet;

    /**
     * Pokemon takes specified damage amount.
     * @param dmg amount damaged
     */
    public void takeDamage(int dmg) {
        hp = hp - dmg;
    }

    /**
     * Pokemon heals for specified amount. If the healing
     * would bring it over its maximum HP, it will only heal to the maximum
     * @param heal amount healed
     */
    public void heal(int heal) {
        hp = hp + heal;
        if (hp > maxHp) {
            hp = maxHp;
        }
    }

    /**
     * Counts how many energy cards are attached to a pokemon
     * @return total amount of energy attached
     */
    public int energyCheck() {
        int count = 0;
        for (Energy card : energyRes) {
            count++;
        }
        return count;
    }

    /**
     * Counts how many energy cards of a specified type are attached
     * to a pokemon
     * @param type Energy type to be searched for
     * @return amount of specified energy attached
     */
    public int energyCheckType(EnergyType type) {
        int count = 0;
        for (Energy card : energyRes) {
            if (card.getType() == type) {
                count++;
            }
        }
        return count;
    }

    /**
     * Formats the pokemon's energy reservoir
     * @return Arraylist of pokemon's energy reservoir
     */
    public ArrayList<String> resToString() {
        ArrayList<String> resF = new ArrayList<>();
        for (Energy energy : energyRes) {
            resF.add(energy.toString());
        }

        if (resF.isEmpty()) {
            resF.add("None");
        }
        return resF;
    }


    public ArrayList<Energy> getEnergyRes() {
        return energyRes;
    }

    public EnergyType getType() {
        return type;
    }

    public int getHp() {
        return hp;
    }

    public ArrayList<Attack> getMoveSet() { return moveSet;}

    public int getRetreatCost() {
        return retreatCost;
    }

}
