package echolex;

import echolex.command.Command;
import echolex.error.EchoLexException;
import echolex.task.TaskList;
import echolex.utility.Parser;
import echolex.utility.Storage;
import echolex.utility.Ui;

/**
 * Represents the main class for the EchoLex application, responsible for running the application,
 * processing user input, and interacting with the underlying components such as storage, tasks,
 * and the user interface.
 */
public class EchoLex {

    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Constructs a new EchoLex instance with the specified file path.
     * Initializes the user interface and storage, and attempts to load the task list from the file.
     * If loading fails, an error message is displayed, and a new empty task list is created.
     *
     * @param filePath the file path where tasks are saved and loaded from
     */
    public EchoLex(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (EchoLexException e) {
            ui.boxInput("EchoLex Error: " + e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Processes the user's input, parses it into a command, and executes it.
     * Updates the task list and saves it to storage after each command execution.
     * If the command indicates an exit, returns a goodbye message.
     *
     * @param input the user's input command
     * @return a response message based on the command's result or an error message
     */
    public String getResponse(String input) {

        if (input.isEmpty()) {
            return ui.getWelcome();
        }

        try {
            Command c = Parser.parse(input);
            String reply = c.execute(tasks);
            storage.save(tasks);
            if (c.isExit()) {
                return ui.getGoodbye();
            }
            return reply;
        } catch (EchoLexException e) {
            return e.getMessage();
        }

    }

    /**
     * Starts and runs the EchoLex application by displaying the welcome message,
     * reading and processing user commands in a loop, and displaying the corresponding responses.
     * The loop continues until the exit command is given, at which point the goodbye message is displayed.
     */
    public void run() {

        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                ui.boxInput(c.execute(tasks));
                storage.save(tasks);
                isExit = c.isExit();
            } catch (EchoLexException e) {
                ui.boxInput(e.getMessage());
            }
        }
        ui.showGoodbye();

    }

    /**
     * The main method that starts the EchoLex application. Initializes the application
     * with the given file path and begins the running process.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new EchoLex("./data/EchoLex.txt").run();
    }

}
