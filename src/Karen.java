public class Karen extends Trainer{

    public Karen() {
        desc = "Each player shuffles all Pokémon in their discard pile into their deck.";
    }

    public void ability(Player user, Player defender) {
        user.getDeck().addAll(user.getDiscard());
        user.getDiscard().clear();
        defender.getDeck().addAll(defender.getDiscard());
        defender.getDiscard().clear();
    }
}
