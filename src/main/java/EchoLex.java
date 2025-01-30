import java.util.Scanner;
import java.util.ArrayList;

public class EchoLex {

    /** Chat Delimiter: Horizontal Line */
    static final String HORIZONTAL_LINE = "____________________________________________________________";
    /** User Input Text Memory */
    private static ArrayList<String> memory = new ArrayList<>();

    public static void main(String[] args) {

        echoInput("Hello! I'm EchoLex" + "\n" + "What can I do for you?");

        Scanner in = new Scanner(System.in);
        String userInput = "";
        boolean done = false;

        while (!done) {
            userInput = in.nextLine();
            done = parseInput(userInput);
        }

    }

    /**
     * Parses input entered by the user.
     *
     * @param input Input entered by the user.
     */
    public static boolean parseInput(String input) {

        switch (input) {
        case "list":
            echoInput(listCommand());
            break;
        case "bye":
            echoInput("Bye. Hope to see you again soon!");
            return true;
        default:
            memory.add(input);
            echoInput("added: " + input);
            break;
        }

        return false;

    }

    /**
     * Echoes input between horizontal lines for chat formatting.
     *
     * @param input Input to be formatted between horizontal lines.
     */
    public static void echoInput(String input) {

        String indent = "    ";     // 4 spaces

        System.out.println(indent + HORIZONTAL_LINE);
        for (String line : input.split("\n")) {
            System.out.println(indent + " " + line);
        }
        System.out.println(indent + HORIZONTAL_LINE);

        System.out.println();

    }

    /**
     * Lists previous input from user saved in memory.
     *
     * @return Formatted memory string.
     */
    public static String listCommand() {

        int counter = 1;
        String result = "";

        for (String input : memory) {
            result = result.concat(counter + ". " + input + "\n");
            counter++;
        }

        return result;

    }

}
