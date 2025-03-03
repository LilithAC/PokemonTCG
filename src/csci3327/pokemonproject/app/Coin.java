package csci3327.pokemonproject.app;

import java.util.Random;

public class Coin {

    public Coin() {

    }

    public boolean flip() {
        return new Random().nextBoolean();
    }
}
