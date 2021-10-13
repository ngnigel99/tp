package seedu.duke.exceptions;

public class MissingTodoArgumentsException extends Exception {
    public MissingTodoArgumentsException(String message) {
        System.out.println(message);
    }
}
