package csci3327.pokemonproject.pkmncards.trainer;

import csci3327.pokemonproject.app.Player;

public class Potion extends Trainer {

    public Potion() {
        desc = "Heal 50 damage from your active Pokemon.";
    }

    @Override
    public void ability(Player user, Player defender) {
        user.getActive().heal(50);
    }

    @Override
    public String toString() {
        return "Potion";
    }
}
