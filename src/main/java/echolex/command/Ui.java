package echolex.command;

import java.util.Scanner;

public class Ui {

    /** Chat Indent */
    static final String INDENT = "    ";     // 4 spaces
    /** Chat Delimiter: Horizontal Line */
    static final String HORIZONTAL_LINE = "____________________________________________________________";

    /** Chat Hello / Goodbye */
    static final String HELLO = "Hello! I'm EchoLex" + "\n" + "What can I do for you?";
    static final String GOODBYE = "Bye. Hope to see you again soon!";

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
     * @param input Input to be formatted between horizontal lines.
     */
    public void boxInput(String input) {

        System.out.println(INDENT + HORIZONTAL_LINE);
        for (String line : input.split("\n")) {
            System.out.println(INDENT + " " + line);
        }
        System.out.println(INDENT + HORIZONTAL_LINE);
        System.out.println();

    }

}
