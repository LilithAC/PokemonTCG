import java.util.ArrayList;

public class Player {

    private ArrayList<PkmnCard> deck;
    private ArrayList<PkmnCard> hand;
    private ArrayList<PkmnCard> prizePile;
    private ArrayList<PkmnCard> discard;
    private ArrayList<Pokemon> bench;
    private Pokemon active;
    //can turn this into a boolean
    private int energyCounter;

    public Player(ArrayList<PkmnCard> deck) {
        this.deck = deck;
        hand = new ArrayList<>();
        prizePile = new ArrayList<>();
        discard = new ArrayList<>();
        bench = new ArrayList<>();
    }

    public boolean containsPkmn() {
        int pkmn = 0;
        for (PkmnCard card : hand) {
            if(card instanceof Pokemon ) {
                pkmn++;
            }
        }

        return pkmn != 0;
    }

    //attaches specified energy to specified Pkmn's energy reservoir
    public void attachEnergy(Pokemon poke, Energy energy) {
        int energyPos = hand.indexOf(energy);
        poke.getEnergyRes().add(energy);
        hand.remove(energyPos);
        energyCounter++;
    }

    //adds a single PkmnCard to hand and removes it from deck
    public void drawCard() {
        hand.add(getDeck().getFirst());
        deck.removeFirst();
    }

    //adds specified amount of PkmnCards to hand
    public void drawCard(int amount) {
        for (int i = 0; i < amount; i++) {
            hand.add(getDeck().getFirst());
            deck.removeFirst();
        }
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

    public Pokemon getActive() {
        return active;
    }

    public void setActive(Pokemon active) {
        this.active = active;
    }

    public int getEnergyCounter() { return energyCounter; }

    public void setEnergyCounter(int energyCounter) { this.energyCounter = energyCounter; }

    //checks prize pile for rare candies
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
