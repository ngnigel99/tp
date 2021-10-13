package seedu.duke.commands;

import seedu.duke.exceptions.IncorrectNumberOfArgumentsException;
import seedu.duke.exceptions.InvalidDateMonthException;
import seedu.duke.logger.ClickLogger;
import seedu.duke.parser.schedule.ParserSchedule;
import seedu.duke.storage.Storage;
import seedu.duke.task.Task;
import seedu.duke.task.TaskList;
import seedu.duke.ui.Ui;
import seedu.duke.schedule.Schedule;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.logging.Level;

import static seedu.duke.constants.Messages.MONTH_LOWER_LIMIT;
import static seedu.duke.constants.Messages.MONTH_UPPER_LIMIT;
import static seedu.duke.constants.Messages.TOTAL_SIZE;
import static seedu.duke.constants.Messages.DELIMETER_DATE;

public class DisplayCalendarCommand extends Command {

    private int year;
    private int month;
    private YearMonth inputYearMonth;
    private String[] yearMonthArguments;
    private ArrayList<ArrayList<String>> calendarTasks = new ArrayList<>(TOTAL_SIZE);

    public DisplayCalendarCommand(String input) {
        while (calendarTasks.size() != TOTAL_SIZE) {
            calendarTasks.add(new ArrayList<>());
        }
        try {
            this.yearMonthArguments = ParserSchedule.parseCalendarCommand(input);
            this.year = Integer.parseInt(yearMonthArguments[1]);
            this.month = Integer.parseInt(yearMonthArguments[0]);
            if ((month < MONTH_LOWER_LIMIT || month > MONTH_UPPER_LIMIT)) {
                throw new InvalidDateMonthException("The month has to be a value between 01-12 !");
            }
            this.inputYearMonth = YearMonth.of(year, month);
        } catch (IndexOutOfBoundsException | NumberFormatException
                | InvalidDateMonthException | IncorrectNumberOfArgumentsException c) {
            ClickLogger.getNewLogger().log(Level.WARNING, "Calendar display failed...");
            Ui.printInvalidCalendarInput();
        }
    }

    private void parseTaskList(TaskList taskList) {
        for (Task task : taskList.getTaskList()) {
            String description = task.getDescription();
            String[] dateSplit = task.getDate().split(DELIMETER_DATE);
            if (this.month == Integer.parseInt(dateSplit[1])
                    && this.year == Integer.parseInt(dateSplit[2])) {
                int day = Integer.parseInt(dateSplit[0]);
                this.calendarTasks.get(day).add(description);
            }
        }
    }

    @Override
    public void execute(Ui ui, Storage storage) {
        Ui.printCalenderTitle(inputYearMonth);
        parseTaskList(storage.tasksList);
        Schedule.displayCalendar(inputYearMonth, calendarTasks);
    }
}