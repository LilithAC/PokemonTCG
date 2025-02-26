import static java.util.Collections.shuffle;

public class PkmnGame {

    public Player player1;
    private Player player2;

    //constructs pokemon game with two players and decides turn order
    public PkmnGame(Player player1, Player player2) {

        Coin coin = new Coin();
        if (coin.flip()) {
            this.player1 = player1;
            this.player2 = player2;
        } else {
            this.player2 = player1;
            this.player1 = player2;
        }
    }

    public void startGame() {
        if (player1.getDeck().size() != 60) {
            throw new RuntimeException("A deck needs 60 cards exactly! Player 1 has " + player1.getDeck().size() + " cards");
        }
        if (player2.getDeck().size() != 60) {
            throw new RuntimeException("A deck needs 60 cards exactly! PLayer 2 has " + player2.getDeck().size() + " cards");
        }

        shuffle(player1.getDeck());
        shuffle(player2.getDeck());

        fillHand(player1);
        fillHand(player2);
        fillPrize(player1);
        fillPrize(player2);
    }

    public void gameLoop(){
        while (!checkLoser(player1) || !checkLoser(player2)) {
            takeTurn(player1);
            checkLoser(player2);
            takeTurn(player2);
        }
    }

    //sends both players hands and prize pile cards back into their deck
    public void stopGame() {

        player1.getDeck().addAll(player1.getHand());
        player1.getHand().clear();

        player1.getDeck().addAll(player1.getPrizePile());
        player1.getPrizePile().clear();

        player1.getDeck().addAll(player1.getDiscard());
        player1.getDiscard().clear();

        player2.getDeck().addAll(player2.getHand());
        player2.getHand().clear();

        player2.getDeck().addAll(player2.getPrizePile());
        player2.getPrizePile().clear();

        player2.getDeck().addAll(player2.getDiscard());
        player2.getDiscard().clear();
    }

    /**needs to check if any conditions are true:
     * player has 0 pokemon in play
     * player's deck is empty
    **/
    public boolean checkLoser(Player player) {
        return player.getDeck().isEmpty() || (player.getBench().isEmpty() && player.getActive() == null);
    }

    public boolean checkWin(Player player) {
        return player.getPrizePile().isEmpty();
    }

    public void takeTurn(Player player) {
        drawCard(player);
    }

    //adds a PkmnCard to hand and remove it from deck
    public void drawCard(Player player) {
        player.addHand(player.getDeck().getFirst());
        player.getDeck().removeFirst();
    }

    //populates hand with 7 PkmnCards
    public void fillHand(Player player) {
        for (int i = 0; i < 7; i++) {
            drawCard(player);
        }
    }

    //populates prize pile with 6 PkmnCards
    public void fillPrize(Player player) {
        for(int i = 0; i < 6; i++) {
            player.getPrizePile().add(player.getDeck().get(i));
            player.getDeck().remove(i);
        }
    }

    //TO DO:

    private void mulligan(Player player) {

        player.getDeck().addAll(player.getHand());
        player.getHand().clear();

        shuffle(player.getDeck());

        for (int i = 0; i < 8; i++) {
            drawCard(player);
        }

        System.out.println( player + " mulliganed.");
    }

}
