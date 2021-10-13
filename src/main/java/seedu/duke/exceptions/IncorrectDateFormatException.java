package seedu.duke.exceptions;

public class IncorrectDateFormatException extends Exception {
    public IncorrectDateFormatException() {
        System.out.println("You have given a date of incorrect format! Try DD-MM-YYY...");
    }
}
