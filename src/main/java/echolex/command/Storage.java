package echolex.command;

import echolex.error.EchoLexException;
import echolex.task.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

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

        File file = new File(filePath);
        File directory = file.getParentFile();
        if (directory != null && !directory.exists()) {
            directory.mkdirs(); // Creates missing parent "data" directory
        }
        if (!file.exists()) {   // Create the save file if it does not exist
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new EchoLexException(e.getMessage());
            }
            assert file.exists() : "File " + filePath + " does not exist";
            return tasks;
        }

        // Loading task entries from save file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                switch (parts[0]) {
                case "T":
                    tasks.add(new Todo(parts[2], (parts[1].equals("1"))));
                    break;
                case "D":
                    try {
                        LocalDateTime by = Parser.parseDate(parts[3]);
                        tasks.add(new Deadline(parts[2], (parts[1].equals("1")), by));
                    } catch (EchoLexException e) {
                        throw new EchoLexException(e.getMessage());
                    }
                    break;
                case "E":
                    try {
                        LocalDateTime from = Parser.parseDate(parts[3]);
                        LocalDateTime to = Parser.parseDate(parts[4]);
                        tasks.add(new Event(parts[2], (parts[1].equals("1")), from, to));
                    } catch (EchoLexException e) {
                        throw new EchoLexException(e.getMessage());
                    }
                    break;
                default:
                    break;
                }
            }
        } catch (IOException e) {
            throw new EchoLexException(e.getMessage());
        }

        return tasks;

    }

    /**
     * Saves the given task list to the specified filePath.
     *
     * @param tasks The task list to save.
     */
    public void save(TaskList tasks) throws EchoLexException {

        File file = new File(filePath);
        File directory = file.getParentFile();
        if (directory != null && !directory.exists()) {
            directory.mkdirs(); // Creates missing parent "data" directory
        }
        if (!file.exists()) {   // Create the save file if it does not exist
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new EchoLexException(e.getMessage());
            }
        }

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

}
