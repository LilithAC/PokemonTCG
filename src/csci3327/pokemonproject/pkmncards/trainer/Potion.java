package csci3327.pokemonproject.pkmncards.trainer;

import csci3327.pokemonproject.app.GameStateHandle;
import csci3327.pokemonproject.app.Player;

public class Potion extends Trainer {

    public Potion() {
        desc = "Heal 50 damage from your active Pokemon.";
    }

    @Override
    public void ability(Player user, Player defender) {
        if (user.getActive() == null) {
            System.out.println("You have no active pokemon to heal");
        } else {
            user.getActive().heal(50);
        }
    }

    @Override
    public String toString() {
        return "Potion";
    }
}
