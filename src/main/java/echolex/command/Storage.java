package echolex.command;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import echolex.error.EchoLexException;
import echolex.task.Deadline;
import echolex.task.Event;
import echolex.task.Task;
import echolex.task.TaskList;
import echolex.task.Todo;

/**
 * Handles loading and saving of tasks to a specified file.
 */
public class Storage {

    private String filePath;

    /**
     * Constructs a Storage object with the given file path.
     *
     * @param filePath The path of the file to store task data.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads task list from specified filePath.
     * If the file does not exist, it creates an empty task list.
     *
     * @return TaskList object.
     */
    public ArrayList<Task> load() throws EchoLexException {

        ArrayList<Task> tasks = new ArrayList<>();

        checkFilePath();

        // Loading task entries from save file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = loadTaskFromLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            throw new EchoLexException(e.getMessage());
        }

        return tasks;

    }

    /**
     * Loads a specific task from a line in filePath.
     *
     * @param line Single line from filePath.
     * @return Task object.
     */
    public Task loadTaskFromLine(String line) throws EchoLexException {

        String[] parts = line.split(" \\| ");
        switch (parts[0]) {
        case "T":
            return new Todo(parts[2], (parts[1].equals("1")));
        case "D":
            try {
                LocalDateTime by = Parser.parseDate(parts[3]);
                return new Deadline(parts[2], (parts[1].equals("1")), by);
            } catch (EchoLexException e) {
                throw new EchoLexException(e.getMessage());
            }
        case "E":
            try {
                LocalDateTime from = Parser.parseDate(parts[3]);
                LocalDateTime to = Parser.parseDate(parts[4]);
                return new Event(parts[2], (parts[1].equals("1")), from, to);
            } catch (EchoLexException e) {
                throw new EchoLexException(e.getMessage());
            }
        default:
            break;
        }

        return null;

    }

    /**
     * Saves the given task list to the specified filePath.
     *
     * @param tasks The task list to save.
     */
    public void save(TaskList tasks) throws EchoLexException {

        checkFilePath();

        // Writing task entries to save file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.write(task.saveFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new EchoLexException(e.getMessage());
        }

    }

    /**
     * Checks if filePath exists.
     * Otherwise, creates the necessary directories / files
     */
    public void checkFilePath() throws EchoLexException {

        File file = new File(filePath);
        File directory = file.getParentFile();
        if (directory != null && !directory.exists()) {
            directory.mkdirs(); // Creates missing parent "data" directory
        }
        if (!file.exists()) { // Create the save file if it does not exist
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new EchoLexException(e.getMessage());
            }
        }

    }

}
