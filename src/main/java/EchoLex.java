import java.util.Scanner;

public class EchoLex {

    /** Chat Delimiter: Horizontal Line */
    static final String HORIZONTAL_LINE = "____________________________________________________________";

    public static void main(String[] args) {

        echoCommand(" Hello! I'm EchoLex" + "\n" + " What can I do for you?");

        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();

        while (!userInput.equals("bye")) {
            echoCommand(userInput);
            userInput = in.nextLine();
        }

        echoCommand(" Bye. Hope to see you again soon!");

    }

    /**
     * Echoes commands entered by the user between horizontal lines.
     *
     * @param input Command entered by the user.
     */
    public static void echoCommand(String input) {

        String indent = "    ";     // 4 spaces

        System.out.println(indent + HORIZONTAL_LINE);
        for (String line : input.split("\n")) {
            System.out.println(indent + line);
        }
        System.out.println(indent + HORIZONTAL_LINE);

        System.out.println();

    }


}
