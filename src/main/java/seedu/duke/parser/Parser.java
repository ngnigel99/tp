package seedu.duke.parser;

import seedu.duke.commands.Command;
import seedu.duke.commands.AddEntryCommand;
import seedu.duke.commands.ListJournalCommand;
import seedu.duke.commands.AddFoodCommand;
import seedu.duke.commands.AddModuleCommand;
import seedu.duke.commands.AddNoteCommand;
import seedu.duke.commands.AddTodoCommand;
import seedu.duke.commands.ClearFoodCommand;
import seedu.duke.commands.DeleteTaskCommand;
import seedu.duke.commands.DeleteModuleCommand;
import seedu.duke.commands.DisplayCalendarCommand;
import seedu.duke.commands.ExitCommand;
import seedu.duke.commands.HelpCommand;
import seedu.duke.commands.ListFoodCommand;
import seedu.duke.commands.ListModuleCommand;
import seedu.duke.commands.ListTasksCommand;
import seedu.duke.exceptions.IncorrectNumberOfArgumentsException;
import seedu.duke.exceptions.ArgumentsNotFoundException;
import seedu.duke.exceptions.ClickException;
import seedu.duke.exceptions.IllegalDateTimeException;
import seedu.duke.exceptions.WrongDividerOrderException;
import seedu.duke.exceptions.StorageException;
import seedu.duke.exceptions.IllegalFoodParameterException;
import seedu.duke.exceptions.EmptyJournalArgumentException;
import seedu.duke.exceptions.IncorrectJournalArgumentException;

import seedu.duke.food.FoodRecord;
import seedu.duke.constants.Messages;
import seedu.duke.module.Module;
import seedu.duke.parser.schedule.ParserSchedule;
import seedu.duke.ui.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static seedu.duke.constants.CommandConstants.COMMAND_ADD_ENTRY;
import static seedu.duke.constants.CommandConstants.COMMAND_ADD_NOTE;
import static seedu.duke.constants.CommandConstants.COMMAND_JOURNAL_LIST;
import static seedu.duke.constants.CommandConstants.COMMAND_CALENDAR;
import static seedu.duke.constants.CommandConstants.COMMAND_EXIT;
import static seedu.duke.constants.CommandConstants.COMMAND_FOOD;
import static seedu.duke.constants.CommandConstants.COMMAND_HElP;
import static seedu.duke.constants.CommandConstants.COMMAND_LIST_TASKS;
import static seedu.duke.constants.CommandConstants.COMMAND_MODULE;
import static seedu.duke.constants.CommandConstants.COMMAND_NOTE;
import static seedu.duke.constants.CommandConstants.COMMAND_SUFFIX_ADD;
import static seedu.duke.constants.CommandConstants.COMMAND_SUFFIX_CLEAR;
import static seedu.duke.constants.CommandConstants.COMMAND_SUFFIX_DELETE;
import static seedu.duke.constants.CommandConstants.COMMAND_SUFFIX_LIST;
import static seedu.duke.constants.CommandConstants.COMMAND_TODO;
import static seedu.duke.constants.Messages.EMPTY_STRING;
import static seedu.duke.constants.Messages.CALENDAR_INVALID_ARGS;
import static seedu.duke.constants.Messages.CALENDAR_DELETE_INVALID_ARGS;

//@@author nvbinh15
public class Parser {

    static final String INPUT_DATE_TIME_FORMAT = "dd-MM-yyyy HHmm";
    static final String OUTPUT_DATE_TIME_FORMAT = "MMM dd yyyy HH:mm";

    static final int FOOD_MINIMUM_PARAMETER = 4; // tags {/n /c} + 2 inputs {name, calorie}
    static final String FOOD_NAME_DIVIDER = "n/";
    static final String FOOD_CALORIE_DIVIDER = "c/";

    /**
     * Converts date and time in string format to a LocalDateTime object.
     *
     * @param string Date and time in string format.
     * @return A LocalDateTime object that represents the given date and time.
     * @throws IllegalDateTimeException If the input date time is invalid.
     */
    public static LocalDateTime stringToDateTime(String string) throws IllegalDateTimeException {
        try {
            return LocalDateTime.parse(string, DateTimeFormatter.ofPattern(INPUT_DATE_TIME_FORMAT));
        } catch (Exception e) {
            throw new IllegalDateTimeException();
        }
    }

