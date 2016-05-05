package liquibase.exception;

import liquibase.parser.ParsedNode;

/**
 * Exception thrown from parsing changelogs or other files.
 */
public class ParseException extends Exception {

    private final ParsedNode problemNode;

    public ParseException(ParsedNode problemNode) {
        this.problemNode = problemNode;
    }

    public ParseException(String message, ParsedNode problemNode) {
        super(message);
        this.problemNode = problemNode;
    }

    public ParseException(String message, Throwable cause, ParsedNode problemNode) {
        super(message, cause);
        this.problemNode = problemNode;
    }

    public ParseException(Throwable cause, ParsedNode problemNode) {
        super(cause);
        this.problemNode = problemNode;
    }

    public ParsedNode getProblemNode() {
        return problemNode;
    }
}
