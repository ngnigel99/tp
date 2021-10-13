package seedu.duke.commands;

import seedu.duke.exceptions.InvalidArgumentsException;
import seedu.duke.storage.Storage;
import seedu.duke.storage.StorageTasks;
import seedu.duke.task.Task;
import seedu.duke.task.TaskList;
import seedu.duke.ui.Ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ListTasksCommand extends Command {

    public ListTasksCommand() {
    }

    @Override
    public void execute(Ui ui, Storage storage) throws IOException {
        TaskList tasks = StorageTasks.readTaskList();
        Ui.printTaskList(tasks.getTaskList());
    }
}