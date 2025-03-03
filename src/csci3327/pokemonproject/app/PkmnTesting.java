package csci3327.pokemonproject.app;

public class PkmnTesting {

    public static void main(String[] args) {

        GameStateHandle.printMainMenu();
        int choice = PlayerInput.getInput();

        while (true) {
            GameStateHandle.step(choice);
            if (GameStateHandle.getState() == State.WINNER) {
                break;
            }
            choice = PlayerInput.getInput();
        }
        System.exit(0);

        // ===== THIRD CHECKUP =====
        //1-4 different pokemon minimum
        //1-5 different trainer cards
        //as much energy as desired
        //Sample: 20 Pokemon, 30 trainers, 10 energy

        //you should be able to play against yourself OR an ai that does everything it can do and ends turn
        //make javaDoc, label extra features

    }
}
