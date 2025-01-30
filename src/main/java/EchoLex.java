import java.util.Scanner;
import java.util.ArrayList;

public class EchoLex {

    /** Chat Delimiter: Horizontal Line */
    static final String HORIZONTAL_LINE = "____________________________________________________________";
    /** User Input Text Memory */
    private static ArrayList<Task> memory = new ArrayList<>();

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

        // Split Input into Command and Arguments
        String[] parts = input.split(" ");
        String command = parts[0];
        String argument = "";
        if (parts.length > 1) {
            argument = parts[1];
        }

        // Execute Command
        switch (command) {
        case "list":
            echoInput(listCommand());
            break;
        case "mark":
            int markIndex = Integer.parseInt(argument);
            if (markIndex < memory.size()) {
                Task markEntry = memory.get(markIndex - 1);
                markEntry.markDone();
                echoInput("Nice! I've marked this task as done:\n  [X] " + markEntry.getDescription());
            } else {
                echoInput("That task is out of range! Please try again.");
            }
            break;
        case "unmark":
            int unmarkIndex = Integer.parseInt(argument);
            if (unmarkIndex < memory.size()) {
                Task unmarkEntry = memory.get(unmarkIndex - 1);
                unmarkEntry.unmarkDone();
                echoInput("OK, I've marked this task as not done yet:\n  [ ] " + unmarkEntry.getDescription());
            } else {
                echoInput("That task is out of range! Please try again.");
            }
            break;
        case "bye":
            echoInput("Bye. Hope to see you again soon!");
            return true;
        default:
            memory.add(new Task(input));
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

        for (Task input : memory) {
            result = result.concat(counter + ".[" + input.getStatusIcon() + "] " + input.getDescription() + "\n");
            counter++;
        }

        return result;

    }

}
