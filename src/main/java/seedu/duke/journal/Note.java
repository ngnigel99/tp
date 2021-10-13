package seedu.duke.journal;

public class Note {
    protected String noteName;

    public Note(String noteName) {
        this.noteName = noteName;
    }

    /**
     * Returns the name of the note.
     * @return noteName which stores the name of the note.
     */
    public String getNoteName() {
        return noteName;
    }

    /**
     * Returns name of notebook in format to save in data file.
     * @return  stringForStorage name of notebook in format to save in data file.
     */
    public String toSaveFileFormat() {
        String stringForStorage = "note" + "|" + noteName;
        return stringForStorage;
    }

}