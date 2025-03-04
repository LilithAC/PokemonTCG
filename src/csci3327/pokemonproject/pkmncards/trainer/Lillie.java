package csci3327.pokemonproject.pkmncards.trainer;

import csci3327.pokemonproject.app.Player;

public class Lillie extends Trainer {

    public Lillie() {
        desc = "Draw cards until you have 6 cards in your hand.";
    }

    @Override
    public void ability(Player user, Player defender) {
        if (user.getHand().size() >= 6 ) {
            System.out.println("This card had no effect.");
        } else {
            for (int i = user.getHand().size(); i < 6; i++) {
                user.drawCard();
            }
        }
    }

    @Override
    public String toString() {
        return "Lillie";
    }
}
