import static java.util.Collections.shuffle;

public class PkmnGame {

    protected Player player1;
    protected Player player2;
    protected int turnCount;

    //constructs pokemon game with two players
    public PkmnGame(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        turnCount = 1;
    }

    //checks deck sizes, shuffles decks, and fills hands and prize piles
    public void startGame() {
        System.out.println("Starting game...");

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

        //write mulligan code here

        fillPrize(player1);
        fillPrize(player2);

        Coin coin = new Coin();
        if (coin.flip()) {
            System.out.println("The coin flipped heads. Player 1 goes first.");
            GameStateHandle.activePlayer = player1;
            GameStateHandle.inactivePlayer = player2;
        } else {
            System.out.println("The coin flipped tails. Player 2 goes first.");
            GameStateHandle.activePlayer = player2;
            GameStateHandle.inactivePlayer = player1;
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

    // checks if player has 0 pokemon in play or if deck is empty
    public boolean checkLoser(Player player) {
        return player.getDeck().isEmpty() || (player.getBench().isEmpty() && player.getActive() == null);
    }

    //checks if players prize pile is empty
    public boolean checkWin(Player player) {
        return player.getPrizePile().isEmpty();
    }

    //sends all attached energy to discard pile then sends pokemon as well
    public void knockOut(Player player) {
        System.out.println(player.getActive().toString() + " was knocked out! Pokemon sent to discard pile.");

        for (Energy energy : player.getActive().getEnergyRes()) {
            player.getDiscard().add(energy);
        }
        player.getDiscard().add(player.getActive());
    }

    //populates hand with 7 PkmnCards
    public void fillHand(Player player) {
        for (int i = 0; i < 7; i++) {
            player.drawCard();
        }
    }

    //populates prize pile with 6 PkmnCards
    public void fillPrize(Player player) {
        for(int i = 0; i < 6; i++) {
            player.getPrizePile().add(player.getDeck().get(i));
            player.getDeck().remove(i);
        }
    }

    private void mulligan(Player player) {

        player.getDeck().addAll(player.getHand());
        player.getHand().clear();

        shuffle(player.getDeck());

        for (int i = 0; i < 7; i++) {
            player.drawCard();
        }

        System.out.println( player + " mulliganed.");
    }

    public void gameOver(Player winner) {
        System.out.println("The game is over! " + winner + " won!");
        stopGame();
    }

}
