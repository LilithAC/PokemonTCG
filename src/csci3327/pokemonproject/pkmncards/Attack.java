package csci3327.pokemonproject.pkmncards;

import csci3327.pokemonproject.pkmncards.energy.EnergyType;

import java.util.ArrayList;

public class Attack {

    protected String name;
    protected String desc;
    protected String costsName;
    protected int dmg;
    protected int critDmg;
    protected ArrayList<EnergyCost> energyCosts;
    protected EnergyCost totalCost;
    protected EnergyCost colorCost;
    protected AttackCost costs;

    public Attack(String name, int dmg, int critDmg, int total, int color, EnergyType type, String costsName) {
        this.name = name;
        this.dmg = dmg;
        this.critDmg = critDmg;
        this.costsName = costsName;
        desc = "";

        totalCost = new EnergyCost(EnergyType.ANY, total);
        colorCost = new EnergyCost(type, color);

        energyCosts = new ArrayList<>();
        energyCosts.add(totalCost);
        energyCosts.add(colorCost);

        costs = new AttackCost(energyCosts);
    }

    public AttackCost getCosts() {
        return costs;
    }

    public int getCritDmg() {
        return critDmg;
    }

    public int getDmg() {
        return dmg;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() { return name;}

    public String getCostsName() {
        return costsName;
    }
}
