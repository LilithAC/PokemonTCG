package csci3327.pokemonproject.app;

import java.util.Random;

public class Coin {

    /**
     * Initializes a new coin object.
     */
    public Coin() {

    }

    /**
     * Flips a coin object landing on heads or tails
     * @return random boolean
     */
    public boolean flip() {
        return new Random().nextBoolean();
    }
}
