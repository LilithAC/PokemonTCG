import java.util.ArrayList;

abstract class Pokemon extends PkmnCard {

    private int hp;
    private String type;
    private ArrayList<Energy> energyRes;

    public Pokemon() {

    }

    public int getHp() {
        return hp;
    }

    public void takeDamage(int dmg) {
        hp = hp - dmg;
    }

    public String getType() {
        return type;
    }

    //counts how many energy cards are attached
    public int energyCheck() {
        int count = 0;
        for (Energy card : energyRes) {
            count++;
        }
        return count;
    }

    //counts how many energy cards of a specified type are attached
    public int energyCheckType(String type) {
        int count = 0;
        for (Energy card : energyRes) {
            if (card.getType() == type) {
                count++;
            }
        }
        return count;
    }

    public ArrayList<Energy> getEnergyRes() {
        return energyRes;
    }
}
