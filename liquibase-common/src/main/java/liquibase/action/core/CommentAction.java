package liquibase.action.core;

import liquibase.action.AbstractAction;

/**
 * Makes a comment. Normally this is a no-op action, but can be watched for and logged if wanted.
 */
public class CommentAction extends AbstractAction {

    /**
     * A header for the comment.
     */
    public String header;

    /**
     * Main comment.
     */
    public String comment;

    public CommentAction() {
    }

    public CommentAction(String comment) {
        this.comment = comment;
    }

    public CommentAction(String header, String comment) {
        this.header = header;
        this.comment = comment;
    }
}
