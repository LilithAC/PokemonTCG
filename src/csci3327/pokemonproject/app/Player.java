package csci3327.pokemonproject.app;

import csci3327.pokemonproject.pkmncards.energy.Energy;
import csci3327.pokemonproject.pkmncards.PkmnCard;
import csci3327.pokemonproject.pkmncards.pokemon.Pokemon;
import csci3327.pokemonproject.pkmncards.trainer.RareCandy;

import java.util.ArrayList;

public class Player {

    private String name;
    private ArrayList<PkmnCard> deck;
    private ArrayList<PkmnCard> hand;
    private ArrayList<PkmnCard> prizePile;
    private ArrayList<PkmnCard> discard;
    private ArrayList<Pokemon> bench;
    private Pokemon active;
    private int energyCounter;

    /**
     * Initializes a new player with a name and deck of cards.
     * @param deck deck to be attached to player
     * @param name formatted name of the player
     */
    public Player(ArrayList<PkmnCard> deck, String name) {
        this.name = name;
        this.deck = deck;
        hand = new ArrayList<>();
        prizePile = new ArrayList<>();
        discard = new ArrayList<>();
        bench = new ArrayList<>();
    }

    /**
     * Checks if the player's hand contains and pokemon monster cards.
     * @return whether the players hand contains 0 pokemon
     */
    public boolean containsPkmn() {
        int pkmn = 0;
        for (PkmnCard card : hand) {
            if(card instanceof Pokemon ) {
                pkmn++;
            }
        }

        return pkmn != 0;
    }

    /**
     * Attaches specified energy to specified pokemon's energy reservoir.
     * @param poke pokemon to have energy attached
     * @param energy energy card to be attached
     */
    public void attachEnergy(Pokemon poke, Energy energy) {
        int energyPos = hand.indexOf(energy);
        poke.getEnergyRes().add(energy);
        hand.remove(energyPos);
        energyCounter++;
    }

    /**
     * Adds a single pokemon card to players hand and removes it from deck.
     */
    public void drawCard() {
        hand.add(getDeck().getFirst());
        deck.removeFirst();
    }

    /**
     * adds specified amount of pokemon cards to players hand and removes it from deck.
     * @param amount number of cards to be drawn
     */
    public void drawCard(int amount) {
        for (int i = 0; i < amount; i++) {
            hand.add(getDeck().getFirst());
            deck.removeFirst();
        }
    }

    /**
     * Takes a single card from prize pile and adds it to hand.
     */
    public void removePrize() {
        hand.add(getPrizePile().getFirst());
        prizePile.removeFirst();
    }

    /**
     * Checks prize pile for rare candies
     * @param candies amount of candies in the deck
     * @return whether all the candies are in the players prize pile
     */
    public boolean checkPrize(int candies) {

        int count = 0;
        for (PkmnCard card : prizePile) {
            if (card instanceof RareCandy) {
                count++;
            }
        }
        return count == candies;
    }

    public ArrayList<PkmnCard> getDeck() {
        return deck;
    }

    public ArrayList<PkmnCard> getHand() {
        return hand;
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

    public String getName() {
        return name;
    }

    public Pokemon getActive() {
        return active;
    }

    public void setActive(Pokemon active) {
        this.active = active;
    }

    public int getEnergyCounter() { return energyCounter; }

    public void setEnergyCounter(int energyCounter) { this.energyCounter = energyCounter; }

}
