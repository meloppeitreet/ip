import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Storage {

    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasklist from specified filePath.
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
     * Saves tasklist to specified filePath.
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
