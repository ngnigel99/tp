package seedu.duke.commands;

import seedu.duke.exceptions.IncorrectNumberOfArgumentsException;
import seedu.duke.exceptions.IndexNotFoundException;
import seedu.duke.parser.Parser;
import seedu.duke.storage.Storage;
import seedu.duke.storage.StorageTasks;
import seedu.duke.ui.Ui;
import static seedu.duke.constants.Messages.DELETED_TASK;

import java.io.IOException;


public class DeleteTaskCommand extends Command {
    private int index;
    private String userInput;

    public DeleteTaskCommand(int index, String userInput) {
        this.index = index;
        this.userInput = userInput;
    }

    @Override
    public void execute(Ui ui, Storage storage) throws IOException,
            IndexNotFoundException, IncorrectNumberOfArgumentsException {
        if (this.index > storage.tasksList.getTaskList().size()) {
            throw new IndexNotFoundException();
        }
        Storage.tasksList.deleteTask(this.index);
        System.out.println(DELETED_TASK);
        StorageTasks.writeTaskList(Storage.tasksList);
    }
}