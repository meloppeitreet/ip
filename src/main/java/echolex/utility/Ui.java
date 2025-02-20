package echolex.utility;

import java.util.Scanner;

/**
 * Displays and formats text to a CLI user.
 */
public class Ui {

    /** Chat Indent */
    static final String INDENT = "    "; // 4 spaces
    /** Chat Delimiter: Horizontal Line */
    static final String HORIZONTAL_LINE = "____________________________________________________________";

    /** Chat Hello / Goodbye */
    static final String HELLO = "Welcome, mortal. In this fleeting existence, "
            + "time slips through our fingers like sand. "
            + "Today, you are given the chance to conquer the tasks before you, "
            + "to create meaning in the chaos. Complete what is in front of you—not for tomorrow, "
            + "but for the fleeting moments that remain. In this endless void, "
            + "let your actions give you direction. Let's begin, for the things that truly matter await.";
    static final String GOODBYE = "Goodbye. I hope you found meaning in your existence today :)";

    public Ui() { };

    /**
     * Shows welcome message to user.
     */
    public void showWelcome() {
        boxInput(HELLO);
    }

    /**
     * Shows goodbye message to user.
     */
    public void showGoodbye() {
        boxInput(GOODBYE);
    }

    /**
     * Get welcome message to user.
     */
    public String getWelcome() {
        return HELLO;
    }

    /**
     * Get goodbye message to user.
     */
    public String getGoodbye() {
        return GOODBYE;
    }

    /**
     * Read command from user.
     *
     * @return Command String.
     */
    public String readCommand() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    /**
     * Shows horizontal line.
     */
    public void showLine() {
        System.out.println(INDENT + HORIZONTAL_LINE);
    }

    /**
     * Echoes input between horizontal lines for chat formatting.
     *
     * @param inputs Input to be formatted between horizontal lines.
     */
    public void boxInput(String ... inputs) {

        System.out.println(INDENT + HORIZONTAL_LINE);
        for (String input : inputs) {
            for (String line : input.split("\n")) {
                System.out.println(INDENT + " " + line);
            }
            System.out.println();
        }
        System.out.println(INDENT + HORIZONTAL_LINE);
        System.out.println();

    }

}
