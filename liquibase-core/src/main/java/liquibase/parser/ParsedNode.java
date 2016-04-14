package liquibase.parser;

import liquibase.AbstractExtensibleObject;
import liquibase.exception.ParseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Represents an abstract syntax tree that can eventually be loaded into an object.
 * By using a ParsedNode rather, we can make most of the parsing logic independent of the original file format.
 * <br><br>
 * This object is similar in theory to an XML DOM but is lighter-weight and not XML-specific.
 * Each node has a name as well as a value and/or children ParsedNodes.
 * <br><br>
 * In order to maintain parent/children references, these objects are created and managed through methods and cannot be constructed directly.
 *
 * @see Parser
 * @see liquibase.parser.mapping.ParsedNodeMapping
 */
public class ParsedNode extends AbstractExtensibleObject {

    //parent and children are private so that they are managed through business logic to maintain references
    private ParsedNode parent;
    private List<ParsedNode> children = new ArrayList<>();

    /**
     * The name of the node.
     */
    public String name;
    public Object value;

    private String originalName;
    private ParsedNode originalParent;

    /**
     * The name of the file this parsed node came from.
     */
    public String fileName;

    /**
     * The line number of the file this parsed node came from.
     */
    public Integer lineNumber;

    /**
     * The column number if the file this parsed node came from.
     */
    public Integer columnNumber;

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Creates a new root ParsedNode.
     */
    public static ParsedNode createRootNode(String name) {
        return new ParsedNode(name, null);
    }

    private ParsedNode(String name, ParsedNode parent) {
        this.name = name;
        this.originalName = name;
        this.parent = parent;
        this.originalParent = parent;
    }

    /**
     * Uses an custom format vs. {@link AbstractExtensibleObject} to be more readable
     */
    public String describe() {
        return (getClass().getSimpleName()
                + "{" + name + (value == null ? "" : ("=" + value))
                + (children == null || children.size() == 0 ? "" : ", children=" + new StringUtil.DefaultFormatter().toString(children)))
                + "}";
    }

    /**
     * Sets the "value" of this parsed node.
     *
     * @return this parsed node.
     */
    public ParsedNode setValue(Object value) {
        this.value = value;
        return this;
    }

    /**
     * Returns this node's parent.
     */
    public ParsedNode getParent() {
        return parent;
    }

    /**
     * The name originally set for this parsed node. Even if the name is changed, this value stays constant.
     */
    public String getOriginalName() {
        return originalName;
    }

    /**
     * The original parent of this parsed node. Even if the node is moved, this value stays constant.
     */
    public ParsedNode getOriginalParent() {
        return originalParent;
    }

    /**
     * Creates a new child node with the given name and adds it to this object's children.
     *
     * @return the new child node.
     */
    public ParsedNode addChild(String name) {
        ParsedNode child = new ParsedNode(name, this);
        children.add(child);
        return child;
    }

    /**
     * Finds a child with the given name.
     *
     * @param createIfNeeded If true, creates a new child node on this node if one doesn't already exist. If false, returns null if no match is found.
     * @throws ParseException If multiple children have the give name
     */
    public ParsedNode getChild(String name, boolean createIfNeeded) throws ParseException {
        ParsedNode returnNode = null;
        for (ParsedNode child : children) {
            if (Objects.equals(child.name, name)) {
                if (returnNode == null) {
                    returnNode = child;
                } else {
                    throw new ParseException("Multiple children match " + name, this);
                }
            }
        }

        if (returnNode == null && createIfNeeded) {
            returnNode = this.addChild(name);
        }
        return returnNode;
    }

    /**
     * Finds the child with the given name and returns the value in that child.
     * If no child matches the name, returns null.
     *
     * @param returnType  convert the value to the given type using {@link ObjectUtil#convert(Object, Class)}
     * @param removeChild if true, remove the child (if it exists) after returning the value.
     * @throws ParseException if multiple children match the given name
     */
    public <T> T getChildValue(String name, Class<T> returnType, boolean removeChild) throws ParseException {
        ParsedNode child = getChild(name, false);
        if (child == null) {
            return null;
        }
        Object returnValue = child.value;
        if (removeChild) {
            child.remove();
        }
        return ObjectUtil.convert(returnValue, returnType);
    }


    /**
     * Returns all children that match the given filter.
     *
     * @param recursive If true, search recursively down the children until a match is found.
     */
    public List<ParsedNode> getChildren(ParsedNodeFilter filter, boolean recursive) {
        List<ParsedNode> returnList = new ArrayList<>();
        getChildren(filter, recursive, returnList);
        return Collections.unmodifiableList(returnList);
    }

