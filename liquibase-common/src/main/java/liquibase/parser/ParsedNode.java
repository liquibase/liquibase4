package liquibase.parser;

import liquibase.AbstractExtensibleObject;
import liquibase.exception.ParseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

/**
 * Represents an abstract syntax tree that can eventually be loaded into an object.
 * By using a ParsedNode rather, we can make most of the parsing logic independent of the original file format.
 * <br><br>
 * This object is similar in theory to an XML DOM but is lighter-weight and not XML-specific.
 * Each node has a name as well as a value and/or children ParsedNodes. There can be multiple children with the same node name.
 * <br><br>
 * In order to maintain parent/children references, these objects are created and managed through methods and cannot be constructed directly.
 *
 * @see Parser
 * @see liquibase.parser.mapping.ParsedNodeMapping
 */
public class ParsedNode extends AbstractExtensibleObject implements Comparable<ParsedNode> {

    /**
     * Convenience enum for available {@link #addMarker(Marker)} options.
     */
    public enum Marker {

        /**
         * If set and supported by a parser/unparser, the node was/should be represented as a nested text value.
         * For example, in xml the node should be represented as &lt;parentNode&gt;value&lt;/parentNode&gt; instead of &lt;name&gt;value&lt;/name&gt;
         */
        isText,

        isCollectionNode,

        /**
         * If set and supported by a parser/unparser, the node was/should be represented as an attribute.
         * For example, in xml the node should be represented as name="value" instead of &lt;name&gt;value&lt;/name&gt;
         */
        isAttribute
    }

    //these values are private so that they are managed through business logic to maintain references
    private ParsedNode parent;
    private List<ParsedNode> children = new ArrayList<>();
    private String name;
    private Object value;

    private String originalName;
    private Object originalValue;
    private ParsedNode originalParent;

    private Set<String> markers = new HashSet<>();

    /**
     * The type of object this parsed node was unparsed from.
     */
    public Class sourceType;

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
     * Returns the node's name. Not exposed as a public property so we can track changes to the node name.
     */
    public String getName() {
        return name;
    }

    /**
     * Alias for {@link #rename(String)}
     */
    public ParsedNode setName(String newName) {
        return rename(newName);
    }

    /**
     * Changes the node's name.
     */
    public ParsedNode rename(String newName) {
        log.debug("Renamed '" + this.name + "' to '" + newName + "'");
        this.name = newName;
        return this;
    }

    /**
     * Returns the raw "value" associated with this node. Nodes can have values in addition to any children they may have.
     * Not exposed as a public property so we can track original values and log changes.
     *
     * @see #getValue(Object, Class)
     * @see #setValue(Object)
     */
    public Object getValue() {
        return value;
    }

    /**
     * Returns the current value for this node, converted to the given type.
     * If the value is null, return the passed defaultValue.
     *
     * @throws IllegalArgumentException if the existing value cannot be converted to the expected type.
     */
    public <T> T getValue(T defaultValue, Class<T> type) throws IllegalArgumentException {
        return ObjectUtil.defaultIfNull(ObjectUtil.convert(this.value, type), defaultValue);
    }

    /**
     * Sets the node's value.
     * Because value is not set in the constructor to know what the originalValue really is, we will set the originalValue property the first time this is called with a non-null value.
     *
     * @see #moveValue(ParsedNode)
     */
    public ParsedNode setValue(Object value) {
        if (log.isDebugEnabled()) {
            log.debug("Setting '" + this.name + "' value '" + value + "'");
        }

        this.value = value;
        if (originalValue == null) {
            this.originalValue = value;
        }
        return this;
    }

