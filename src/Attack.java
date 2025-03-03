import java.util.ArrayList;

public class Attack {

    protected String name;
    protected String desc;
    protected int dmg;
    protected int critDmg;
    protected ArrayList<EnergyCost> energyCosts;
    protected EnergyCost totalCost;
    protected EnergyCost colorCost;
    protected AttackCost costs;

    public Attack(String name, int dmg, int critDmg, int total, int color, EnergyType type) {
        this.name = name;
        this.dmg = dmg;
        this.critDmg = critDmg;
        desc = "";

        totalCost = new EnergyCost(EnergyType.ANY, total);
        colorCost = new EnergyCost(type, color);

        energyCosts = new ArrayList<>();
        energyCosts.add(totalCost);
        energyCosts.add(colorCost);

        costs = new AttackCost(energyCosts);
    }

    public void ability() {

    }

    public ArrayList<String> costsToString() {
        ArrayList<String> costsF = new ArrayList<>();
        for (EnergyCost cost : energyCosts) {
            costsF.add(cost.toString());
        }
        return costsF;
    }
}
