import java.lang.reflect.Method;
import java.util.ArrayList;

abstract class Pokemon extends PkmnCard {

    protected int hp;
    protected int retreatCost;
    protected EnergyType type;
    protected ArrayList<Energy> energyRes;
    protected ArrayList<Attack> moveSet;

    public int getHp() {
        return hp;
    }

    public void takeDamage(int dmg) {
        hp = hp - dmg;
    }

    public void heal(int heal) {
        hp = hp + heal;
    }

    public EnergyType getType() {
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
    public int energyCheckType(EnergyType type) {
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

    public ArrayList<Attack> getMoveSet() { return moveSet;}

}
