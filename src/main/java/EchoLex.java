import java.util.Scanner;
import java.util.ArrayList;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class EchoLex {

    /** User Input Text Memory */
    private static ArrayList<Task> memory = new ArrayList<>();

    /** Chat Delimiter: Horizontal Line */
    static final String HORIZONTAL_LINE = "____________________________________________________________";

    /** Tasks Save Location */
    static final String SAVE_DIRECTORY = "./data";
    static final String SAVE_LOCATION = "./data/EchoLex.txt";

    public static void main(String[] args) {

        loadTasks();

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
            try {
                boxInput(markCommand(command, argument));
            } catch (EchoLexException e) {
                boxInput("EchoLex Error: " + e.getMessage());
            }
            saveTasks();
            break;
        case "delete":
            try {
                boxInput(deleteCommand(argument));
            } catch (EchoLexException e) {
                boxInput("EchoLex Error: " + e.getMessage());
            }
            saveTasks();
            break;
        case "todo":
        case "deadline":
        case "event":
            try {
                boxInput(addTask(command, argument, parts));
            } catch (EchoLexException e) {
                boxInput("EchoLex Error: " + e.getMessage());
            }
            saveTasks();
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
    public static String markCommand(String mark, String index) throws EchoLexException {

        try {
            int markIndex = Integer.parseInt(index);
            if (markIndex > memory.size() || markIndex < 0) {
                throw new EchoLexException("The specified task is out of range. Please try again.");
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
        } catch (NumberFormatException e) {
            throw new EchoLexException("Invalid index: " + index);
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
    public static String addTask(String type, String description, String[] options) throws EchoLexException {

        Task task;

        switch (type) {
        case "deadline":
            String by = searchOption(options, "by");
            if (by.isEmpty()) {
                throw new EchoLexException("Deadline option '/by' has not been provided.");
            }
            task = new Deadline(description, Boolean.FALSE, by);
            break;
        case "event":
            String from = searchOption(options, "from");
            if (from.isEmpty()) {
                throw new EchoLexException("Event option '/from' has not been provided.");
            }
            String to = searchOption(options, "to");
            if (to.isEmpty()) {
                throw new EchoLexException("Event option '/to' has not been provided.");
            }
            task = new Event(description, Boolean.FALSE, from, to);
            break;
        default:
            task = new Todo(description, Boolean.FALSE);
        }

        memory.add(task);

        String result = "Got it. I've added this task:\n  " + task.toString();
        result = result.concat("\nNow you have " + memory.size() + " tasks in the list.");
        return result;

    }

    /**
     * Delete tasks.
     *
     * @param index Index to be deleted (to be converted to integer).
     * @return Result of task deletion.
     */
    public static String deleteCommand(String index) throws EchoLexException {

        int deleteIndex = Integer.parseInt(index);
        if (deleteIndex > memory.size() || deleteIndex < 0) {
            throw new EchoLexException("The specified task is out of range. Please try again.");
        } else {
            String result =  "Noted. I've removed this task:\n  " + memory.get(deleteIndex - 1).toString();
            memory.remove(deleteIndex - 1);
            result += "\nNow you have " + memory.size() + " tasks in the list.";
            return result;
        }

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

    /**
     * Load tasks from ./data/EchoLex.txt on the hard disk.
     */
    public static void loadTasks() {

        memory.clear();     // just in case loadTasks() is called while memory is populated

        File file = new File(SAVE_LOCATION);
        File directory = file.getParentFile();
        if (directory != null && !directory.exists()) {
            directory.mkdirs(); // Creates missing parent "data" directory
        }
        if (!file.exists()) {   // Create the save file if it does not exist
            try {
                file.createNewFile();
            } catch (IOException e) {
                boxInput("EchoLex Error: " + e.getMessage());
            }
            return;
        }

        // Loading task entries from save file
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_LOCATION))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                switch (parts[0]) {
                case "T":
                    memory.add(new Todo(parts[2], (parts[1].equals("1"))));
                    break;
                case "D":
                    memory.add(new Deadline(parts[2], (parts[1].equals("1")), parts[3]));
                    break;
                case "E":
                    memory.add(new Event(parts[2], (parts[1].equals("1")), parts[3], parts[4]));
                    break;
                default:
                    break;
                }
            }
        } catch (IOException e) {
            boxInput("EchoLex Error: " + e.getMessage());
        }

    }

    /**
     * Save tasks in ./data/EchoLex.txt on the hard disk.
     */
    public static void saveTasks() {

        File file = new File(SAVE_LOCATION);
        File directory = file.getParentFile();
        if (directory != null && !directory.exists()) {
            directory.mkdirs(); // Creates missing parent "data" directory
        }
        if (!file.exists()) {   // Create the save file if it does not exist
            try {
                file.createNewFile();
            } catch (IOException e) {
                boxInput("EchoLex Error: " + e.getMessage());
            }
        }

        // Writing task entries to save file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_LOCATION))) {
            for (Task task : memory) {
                writer.write(task.saveFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            boxInput("EchoLex Error: " + e.getMessage());
        }

    }

}