    /**
     * Moves the value on this node to the given newNode.
     */
    public void moveValue(ParsedNode newNode) {
        log.debug("Moving node value from '" + this.name + "' to '" + newNode.name + "'");
        newNode.value = this.value;
        this.value = null;
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
     * Returns the original value from this node.
     */
    public Object getOriginalValue() {
        return originalValue;
    }

    /**
     * Return true if there is no value and no children nodes.
     */
    public boolean isEmpty() {
        return value == null && children.size() == 0;
    }

    /**
     * Uses an custom format with the name and value at the front and then the children instead of the standard {@link AbstractExtensibleObject} logic to be more readable
     */
    public String describe() {
        return (getClass().getSimpleName()
                + "{" + name+ (value == null ? "" : ("=\"" + value+"\""))
                + (children == null || children.size() == 0 ? "" : " " + new StringUtil.DefaultFormatter().toString(children)))
                + "}";
    }

    /**
     * Outputs a multi-line, easy to read version of this parsed node.
     * Includes path up through it's parents if it is not the root node.
     */
    public String prettyPrint() {
        return prettyPrint(true);
    }

    private String prettyPrint(boolean includePath) {
        StringWriter writer = new StringWriter();
        if (includePath) {
            String parentString = "";
            ParsedNode parent = this.getParent();
            while (parent != null) {
                parentString = parent.name + " / " + parentString;
                parent = parent.getParent();
            }
            writer.write(parentString);
        }

        this.prettyPrint(writer);

        return writer.toString();
    }

    private void prettyPrint(Writer writer) {
        String output = name;
        if (this.value != null) {
            output += ": " + this.value;
        }
        for (ParsedNode child : children) {
            output += "\n" + StringUtil.indent(child.prettyPrint(false));
        }

        try {
            writer.write(output);
        } catch (IOException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }


    /**
     * Convenience method for {@link #addMarker(String)} using standard markers
     */
    public ParsedNode addMarker(Marker marker) {
        return addMarker(marker.name());
    }

    /**
     * Adds a marker to this parsed node.
     * Markers are not treated as child nodes or output, but can be used to control how a node is output and/or managed.
     *
     * @see {@link liquibase.parser.ParsedNode.Marker} for standard options
     * @see {@link #addMarker(Marker)}
     */
    public ParsedNode addMarker(String marker) {
        this.markers.add(marker);
        return this;
    }


    /**
     * Convenience method for {@link #removeMarker(String)} using standard markers
     */
    public ParsedNode removeMarker(Marker marker) {
        return removeMarker(marker.name());
    }

    /**
     * Removes a marker from this parsed node. If the marker is not currently set, does nothing.
     */
    public ParsedNode removeMarker(String marker) {
        this.markers.remove(marker);
        return this;
    }


    /**
     * Convenience method for {@link #hasMarker(String)} using standard markers
     */
    public boolean hasMarker(Marker marker) {
        return hasMarker(marker.name());
    }

    /**
     * Return true if the given marker has been set on this node.
     */
    public boolean hasMarker(String marker) {
        return this.markers.contains(marker);
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
     * Convenience method to bulk-add children, especially useful in tests.
     * Each key in the passed map is converted to a node.
     * If the map entry's value is also a Map, that map is used to create sub-nodes with this method as well.
     * If the map entry's value is not a map, it is set as the new node's value.
     */
    public void addChildren(Map<String, Object> children) {
        if (children != null) {
            for (Map.Entry<String, Object> child : children.entrySet()) {
                ParsedNode childNode = this.addChild(child.getKey());
                if (child.getValue() instanceof Map) {
                    childNode.addChildren((Map<String, Object>) child.getValue());
                } else {
                    childNode.setValue(child.getValue());
                }
            }
        }
    }


    /**
     * Return true if there is at least one child with the given name.
     */
    public boolean hasChild(String name) {
        for (ParsedNode child : children) {
            if (Objects.equals(child.name, name)) {
                return true;
            }
        }
        return false;
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
        T returnValue = child.getValue(null, returnType);
        if (removeChild) {
            child.remove();
        }
        return returnValue;
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

    /**
     * Convenience method for {@link #getChildren(ParsedNodeFilter, boolean)} to find nodes by their sourceType.
     */
    public List<ParsedNode> getChildren(final Class sourceType, boolean recursive) {
        return getChildren(new ParsedNodeFilter() {
            @Override
            public boolean matches(ParsedNode node) {
                return node.sourceType != null && sourceType.isAssignableFrom(node.sourceType);
            }
        }, recursive);
    }

    private void getChildren(ParsedNodeFilter filter, boolean recursive, List<ParsedNode> list) {
        for (ParsedNode child : this.children) {
            if (filter.matches(child)) {
                list.add(child);
            } else if (recursive && !child.children.isEmpty()) {
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
                log.debug("Removing '" + child.name + "' from '" + this.name + "'");
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

        log.debug("Removing '" + this.name + "' from '" + parent.name + "'");

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
     * Removes this node from it's parent only if it has no value and has no children.
     * Returns true if node was removed, false if it was not.
     */
    public boolean removeIfEmpty() throws ParseException {
        if (value == null && children.isEmpty()) {
            this.remove();
            return true;
        } else {
            return false;
        }

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
                log.debug("Moving '" + child.name + "' from '" + child.parent.name + "' to '" + newParent.name + "'");

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
        log.debug("Moving '" + this.name + "' from '" + this.parent.name + "' to '" + newParent.name + "'");

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
     *
     * @return the new copy of the node
     */
    public ParsedNode copyTo(ParsedNode newParent) {
        log.debug("Copying '" + this.name + "' from '" + this.parent.name + "' to '" + newParent.name + "'");

        ParsedNode copy = newParent.addChild(this.name);
        copy.originalName = this.originalName;
        copy.originalParent = this.originalParent;

        copy.fileName = this.fileName;
        copy.lineNumber = this.lineNumber;
        copy.columnNumber = this.columnNumber;

        copy.value = this.value;
        for (ParsedNode child : new ArrayList<>(this.children)) {
            child.copyTo(copy);
        }
        copy.markers.addAll(this.markers);

        return copy;
    }

    /**
     * Copies the direct children that match the given filter to the newParent.
     */
    public void copyChildren(ParsedNodeFilter filter, ParsedNode newParent) {
        for (ParsedNode child : new ArrayList<>(children)) {
            if (filter.matches(child)) {
                child.copyTo(newParent);
            }
        }

    }

    /**
     * Convenience method for {@link #copyChildren(ParsedNodeFilter, ParsedNode)} by child name
     */
    public void copyChildren(String childName, ParsedNode newParent) {
        copyChildren(new ParsedNodeNameFilter(childName), newParent);
    }


    /**
     * Rename all children that match the given filter.
     */
    public void renameChildren(ParsedNodeFilter filter, String newName) {
        for (ParsedNode child : children) {
            if (filter.matches(child)) {
                log.debug("Renamed '" + child.name + "' to '" + newName + "'");
                child.name = newName;
            }
        }

    }

    @Override
    public Object clone() {
        ParsedNode clone = (ParsedNode) super.clone();
        clone.markers.addAll(this.markers);
        return clone;
    }

    /**
     * Convenience version of {@link #renameChildren(ParsedNodeFilter, String)} when you are renaming by node name.
     */
    public void renameChildren(String oldName, String newName) {
        renameChildren(new ParsedNodeNameFilter(oldName), newName);
    }

    /**
     * Interface for finding parsed nodes.
     */
    public interface ParsedNodeFilter {
        boolean matches(ParsedNode node);

        ParsedNodeFilter ALL = new ParsedNodeFilter() {
            @Override
            public boolean matches(ParsedNode node) {
                return true;
            }
        };
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

    @Override
    public int compareTo(ParsedNode o) {
        if (this.getChildren().size() == 0 && o.getChildren().size() > 0) {
            return -1;
        } else if (this.getChildren().size() > 0 && o.getChildren().size() == 0) {
            return 1;
        }

        int result = this.getName().compareTo(o.getName());
        if (result == 0) {
            result = this.describe().compareTo(o.describe());
        }
        return result;
    }

    /**
     * Return true if this ParsedNode has children with duplicate names.
     * @return
     */
    public boolean hasDuplicateChildren() {
        Set<String> childNames = new HashSet<>();
        for (ParsedNode child : this.getChildren()) {
            if (!childNames.add(child.getName())) {
                return true;
            }
        }
        return false;
    }

}
