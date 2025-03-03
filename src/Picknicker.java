public class Picknicker extends Trainer {

    public Picknicker() {
        desc = "Flip a coin. If heads, draw 4 cards, if tails draw 2 cards.";
    }

    @Override
    public void ability(Player user, Player defender) {
        Coin coin = new Coin();
        if (coin.flip()) {
            user.drawCard(4);
        } else {
            user.drawCard(2);
        }
    }

    @Override
    public String toString() {
        return "Picknicker";
    }
}
