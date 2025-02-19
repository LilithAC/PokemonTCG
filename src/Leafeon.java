import java.util.ArrayList;

public class Leafeon extends Pokemon {

    private int hp;
    private String type;
    private ArrayList<Energy> energyRes;

    public Leafeon() {
        energyRes = new ArrayList<>();
        hp = 90;
        type = "Grass";
    }

    public void spiralDrain(Pokemon poke) {
        if (energyCheck() > 1 && energyCheckType("Grass") > 0) {
            poke.takeDamage(40);
            hp = hp + 30;
        } else {
            System.out.println("Not enough energy.");
        }
    }

    public void magicalLeaf(Pokemon poke) {
        if (energyCheck() > 2 && energyCheckType("Grass") > 0) {
            Coin coin = new Coin();
            if (coin.flip()) {
                poke.takeDamage(80);
            } else {
                poke.takeDamage(50);
            }
        } else {
            System.out.println("Not enough energy.");
        }
    }

}
