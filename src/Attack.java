public class Attack {

    protected String name;
    protected int dmg;
    public int energyCost;

    public Attack(String name, int dmg, int energyCost) {
        this.name = name;
        this.dmg = dmg;
        this.energyCost = energyCost;
    }
}
