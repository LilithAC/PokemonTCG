import java.util.InputMismatchException;
import java.util.Scanner;

public class PlayerInput {

    private Player player;
    private Scanner scanner;
    private int counter;

    public PlayerInput(Player player) {
        this.player = player;
        scanner = new Scanner(System.in);
    }

    public void attachEnergy() {
        if (counter > 0) {
            System.out.println("Cannot attach any more energy this turn.");
        } else {
            try {
                System.out.println("Please choose a Pokemon to attach energy to.");
                int choice = scanner.nextInt();
            } catch (InputMismatchException e) {

            }
        }
    }

}
