package seedu.duke.commands;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import seedu.duke.exceptions.DuplicateTaskException;
import seedu.duke.exceptions.InvalidDateMonthException;
import seedu.duke.storage.Storage;
import seedu.duke.storage.StorageTasks;
import seedu.duke.ui.Ui;
import seedu.duke.task.Task;
import seedu.duke.task.Todo;

import static seedu.duke.constants.Messages.INDEX_TODO_DESCRIPTION;
import static seedu.duke.constants.Messages.INDEX_TODO_DATE;

//@author swatim
public class AddTodoCommand extends Command {

    private ArrayList<String> arguments;

    public AddTodoCommand(ArrayList<String> arguments) {
        super();
        this.arguments = arguments;
    }

    public boolean isValid(String todoDateStringFormat) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(todoDateStringFormat);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public void execute(Ui ui, Storage storage) throws IOException,
            ParseException, InvalidDateMonthException, DuplicateTaskException {
        String description = arguments.get(INDEX_TODO_DESCRIPTION).trim();
        String todoDateStringFormat = arguments.get(INDEX_TODO_DATE);
        Task task = new Todo(description, todoDateStringFormat);
        for (int i = 0; i < storage.tasksList.getTaskList().size(); i++) {
            if (task.getDescription().equals(storage.tasksList.getTaskList().get(i).getDescription())) {
                throw new DuplicateTaskException();
            }
        }
        if (!isValid(todoDateStringFormat)) {
            throw new InvalidDateMonthException("Invalid date given, use format DD-MM-YYYY!");
        }
        storage.tasksList.addTask(task);
        Ui.printTaskAddedMessage();
        StorageTasks.writeTaskList(storage.tasksList);
    }

}
