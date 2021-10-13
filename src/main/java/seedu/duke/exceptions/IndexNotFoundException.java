package seedu.duke.exceptions;

public class IndexNotFoundException extends Exception {
    public IndexNotFoundException() {
        System.out.println("Cannot find index of task to delete! Use 'calendar list' first"
                + "to find desired task index");
    }
}
