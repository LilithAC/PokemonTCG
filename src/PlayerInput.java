import java.util.Scanner;

public abstract class PlayerInput {

    private static final Scanner in = new Scanner(System.in);

    public static final String ERROR_FRMT = "Invalid choice: %s. Must be a number.";

    //checks if player input was an integer
    public static int getInput() {
        int choice;
        String next = PlayerInput.in.next();

        while (true) {
            try {
                choice = Integer.parseInt(next);
                break;
            } catch (NumberFormatException e) {
                System.out.println(String.format(ERROR_FRMT, next));
            }
            GameStateHandle.printMainMenu();
            next = PlayerInput.in.next();
        }
        return choice;
    }
}
