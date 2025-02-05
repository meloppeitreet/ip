package echolex;

import echolex.command.Command;
import echolex.command.Parser;
import echolex.command.Storage;
import echolex.command.Ui;
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

    public void run() {

        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
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
