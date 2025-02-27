import java.util.ArrayList;

public class Attack {

    protected String name;
    protected int dmg;
    protected int critDmg;
    protected ArrayList<EnergyCost> energyCosts;
    protected EnergyCost colorlessCost;
    protected EnergyCost colorCost;
    protected AttackCost costs;

    public Attack(String name, int dmg, int critDmg, int colorless, int color, EnergyType type) {
        this.name = name;
        this.dmg = dmg;
        this.critDmg = critDmg;

        colorlessCost = new EnergyCost(EnergyType.ANY, colorless);
        colorCost = new EnergyCost(type, color);

        energyCosts = new ArrayList<>();
        energyCosts.add(colorlessCost);
        energyCosts.add(colorCost);

        costs = new AttackCost(energyCosts);
    }
}
