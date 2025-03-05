package csci3327.pokemonproject.app;

import java.util.Scanner;

public class PlayerInput {

    private static final Scanner in = new Scanner(System.in);
    public static final String ERROR_FRMT = "Invalid choice: %s. Must be a number.";

    /**
     * Takes input from the console and repeats until given an integer
     * @return integer input
     */
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
            next = PlayerInput.in.next();
        }
        return choice;
    }
}
