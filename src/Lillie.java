public class Lillie extends Trainer {

    public Lillie() {
        desc = "Draw cards until you have 6 cards in your hand.";
    }

    @Override
    public void ability(Player user, Player defender) {
        for (int i = user.getHand().size(); i < 7; i++) {
            user.drawCard();
        }
    }

    @Override
    public String toString() {
        return "Lillie";
    }
}
