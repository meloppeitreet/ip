package echolex;

import echolex.command.Command;
import echolex.utility.Parser;
import echolex.utility.Storage;
import echolex.utility.Ui;
import echolex.error.EchoLexException;
import echolex.task.TaskList;

public class EchoLex {

    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

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

    public static void main(String[] args) {
        new EchoLex("./data/EchoLex.txt").run();
    }

}
