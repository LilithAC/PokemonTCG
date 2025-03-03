import java.util.ArrayList;

public class Rowlet extends Pokemon {

     public Rowlet() {
         energyRes = new ArrayList<>();
         moveSet = new ArrayList<>();

         hp = 60;
         type = EnergyType.GRASS;
         retreatCost = 1;

         Attack tackle = new Attack("Tackle", 10, 10, 1, 0, EnergyType.ANY);
         Attack leafBlade = new Attack("Leaf Blade", 10, 40, 2, 1, EnergyType.GRASS);
         leafBlade.desc = "Flip a coin. If heads, this attack does 30 more damage.";

         moveSet.add(tackle);
         moveSet.add(leafBlade);
     }

    @Override
    public String toString() {
        return "Rowlet";
    }
}