    /**
     * Convenience method for {@link #getChildren(ParsedNodeFilter, boolean)} to find nodes by name.
     */
    public List<ParsedNode> getChildren(String nodeName, boolean recursive) {
        return getChildren(new ParsedNodeNameFilter(nodeName), recursive);
    }

    private void getChildren(ParsedNodeFilter filter, boolean recursive, List<ParsedNode> list) {
        for (ParsedNode child : this.children) {
            if (filter.matches(child)) {
                list.add(child);
            } else if (recursive) {
                child.getChildren(filter, recursive, list);
            }
        }
    }

    /**
     * Removes direct children that match the given filter.
     */
    public void removeChildren(ParsedNodeFilter filter) throws ParseException {
        Iterator<ParsedNode> childIterator = children.iterator();
        while (childIterator.hasNext()) {
            ParsedNode child = childIterator.next();
            if (filter.matches(child)) {
                log.debug("Removing '"+child.name+"' from '"+this.name+"'");
                childIterator.remove();
                child.parent = null;
            }
        }
    }

    /**
     * Convenience method for {@link #removeChildren(ParsedNodeFilter)} to remove nodes by name.
     */
    public void removeChildren(String childName) throws ParseException {
        removeChildren(new ParsedNodeNameFilter(childName));
    }

    /**
     * Removes this node from its parent.
     *
     * @throws ParseException if this is the root node
     */
    public void remove() throws ParseException {
        if (this.parent == null) {
            throw new ParseException("Cannot remove root node", this);
        }

        log.debug("Removing '"+this.name+"' from '"+parent.name+"'");

        Iterator<ParsedNode> parentChildIterator = parent.children.iterator();
        while (parentChildIterator.hasNext()) {
            ParsedNode child = parentChildIterator.next();
            if (child == this) {
                parentChildIterator.remove();
                break;
            }
        }
        this.parent = null;

    }

    /**
     * Returns the list of child nodes, unmodifiable.
     */
    public List<ParsedNode> getChildren() {
        return Collections.unmodifiableList(children);
    }


    /**
     * Moves the direct children that match the given filter to the newParent.
     */
    public void moveChildren(ParsedNodeFilter filter, ParsedNode newParent) {
        Iterator<ParsedNode> childIterator = children.iterator();
        while (childIterator.hasNext()) {
            ParsedNode child = childIterator.next();
            if (filter.matches(child)) {
                log.debug("Moving '"+child.name+"' from '"+child.parent.name+"' to '"+newParent.name+"'");

                childIterator.remove();
                newParent.children.add(child);
                child.parent = newParent;
            }
        }

    }

    /**
     * Convenience method for {@link #moveChildren(ParsedNodeFilter, ParsedNode)} by child name
     */
    public void moveChildren(String childName, ParsedNode newParent) {
        moveChildren(new ParsedNodeNameFilter(childName), newParent);
    }

    /**
     * Move this node to the passed new parent.
     */
    public void moveTo(ParsedNode newParent) {
        log.debug("Moving '"+this.name+"' from '"+this.parent.name+"' to '"+newParent.name+"'");

        Iterator<ParsedNode> parentChildIterator = parent.children.iterator();
        while (parentChildIterator.hasNext()) {
            ParsedNode child = parentChildIterator.next();
            if (child == this) {
                parentChildIterator.remove();
                break;
            }
        }
        this.parent = newParent;
        newParent.children.add(this);
    }

    /**
     * Copy this node to the passed new parent.
     */
    public void copyTo(ParsedNode newParent) {
        log.debug("Copying '"+this.name+"' from '"+this.parent.name+"' to '"+newParent.name+"'");

        ParsedNode copy = newParent.addChild(this.name);
        copy.originalName = this.originalName;
        copy.originalParent = this.originalParent;

        copy.fileName = this.fileName;
        copy.lineNumber = this.lineNumber;
        copy.columnNumber = this.columnNumber;

        copy.value = this.value;
        for (ParsedNode child : this.children) {
            child.copyTo(copy);
        }

        Iterator<ParsedNode> parentChildIterator = parent.children.iterator();
        while (parentChildIterator.hasNext()) {
            ParsedNode child = parentChildIterator.next();
            if (child == this) {
                parentChildIterator.remove();
                break;
            }
        }
        this.parent = newParent;
        newParent.children.add(this);

    }


    /**
     * Interface for finding parsed nodes.
     */
    public interface ParsedNodeFilter {
        boolean matches(ParsedNode node);
    }

    private static class ParsedNodeNameFilter implements ParsedNodeFilter {
        private final String nodeName;

        public ParsedNodeNameFilter(String nodeName) {
            this.nodeName = nodeName;
        }

        @Override
        public boolean matches(ParsedNode node) {
            return Objects.equals(node.name, nodeName);
        }
    }
}
