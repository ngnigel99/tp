package seedu.duke.exceptions;

public class DuplicateTaskException extends Exception {
    public DuplicateTaskException() {
        System.out.println("A task by this name has already been added!");
    }
}
