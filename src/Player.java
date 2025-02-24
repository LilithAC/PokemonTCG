import java.util.ArrayList;

import static java.util.Collections.shuffle;

public class Player {

    private ArrayList<PkmnCard> deck;
    private ArrayList<PkmnCard> hand;
    private ArrayList<PkmnCard> prizePile;
    private ArrayList<PkmnCard> discard;
    private ArrayList<Pokemon> bench;
    private Pokemon active;

    public Player(ArrayList<PkmnCard> deck) {
        this.deck = deck;
        hand = new ArrayList<>();
        prizePile = new ArrayList<>();
        discard = new ArrayList<>();
        bench = new ArrayList<>();
    }

    public boolean containsPkmn() {
        int pkmn = 0;
        for (int i = 0; i < hand.size(); i++) {
            if(hand.get(i) instanceof Pokemon ) {
                pkmn++;
            }
        }

        return pkmn != 0;
    }
    //TO DO:
    //needs to check if player has already played an energy card this turn
    public void attachEnergy(Pokemon poke, Energy energy) {
        int energyPos = hand.indexOf(energy);
        poke.getEnergyRes().add(energy);
        hand.remove(energyPos);
    }

    public void playPkmnActive(Pokemon poke) {
        Object o = hand.get(hand.indexOf(poke));
        Pokemon newActive = (Pokemon) o;
        active = newActive;
    }

    public void playPkmnBench(Pokemon poke) {
        Object o = hand.get(hand.indexOf(poke));
        Pokemon toBench = (Pokemon) o;
        bench.add(toBench);
    }

    public ArrayList<PkmnCard> getDeck() {
        return deck;
    }

    public ArrayList<PkmnCard> getHand() {
        return hand;
    }

    public void setHand(ArrayList<PkmnCard> hand) {
        this.hand = hand;
    }

    public void addHand(PkmnCard card) {
        this.hand.add(card);
    }

    public ArrayList<PkmnCard> getPrizePile() {
        return prizePile;
    }

    public ArrayList<PkmnCard> getDiscard() {
        return discard;
    }

    public ArrayList<Pokemon> getBench() {
        return bench;
    }

    public void addBench(Pokemon poke) {
        bench.add(poke);
    }

    public Pokemon getActive() {
        return active;
    }

    public void setActive(Pokemon active) {
        this.active = active;
    }

    //functional programming :3
    public boolean checkPrize(int candies) {

        int count = 0;
        for (PkmnCard card : prizePile) {
            if (card instanceof RareCandy) {
                count++;
            }
        }
        return count == candies;
    }

}
