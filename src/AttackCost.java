import java.util.ArrayList;
import java.util.List;

public class AttackCost {
    protected ArrayList<EnergyCost> costs;

    public AttackCost(ArrayList<EnergyCost> costs) {
        this.costs = costs;
    }

    public boolean isMet(Pokemon pokemon) {
        for (EnergyCost cost : costs) {
            if (pokemon.energyCheck() < cost.cost && pokemon.energyCheckType(cost.type) < cost.cost) {
                return false;
            }
        }
        return true;
    }
}