    /**
     * Converts date and time in LocalDateTime format to a string.
     *
     * @param dateTime A LocalDateTime object that represents the given date and time.
     * @return The string representation of the given LocalDateTime object.
     */
    public static String dateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(OUTPUT_DATE_TIME_FORMAT));
    }

    /**
     * Formats the user's input date time.
     *
     * @param rawDateTime The raw date time from user's input.
     * @return The string representation of other form of the given date time.
     * @throws IllegalDateTimeException If the input date time is invalid.
     */
    public static String formatDateTime(String rawDateTime) throws IllegalDateTimeException {
        try {
            return dateTimeToString(stringToDateTime(rawDateTime));
        } catch (IllegalDateTimeException e) {
            throw new IllegalDateTimeException();
        }
    }

    /**
     * Splits raw user input into command word and command arguments.
     *
     * @param userInput Raw input from user.
     * @return a String array of size 2 including the command type and the arguments.
     */
    public static String[] splitCommandAndArgs(String userInput) {
        String[] tokens = userInput.trim().split("\\s+", 2);
        String command = tokens[0];
        if (tokens.length == 2) {
            return tokens;
        } else {
            return new String[] {command, EMPTY_STRING};
        }
    }

    /**
     * Parses a line of text to a food record.
     * Assumes that both name, calories field not null.
     * Note format: [NAME] | [CALORIES]
     * @param readLine line of text to read
     * @return FoodRecord food record object
     */
    public static FoodRecord parseFoodSavedListToRecord(String readLine) {
        String[] nameCalories = readLine.split("\\|");
        return new FoodRecord(nameCalories[0], Integer.parseInt(nameCalories[1]));
    }

    /**
     * Returns a command to be executed based on the raw input from user.
     *
     * @param userInput The raw input from user.
     * @return The command to be executed.
     * @throws ClickException If there is an exception to type DukeException occurs.
     */
    public Command parseCommand(String userInput)
            throws ClickException, IncorrectNumberOfArgumentsException, IncorrectJournalArgumentException,
            EmptyJournalArgumentException {
        final String[] commandTypeAndParams = splitCommandAndArgs(userInput);
        assert commandTypeAndParams.length == 2;
        final String commandType = commandTypeAndParams[0];
        final String commandArgs = commandTypeAndParams[1];

        switch (commandType) {
        case COMMAND_EXIT:
            return new ExitCommand();
        case COMMAND_CALENDAR:
            String[] todoArguments = commandArgs.split(" ");
            switch (todoArguments[0]) {
            case COMMAND_LIST_TASKS:
                return new ListTasksCommand();
            case COMMAND_TODO:
                ArrayList<String> arguments = ParserSchedule.parseTodoCommand(userInput);
                return new AddTodoCommand(arguments);
            case COMMAND_SUFFIX_DELETE:
                if (todoArguments.length == 1) {
                    throw new IncorrectNumberOfArgumentsException(CALENDAR_DELETE_INVALID_ARGS);
                }
                int indexOfTaskToBeDeleted = Integer.parseInt(todoArguments[1]);
                return new DeleteTaskCommand(indexOfTaskToBeDeleted, userInput);
            case EMPTY_STRING:
                throw new IncorrectNumberOfArgumentsException(CALENDAR_INVALID_ARGS);
            default:
                return new DisplayCalendarCommand(userInput);
            }
        case COMMAND_FOOD:
            String[] foodArgs = commandArgs.split(" ");
            switch (foodArgs[0]) {
            case COMMAND_SUFFIX_ADD:
                String nameCalorieInput = userInput.split("food add")[1];
                return new AddFoodCommand(nameCalorieInput);
            case COMMAND_SUFFIX_CLEAR:
                return new ClearFoodCommand();
            case COMMAND_SUFFIX_LIST:
                return new ListFoodCommand();
            default:
                throw new IllegalArgumentException(Messages.LIST_PROPER_FEATURE +  COMMAND_FOOD);
            }
        case COMMAND_NOTE:
            String[] noteArguments = commandArgs.split(" ");
            switch (noteArguments[0]) {
            case COMMAND_ADD_NOTE:
                return new AddNoteCommand(userInput);
            case COMMAND_ADD_ENTRY:
                return new AddEntryCommand(userInput);
            case COMMAND_JOURNAL_LIST:
                return new ListJournalCommand();
            case "":
                throw new EmptyJournalArgumentException();
            default:
                throw new IncorrectJournalArgumentException();
            }
        case COMMAND_MODULE:
            String[] moduleCommandAndArgs = splitCommandAndArgs(commandArgs);
            switch (moduleCommandAndArgs[0]) {
            case COMMAND_SUFFIX_ADD:
                return new AddModuleCommand(moduleCommandAndArgs[1]);
            case COMMAND_SUFFIX_LIST:
                return new ListModuleCommand();
            case COMMAND_SUFFIX_DELETE:
                return new DeleteModuleCommand(moduleCommandAndArgs[1]);
            default:
                throw new ClickException();
            }
        case COMMAND_HElP:
            return new HelpCommand(commandArgs);
        default:
            throw new ClickException();
        }
    }

    //@author ngnigel99
    public static int getWordCount(String input) {
        return input.trim().split(" ").length;
    }

    /**
     * Returns a string representing a data block, separated by 2 dividers.
     * For example, n/ [DATA_1] c/ [DATA_2], if n/ is passed, DATA_1 would be returned.
     *
     * @param input input that contains dividers and data
     * @param dividerBefore divider before the data
     * @param dividerAfter divider after the data
     * @return data Array List of data extracted, containing both DATA_1 and DATA_2
     *
     * @author ngnigel99
     */
    public static String[] getData(String input,
                                   String dividerBefore,
                                   String dividerAfter)
            throws WrongDividerOrderException, ArgumentsNotFoundException {
        if (!(input.contains(dividerBefore) && input.contains(dividerAfter))) {
            throw new ArgumentsNotFoundException();
        }
        if (input.indexOf(dividerBefore) >= input.indexOf(dividerAfter)) {
            throw new WrongDividerOrderException();
        }
        assert input.indexOf(dividerBefore) < input.indexOf(dividerAfter) : "";
        String afterFirstDivider  = input.split(dividerBefore)[1];
        String dataFirst          = afterFirstDivider.split(dividerAfter)[0].trim();
        String afterSecondDivider = afterFirstDivider.split(dividerAfter)[1];
        String dataSecond         = afterSecondDivider.trim();

        return  new String[] {dataFirst, dataSecond};

    }

    /**
     * Parses a string into a food item.
     * current implementation: {food add} n/ [NAME] c/ [CALORIES].
     * @author ngnigel99
     */
    public static FoodRecord parseFoodRecord(String input) throws IllegalFoodParameterException,
            ArgumentsNotFoundException {
        try {
            if (getWordCount(input) < FOOD_MINIMUM_PARAMETER) {
                throw new IllegalFoodParameterException();
            }
            String[] data = getData(input, FOOD_NAME_DIVIDER, FOOD_CALORIE_DIVIDER);
            String name = data[0];
            int calories = Integer.parseInt(data[1]);
            FoodRecord recordToAdd = new FoodRecord(name, calories);
            return recordToAdd;
        } catch (NumberFormatException e) {
            Ui.printAddFoodSyntax();
        } catch (NullPointerException e) {
            Ui.printNonNullInput();
        } catch (WrongDividerOrderException e) {
            e.printStackTrace();
            System.out.println("Oops, internal error");
        }
        return null;
    }

    public static String formatModuleToStore(Module module) {
        String code = module.getCode();
        String name = module.getName();
        String expectedGrade = module.getExpectedGrade();
        String data = code + " | " + name + " | " + expectedGrade + "\n";
        return data;
    }

    public static Module retrieveStoredModule(String data) throws StorageException {
        String[] tokens = data.split("\\|");
        assert tokens.length == 3;
        String code = tokens[0];
        String name = tokens[1];
        String expectedGrade = tokens[2];
        try {
            return new Module(code, name, expectedGrade);
        } catch (Exception e) {
            throw new StorageException();
        }
    }

}

