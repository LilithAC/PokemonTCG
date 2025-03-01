import java.util.List;

import static java.util.Collections.shuffle;

public class PkmnGame {

    public Player player1;
    private Player player2;

    //constructs pokemon game with two players
    public PkmnGame(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public static void runGame() {
        PlayerInput.printMainMenu();
        var choice = PlayerInput.getInput();

        while (!List.of(1, 2 ,3).contains(choice)) {
            System.out.println(String.format(PlayerInput.ERROR_NUM, choice));

            PlayerInput.printMainMenu();
            choice = PlayerInput.getInput();
        }

        switch (choice) {
            case 1 : PlayerInput.startGame();
                break;
            case 2 : PlayerInput.options();
                break;
            case 3 : PlayerInput.endGame();
                break;
            default:
                throw new IllegalArgumentException(String.format(PlayerInput.ERROR_NUM, choice));
        }
    }

    //checks deck sizes, shuffles decks, and fills hands and prize piles
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

        gameLoop();
    }

    //returns winner of the game
    public Player gameLoop(){
        while ((!checkLoser(player1) && !checkWin(player1)) && (!checkLoser(player2) && !checkWin(player2))) {
            PlayerInput.gameTurn(player1, player2);
            if (checkWin(player1) || checkLoser(player2)) {
                return player1;
            }
            PlayerInput.gameTurn(player2, player1);
            if (checkWin(player2) || checkLoser(player1)) {
                return player2;
            }
        }
        return player1; //should never reach here
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
