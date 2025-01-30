import java.util.Scanner;
import java.util.ArrayList;

public class EchoLex {

    /** User Input Text Memory */
    private static ArrayList<Task> memory = new ArrayList<>();

    /** Chat Delimiter: Horizontal Line */
    static final String HORIZONTAL_LINE = "____________________________________________________________";

    public static void main(String[] args) {

        boxInput("Hello! I'm EchoLex" + "\n" + "What can I do for you?");

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
        String[] parts = input.split("/");

        // Main Command
        String[] main = parts[0].split(" ", 2);
        String command = main[0];
        String argument = "";
        if (main.length > 1) {
            argument = main[1];
        }

        // Execute Command
        switch (command) {
        case "list":
            boxInput(listCommand());
            break;
        case "mark":
        case "unmark":
            boxInput(markCommand(command, argument));
            break;
        case "todo":
        case "deadline":
        case "event":
            boxInput(addTask(command, argument, parts));
            break;
        case "bye":
            boxInput("Bye. Hope to see you again soon!");
            return true;
        default:
            boxInput("Sorry, I did not understand that command.");
            break;
        }

        return false;

    }

    /**
     * Echoes input between horizontal lines for chat formatting.
     *
     * @param input Input to be formatted between horizontal lines.
     */
    public static void boxInput(String input) {

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
     * @return Formatted memory list string.
     */
    public static String listCommand() {

        int counter = 1;
        String result = "Here are the tasks in your list:\n";

        for (Task input : memory) {
            result = result.concat(counter + "." + input.toString() + "\n");
            counter++;
        }

        return result;

    }

    /**
     * Marks or unmarks tasks as done.
     *
     * @param mark Marking/unmarking.
     * @param index Index to be marked (to be converted to integer).
     * @return Result of marking/unmarking.
     */
    public static String markCommand(String mark, String index) {

        String result = "";

        int markIndex = Integer.parseInt(index);
        if (markIndex > memory.size()) {
            return "That task is out of range! Please try again.";
        } else {
            Task markEntry = memory.get(markIndex - 1);
            if (mark.equals("mark")) {
                markEntry.markDone();
                return "Nice! I've marked this task as done:\n  " + markEntry.toString();
            } else {
                markEntry.unmarkDone();
                return "OK, I've marked this task as not done yet:\n  " + markEntry.toString();
            }
        }

    }

    /**
     * Add Tasks.
     *
     * @param type "todo"/"deadline"/"event".
     * @param description Task description.
     * @param options Deadline time or event duration.
     * @return Task created message.
     */
    public static String addTask(String type, String description, String[] options) {

        Task task;

        switch (type) {
        case "deadline":
            String by = searchOption(options, "by");
            if (by.isEmpty()) {
                return "Error: Deadline option '/by' has not been provided.";
            }
            task = new Deadline(description, by);
            break;
        case "event":
            String from = searchOption(options, "from");
            if (from.isEmpty()) {
                return "Error: Event option '/from' has not been provided.";
            }
            String to = searchOption(options, "to");
            if (to.isEmpty()) {
                return "Error: Event option '/to' has not been provided.";
            }
            task = new Event(description, from, to);
            break;
        default:
            task = new Todo(description);
        }

        memory.add(task);

        String result = "Got it. I've added this task:\n  " + task.toString();
        result = result.concat("\nNow you have " + memory.size() + " tasks in the list.");
        return result;

    }

    /**
     * Search for an option and return its argument.
     *
     * @param options List of options previously split by "/".
     * @param search Option to search for.
     * @return Argument for Option.
     */
    public static String searchOption(String[] options, String search) {

        for (String option : options) {
            String[] parts = option.split(" ", 2);
            if (parts[0].equals(search)) {
                return parts[1];
            }
        }
        return "";

    }

}
