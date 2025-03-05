package csci3327.pokemonproject.pkmncards.trainer;

import csci3327.pokemonproject.app.Player;
import csci3327.pokemonproject.pkmncards.PkmnCard;

public abstract class Trainer extends PkmnCard {

    protected String desc;

    /**
     * Trainer's specific ability that accesses both players cards
     * @param user player using the card
     * @param defender player currently inactive
     */
    public void ability(Player user, Player defender) {

    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
