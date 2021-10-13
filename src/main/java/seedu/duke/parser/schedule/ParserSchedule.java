package seedu.duke.parser.schedule;

import seedu.duke.exceptions.IncorrectNumberOfArgumentsException;
import seedu.duke.parser.Parser;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static seedu.duke.constants.Messages.TODO;

public class ParserSchedule {
    public static YearMonth parseCalendarCommandForJunit(String input) {
        // takes substring excluding "calendar" from command
        String extractMonthYear = input.substring(9);
        var arguments = extractMonthYear.split("-");
        int month = Integer.parseInt(arguments[0]);
        assert month <= 12;
        int year = Integer.parseInt(arguments[1]);
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth;
    }

    //modified from @author svetha
    public static ArrayList<String> parseTodoCommand(String input) throws IncorrectNumberOfArgumentsException {
        final String[] commandTypeAndParams = Parser.splitCommandAndArgs(input);
        assert commandTypeAndParams.length == 2;
        final String commandArgs = commandTypeAndParams[1];
        String[] todoArguments = commandArgs.split(" ");
        if (todoArguments.length == 1) {
            throw new IncorrectNumberOfArgumentsException("I need the task name and task date to add it!");
        } else if (todoArguments.length == 2 && todoArguments[1].equals("n/")) {
            throw new IncorrectNumberOfArgumentsException("Task name not found after n/ !");
        } else if (todoArguments.length == 4 && todoArguments[3].equals("d/")) {
            throw new IncorrectNumberOfArgumentsException("Task date not found after d/ !");
        }
        boolean isNameArgumentPresent = false;
        boolean isDateArgumentPresent = false;
        for (String todoArgument : todoArguments) {
            if (todoArgument.equals("n/")) {
                isNameArgumentPresent = true;
            }
            if (todoArgument.equals("d/")) {
                isDateArgumentPresent = true;
            }
        }
        if (isNameArgumentPresent && isDateArgumentPresent) {
            return parseTodoArgumentsArray(input);
        } else if (isNameArgumentPresent) {
            throw new IncorrectNumberOfArgumentsException("Date argument not found!");
        } else if (isDateArgumentPresent) {
            throw new IncorrectNumberOfArgumentsException("Name argument not found!");
        }
        return null;
    }

    //modified from @author svetha
    public static ArrayList<String> parseTodoArgumentsArray(String input) throws IncorrectNumberOfArgumentsException {
        ArrayList<String> argumentsTodoCommand = new ArrayList<>();
        String todoDetails = input.trim().split("todo")[1];
        String descriptionAndDate = todoDetails.split("n/")[1].trim();
        String description = descriptionAndDate.split("d/")[0].trim();
        if (description.equals("")) {
            throw new IncorrectNumberOfArgumentsException("Name argument not found");
        }
        String date = descriptionAndDate.split("d/")[1].trim();
        List<String> todoInformation = Arrays.asList(TODO, description, date);
        argumentsTodoCommand.addAll(todoInformation);
        return argumentsTodoCommand;
    }


    public static String[] parseCalendarCommand(String input) throws IncorrectNumberOfArgumentsException {
        // takes substring excluding "calendar" from command
        String extractMonthYear = input.substring(9);
        String[] arguments = extractMonthYear.split("-");
        return arguments;
    }
}
