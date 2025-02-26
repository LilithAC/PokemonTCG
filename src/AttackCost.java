import java.util.List;

public class AttackCost {

    public class AttackEnergyCost {
        List<EnergyCost> costs;

        public boolean isMet(Pokemon pokemon) {
            for (EnergyCost cost : costs) {
                if (pokemon.energyCheck() < cost.cost && pokemon.energyCheckType(cost.type) < cost.cost) {
                    return false;
                }
            }
            return true;
        }
    }
}
