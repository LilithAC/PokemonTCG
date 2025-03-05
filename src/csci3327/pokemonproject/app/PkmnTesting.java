package csci3327.pokemonproject.app;

public class PkmnTesting {

    public static void main(String[] args) {

        GameStateHandle.printMainMenu();
        int choice = PlayerInput.getInput();
        while (true) {
            GameStateHandle.step(choice);
            if (GameStateHandle.getState() == State.WINNER || GameStateHandle.getState() == State.EXIT) {
                break;
            }
            choice = PlayerInput.getInput();
        }
        System.exit(0);
    }
}
