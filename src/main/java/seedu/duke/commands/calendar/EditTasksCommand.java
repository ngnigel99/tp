package seedu.duke.commands.calendar;

import seedu.duke.commands.Command;
import seedu.duke.exceptions.calendar.DuplicateTaskException;
import seedu.duke.exceptions.calendar.IncorrectNumberOfArgumentsException;
import seedu.duke.exceptions.calendar.InvalidDateException;
import seedu.duke.exceptions.calendar.CalendarIndexNotFoundException;
import seedu.duke.parser.schedule.ParserSchedule;
import seedu.duke.storage.Storage;
import seedu.duke.storage.StorageTasks;
import seedu.duke.schedule.task.Task;
import seedu.duke.schedule.task.Todo;
import seedu.duke.ui.Ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static seedu.duke.constants.Messages.INDEX_TODO_DESCRIPTION;
import static seedu.duke.constants.Messages.INDEX_TODO_DATE;

//@@author swatimahadevan

/**
 * Class to execute editing of todo task.
 */
public class EditTasksCommand extends Command {
    private int index;
    public static final String MESSAGE_EDITED_TASK = "Edited Task!";
    public static final String MESSAGE_ENTER_TASK_COMMAND = "Enter the entire todo command "
            + "with the desired description and date that you "
            + "want to replace in place of the current task at index ";


    /**
     * Class constructor providing syntax for the HelpCommand.
     */
    public EditTasksCommand() {
        syntax = "calendar edit [TASK_INDEX]";

    }

    /**
     * Constructor for EditTasksCommand class.
     *
     * @param index index of task to be deleted
     */
    public EditTasksCommand(int index) {
        this.index = index;
        helpMessage = "Delete task from calendar";
        syntax = "calendar delete TASK_INDEX";
    }

    /**
     * Executed edit task command.
     *
     * @param ui      The component of CLICK that deals with the interaction with the user.
     * @param storage The component of CLICK that deals with loading tasks from the file and saving tasks in the file.
     * @throws IOException in case of writing to save file error.
     * @throws CalendarIndexNotFoundException if index is not found.
     * @throws IncorrectNumberOfArgumentsException in case of wrong number of arguments.
     */
    @Override
    public void execute(Ui ui, Storage storage) throws IOException, IncorrectNumberOfArgumentsException,
            CalendarIndexNotFoundException, InvalidDateException, DuplicateTaskException {
        ui.printLine();
        if (this.index > storage.tasksList.getTaskList().size()) {
            throw new CalendarIndexNotFoundException();
        }
        ui.printMessage(MESSAGE_ENTER_TASK_COMMAND + this.index);
        Scanner in = new Scanner(System.in);
        String followUpInput = in.nextLine();
        ArrayList<String> arguments = ParserSchedule.parseTodoCommand(followUpInput);
        String description = arguments.get(INDEX_TODO_DESCRIPTION).trim();
        String date = arguments.get(INDEX_TODO_DATE);
        Task task = new Todo(description, date);
        AddTodoCommand.checkIfDuplicate(task);
        AddTodoCommand.checkIfDateValid(date);
        Storage.tasksList.editTask(this.index, description, date);
        ui.printMessage(MESSAGE_EDITED_TASK);
        ui.printLine();
        StorageTasks.writeTaskList(Storage.tasksList);
    }
}