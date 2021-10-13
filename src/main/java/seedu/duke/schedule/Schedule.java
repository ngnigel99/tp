package seedu.duke.schedule;

import java.time.DayOfWeek;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import seedu.duke.ui.Ui;

import static seedu.duke.constants.Messages.NUMBER_OF_DAYS_IN_WEEK;
import static seedu.duke.constants.Messages.DAYS_IN_MONTH;
import static seedu.duke.constants.Messages.FIRST_INDEX;
import static seedu.duke.constants.Messages.LEAVE_EMPTY_IN_DISPLAY;
import static seedu.duke.constants.Messages.EMPTY_SPACE;
import static seedu.duke.constants.Messages.CALENDER_DATE_FORMATTER;
import static seedu.duke.constants.Messages.TASK_FORMATTER;
import static seedu.duke.constants.Messages.SEPARATOR_DISPLAY;



/**
 * Represents the logic and UI behind calendar with tasks display.
 */
public class Schedule {

    /**
     * Prints out the calendar after the calendar display command is used.
     *
     * @param inputYearMonth YearMonth object that is parsed from the year and month given by the user.
     * @param calendarTasks  The calendar
     */
    public static void displayCalendar(YearMonth inputYearMonth, ArrayList<ArrayList<String>> calendarTasks) {
        ArrayList<String> calendar = new ArrayList<>();
        addDatesForDaysInMonth(inputYearMonth, calendar);
        addNoDatesInBeginning(inputYearMonth, calendar);
        addNoDatesInEnd(calendar);

        // Print out the entire display in week rows
        int totalWeeks = (int) Math.ceil((double)calendar.size() / NUMBER_OF_DAYS_IN_WEEK);
        int currentWeek = 0;
        Ui.printCalendarLine();
        printEntireCalendar(totalWeeks, currentWeek, calendar, calendarTasks);
    }

    private static void printEntireCalendar(int totalWeeks, int currentWeek,
                                            ArrayList<String> calendar, ArrayList<ArrayList<String>> calendarTasks) {
        AtomicInteger j = new AtomicInteger();
        while (currentWeek < totalWeeks) {
            int indexOfDay = currentWeek * 7;
            Ui.printDayDemarcation();
            printCalendarDates(calendar, indexOfDay);
            System.out.println();
            printTaskForWeek(calendarTasks, calendar, indexOfDay);
            Ui.printCalendarLine();
            currentWeek++;
        }
    }

    //Add all dates that are available in the month from 01-value of last day of month
    private static void addDatesForDaysInMonth(YearMonth inputYearMonth, ArrayList<String> calendar) {
        String[] daysInMonth = DAYS_IN_MONTH;
        calendar.addAll(Arrays.asList(daysInMonth).subList(0, inputYearMonth.lengthOfMonth()));
    }

    // Add in spaces at the beginning for display upto day 1
    private static void addNoDatesInBeginning(YearMonth inputYearMonth, ArrayList<String> calendar) {
        DayOfWeek dayOneOfMonth = inputYearMonth.atDay(1).getDayOfWeek();
        if (dayOneOfMonth != DayOfWeek.SUNDAY) {
            IntStream.range(FIRST_INDEX, dayOneOfMonth.getValue()).forEachOrdered(i ->
                    calendar.add(0, LEAVE_EMPTY_IN_DISPLAY));
        }
    }

    //Add in spaces at the end after the last day to fill in the week
    private static void addNoDatesInEnd(ArrayList<String> calendar) {
        while (calendar.size() % NUMBER_OF_DAYS_IN_WEEK != 0) {
            calendar.add(LEAVE_EMPTY_IN_DISPLAY);
        }
    }

    //Print out the top rows with dates from 01-value of last day of month
    private static void printCalendarDates(ArrayList<String> calendar, int indexOfDay) {
        for (int dayOfWeek = 0; dayOfWeek < NUMBER_OF_DAYS_IN_WEEK; dayOfWeek++) {
            String s = EMPTY_SPACE + calendar.get(indexOfDay + dayOfWeek) + CALENDER_DATE_FORMATTER;
            System.out.print(s);
        }
    }

    private static void printTaskForWeek(ArrayList<ArrayList<String>> calendarTasks,
                                         ArrayList<String> calendar, int indexOfDay) {
        int calendarRow = 0;
        while (calendarRow < 3) {
            System.out.print(SEPARATOR_DISPLAY);
            for (int day = 0; day < 7; day++) {
                printDetails(calendarTasks, calendar, indexOfDay, calendarRow, day);
            }
            System.out.println();
            calendarRow++;
        }
    }

    private static void printDetails(ArrayList<ArrayList<String>> calendarTasks,
                                     ArrayList<String> calendarDates, int indexOfDay, int calendarRow, int day) {
        String dayString = calendarDates.get(indexOfDay + day).trim();
        if (!dayString.equals(EMPTY_SPACE) && calendarTasks.get(Integer.parseInt(dayString)).size() > calendarRow) {
            int currentDay = Integer.parseInt(dayString);
            String taskName = calendarTasks.get(currentDay).get(calendarRow);
            if (taskName.length() > 10) {
                taskName = taskName.substring(0, 10);
            } else {
                taskName = String.format("%-" + 10 + "s", taskName);
            }
            System.out.print(EMPTY_SPACE + taskName + TASK_FORMATTER);
        } else {
            Ui.printEmptyTaskSpot();
        }
    }

}