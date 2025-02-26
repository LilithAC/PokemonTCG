import java.util.ArrayList;

public class Attack {

    protected String name;
    protected int dmg;
    protected int critDmg;
    protected ArrayList<EnergyCost> energyCosts;

    public Attack(String name, int dmg, int critDmg) {
        this.name = name;
        this.dmg = dmg;
        this.critDmg = critDmg;
        energyCosts = new ArrayList<>();
    }
}
