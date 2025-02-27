import java.lang.reflect.Method;
import java.util.ArrayList;

abstract class Pokemon extends PkmnCard {

    protected int hp;
    protected int retreatCost;
    protected EnergyType type;
    protected ArrayList<Energy> energyRes;
    protected ArrayList<Attack> moveSet;
    protected AttackCost move1;
    protected AttackCost move2;

    public int getHp() {
        return hp;
    }

    public void takeDamage(int dmg) {
        hp = hp - dmg;
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

    public ArrayList<Attack> getMoveSet() { return moveSet;}

}
