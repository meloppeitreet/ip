package echolex.command;

import java.time.LocalDate;
import java.util.HashMap;

import echolex.error.EchoLexException;
import echolex.task.Deadline;
import echolex.task.Event;
import echolex.task.Task;
import echolex.task.TaskList;
import echolex.utility.Parser;

/**
 * Represents a command to view schedule of tasks from task list.
 */
public class ScheduleCommand extends Command {

    /**
     * Constructs a ScheduleCommand object.
     *
     * @param command The command type.
     * @param argument The argument for the command.
     * @param options Additional options for the command.
     */
    public ScheduleCommand(String command, String argument, HashMap<String, String> options) {
        super(command, argument, options);
    }

    /**
     * Executes the command on the given task list.
     * Lists tasks scheduled for a specified time.
     *
     * @param tasks The list of tasks.
     * @return Results of task list formatting.
     */
    @Override
    public String execute(TaskList tasks) {

        String result;

        if (options.containsKey("on")) {
            result = daySchedule(tasks);
        } else {
            result = todaySchedule(tasks);
        }

        return result;

    }

    /**
     * Gets schedule summary from today onwards.
     *
     * @param tasks The list of tasks.
     * @return Results of today's schedule formatting.
     */
    public String todaySchedule(TaskList tasks) {

        LocalDate todayDate = LocalDate.now();
        LocalDate tomorrowDate = todayDate.plusDays(1);
        LocalDate threeDaysLaterDate = todayDate.plusDays(3);
        LocalDate sevenDaysLaterDate = todayDate.plusDays(7);

        TaskList todayEventSchedule = eventScheduleIntersect(tasks, todayDate, todayDate);
        TaskList threeDaysEventSchedule = eventScheduleIntersect(tasks, tomorrowDate, threeDaysLaterDate);
        TaskList sevenDaysDeadlineSchedule = deadlinesBefore(tasks, sevenDaysLaterDate);

        String message = "Here is your Event Schedule for Today:\n";
        message += listSchedule(todayEventSchedule, "No Events! Have a relaxing day!");
        message += "Here is your Event Schedule for the next 3 days:\n";
        message += listSchedule(threeDaysEventSchedule, "No Events! Have a relaxing day!");
        message += "Here are your deadlines in the next 7 days:\n";
        message += listSchedule(sevenDaysDeadlineSchedule, "No Deadlines! Have a relaxing time!");

        return message;

    }

    /**
     * Gets schedule for a specific day.
     *
     * @param tasks The list of tasks.
     * @return Results of specified date's schedule formatting.
     */
    public String daySchedule(TaskList tasks) {

        String message = "";
        String date = options.get("on");
        try { // parse "by" date
            LocalDate byDate = Parser.parseDate(date).toLocalDate();
            TaskList dateSchedule = eventScheduleIntersect(tasks, byDate, byDate);
            message += "Here are your events on " + byDate.toString() + ":\n";
            message += listSchedule(dateSchedule, "No Events! Have a relaxing day!");
            return message;
        } catch (EchoLexException e) {
            return "EchoLex Exception: " + e.getMessage();
        }

    }

    /**
     * Gets events between 2 specified dates.
     *
     * @param tasks The list of tasks.
     * @param from The start date.
     * @param to The end date.
     * @return List of events between dates.
     */
    public TaskList eventScheduleIntersect(TaskList tasks, LocalDate from, LocalDate to) {

        TaskList intersection = new TaskList();

        for (Task task : tasks) {
            if (task instanceof Event event) {

                LocalDate eventFrom = event.getFrom().toLocalDate();
                LocalDate eventTo = event.getTo().toLocalDate();

                boolean hasIntersection = !eventTo.isBefore(from) && !eventFrom.isAfter(to);

                if (hasIntersection) {
                    intersection.add(task);
                }

            }
        }

        return intersection;

    }

    /**
     * Gets deadlines before a specified date.
     *
     * @param tasks The list of tasks.
     * @param by The deadline date.
     * @return List of deadlines before date.
     */
    public TaskList deadlinesBefore(TaskList tasks, LocalDate by) {

        TaskList before = new TaskList();

        for (Task task : tasks) {
            if (task instanceof Deadline deadline) {

                LocalDate deadlineBy = deadline.getBy().toLocalDate();

                boolean isBefore = deadlineBy.isBefore(by) || deadlineBy.isEqual(by);

                if (isBefore) {
                    before.add(task);
                }

            }
        }

        return before;

    }

    /**
     * Formats list of tasks.
     *
     * @param schedule The list of tasks.
     * @param noEventMessage The message if there are no tasks.
     * @return Results of schedule formatting.
     */
    public String listSchedule(TaskList schedule, String noEventMessage) {

        int counter = 1;
        String result = "";

        if (schedule.isEmpty()) {
            return noEventMessage + "\n\n";
        }

        for (Task task : schedule) {
            result = result.concat(counter + "." + task.toString() + "\n");
            counter++;
        }
        return result + "\n";
    }

}
