package csci3327.pokemonproject.app;

import csci3327.pokemonproject.pkmncards.energy.Energy;
import static java.util.Collections.shuffle;

public class PkmnGame {

    protected Player player1;
    protected Player player2;
    protected int turnCount;

    /**
     * Creates a pokemon game with 2 players and initializes turn count.
     * @param player1 the first player initialized
     * @param player2 the second player initialized
     */
    public PkmnGame(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        turnCount = 1;
    }

    /**
     * Checks deck sizes, shuffles decks, fills hands and prize piles,
     * and decides turn order.
     */
    public void startGame() {
        System.out.println("Starting game...");

        if (player1.getDeck().size() != 60) {
            throw new RuntimeException("A deck needs 60 cards exactly! " + player1.getName() + " has " + player1.getDeck().size() + " cards");
        }
        if (player2.getDeck().size() != 60) {
            throw new RuntimeException("A deck needs 60 cards exactly! " + player2.getName() + " has " + player2.getDeck().size() + " cards");
        }

        shuffle(player1.getDeck());
        shuffle(player2.getDeck());

        fillHand(player1);
        fillHand(player2);

        while (!player1.containsPkmn()) {
            mulligan(player1, player2);
        }
        while (!player2.containsPkmn()) {
            mulligan(player2, player1);
        }

        fillPrize(player1);
        fillPrize(player2);

        Coin coin = new Coin();
        if (coin.flip()) {
            System.out.println("The coin flipped heads. " + player1.getName() +" goes first.");
            GameStateHandle.activePlayer = player1;
            GameStateHandle.inactivePlayer = player2;
        } else {
            System.out.println("The coin flipped tails. " + player2.getName() +" goes first.");
            GameStateHandle.activePlayer = player2;
            GameStateHandle.inactivePlayer = player1;
        }
    }

    /**
     * Sends both players hands, prize piles, and discard piles back into their deck.
     */
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

    /**
     * Checks if player has 0 pokemon in play or if deck is empty.
     * @param player player to check
     * @return whether the player lost
     */
    public boolean checkLoser(Player player) {
        return player.getDeck().isEmpty() || (player.getBench().isEmpty() && player.getActive()==null);
    }

    /**
     * Checks if players prize pile is empty.
     * @param player player to check
     * @return whether the player won
     */
    public boolean checkWin(Player player) {
        return player.getPrizePile().isEmpty();
    }

    /**
     * Sends all active pokemon's attached energy to discard pile then sends pokemon as well.
     * @param player player whose pokemon was KO'd
     */
    public void knockOut(Player player) {
        System.out.println(player.getActive().toString() + " was knocked out! "+ player.getActive().toString() + " sent to discard pile.");

        for (Energy energy : player.getActive().getEnergyRes()) {
            player.getDiscard().add(energy);
        }
        player.getDiscard().add(player.getActive());
        player.setActive(null);
    }

    /**
     * Populates hand with 7 pokemon cards.
     * @param player player whose hand is to be filled
     */
    public void fillHand(Player player) {
        for (int i = 0; i < 7; i++) {
            player.drawCard();
        }
    }

    /**
     * Populates prize pile with 6 pokemon cards.
     * @param player player whose prize pile is to be filled
     */
    public void fillPrize(Player player) {
        for(int i = 0; i < 6; i++) {
            player.getPrizePile().add(player.getDeck().get(i));
            player.getDeck().remove(i);
        }
    }

    /**
     * If player has no pokemon cards in hand,
     * they shuffle it back into their deck while opponent draws card.
     * @param player player who mulligans
     * @param player2 player who draws a card
     */
    private void mulligan(Player player, Player player2) {

        player.getDeck().addAll(player.getHand());
        player.getHand().clear();

        shuffle(player.getDeck());

        for (int i = 0; i < 7; i++) {
            player.drawCard();
        }

        System.out.println( player.getName() + " mulliganed. " + player2.getName() + " draws another card.");

        player2.drawCard();
    }

    /**
     * Prints the winner and stops the game
     * @param winner player who one the game
     */
    public void gameOver(Player winner) {
        System.out.println("The game is over! " + winner.getName() + " won!");
        stopGame();
    }

}
