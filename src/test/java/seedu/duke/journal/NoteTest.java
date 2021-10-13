package seedu.duke.journal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoteTest {
    static Note parser = new Note("Trial");

    @Test
    void getNoteNameTest() {
        assertEquals("Trial", parser.getNoteName());
    }
}