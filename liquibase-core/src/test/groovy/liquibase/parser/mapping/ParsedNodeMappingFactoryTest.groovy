package liquibase.parser.mapping

import liquibase.JUnitScope
import liquibase.action.core.RenameColumnAction
import liquibase.changelog.ChangeLog
import liquibase.changelog.ChangeSet
import liquibase.item.core.RelationReference
import liquibase.item.core.Table
import liquibase.util.StringClauses
import spock.lang.Specification
import spock.lang.Unroll

class ParsedNodeMappingFactoryTest extends Specification {

    def "converting nulls returns nulls"() {
        when:
        def scope = JUnitScope.instance
        def mappingFactory = scope.getSingleton(ParsedNodeMappingFactory)

        then:
        mappingFactory.toObject(null, ChangeSet, ChangeLog, "changeLogEntries", scope) == null
        mappingFactory.toObject(null, null, ChangeLog, "changeLogEntries", scope) == null

        mappingFactory.toParsedNode(null, ChangeLog, "changeLogEntries", scope) == null
        mappingFactory.toParsedNode(null, null, "changeLogEntries", scope) == null
    }

    /**
     * Tests mapping logic across configured ParsedNodeMappings by converting an object to a parsed node then converting back, comparing that everything matches.
     */
    @Unroll("#featureName: #desc")
    def "parsed nodes convert to and from objects correctly"() {
        when:
        def scope = JUnitScope.instance
        def mappingFactory = scope.getSingleton(ParsedNodeMappingFactory)
        def node = mappingFactory.toParsedNode(object, null, null, scope)
        def rebuiltObject = mappingFactory.toObject(node, object.getClass(), null, null, scope)

        then:
        node.describe() == expectedNodeDesc
        rebuiltObject.describe() == object.describe()
        mappingFactory.toParsedNode(rebuiltObject, null, null, scope).describe() == expectedNodeDesc

        where:
        [desc, object, expectedNodeDesc] << [
                ["simple empty changelog", new ChangeLog(), "ParsedNode{name=changeLog}"],

                ["changelog with string properties set",
                 new ChangeLog().each {
                     it.logicalPath = "com/example/logical.xml"
                     it.physicalPath = "com/example/physical.xml"
                 },
                 "ParsedNode{children=[ParsedNode{name=logicalPath, value=com/example/logical.xml}, ParsedNode{name=physicalPath, value=com/example/physical.xml}], name=changeLog}"],

                ["changelog with empty changeSets",
                 new ChangeLog().each {
                     it.logicalPath = "com/example/logical.xml"
                     it.physicalPath = "com/example/physical.xml"
                     it.addEntry(new ChangeSet("1", "bob", "com/example/path"))
                     it.addEntry(new ChangeSet("2", "bob", "com/example/path"))
                 },
                 "ParsedNode{children=[ParsedNode{name=changeLogEntries, value=[ParsedNode{children=[ParsedNode{name=author, value=bob}, ParsedNode{name=id, value=1}, ParsedNode{name=logicalPath, value=com/example/path}], name=changeSet}, ParsedNode{children=[ParsedNode{name=author, value=bob}, ParsedNode{name=id, value=2}, ParsedNode{name=logicalPath, value=com/example/path}], name=changeSet}]}, ParsedNode{name=logicalPath, value=com/example/logical.xml}, ParsedNode{name=physicalPath, value=com/example/physical.xml}], name=changeLog}"],

                ["changelog with a changeSet with an action", new ChangeLog().each {
                    it.logicalPath = "com/example/logical.xml"
                    it.addEntry(
                            new ChangeSet("1", "bob", "com/example/path")
                                    .addAction(new RenameColumnAction().each({
                                it.oldName = "old_name";
                                it.newName = "new_name";
                                it.relation = new RelationReference(Table, "schema_name", "table_name")
                                it.columnDefinition = new StringClauses().append("int not null")
                            })))
                },
                 "ParsedNode{children=[ParsedNode{name=changeLogEntries, value=[ParsedNode{children=[ParsedNode{name=actions, value=[ParsedNode{children=[ParsedNode{name=columnDefinition, value=int not null}, ParsedNode{name=newName, value=new_name}, ParsedNode{name=oldName, value=old_name}, ParsedNode{name=relation, value=ParsedNode{children=[ParsedNode{name=container, value=ParsedNode{children=[ParsedNode{name=name, value=schema_name}, ParsedNode{name=type, value=liquibase.item.core.Schema}, ParsedNode{name=virtual, value=false}], name=schemaReference}}, ParsedNode{name=name, value=table_name}, ParsedNode{name=type, value=liquibase.item.core.Table}, ParsedNode{name=virtual, value=false}], name=relationReference}}], name=renameColumnAction}]}, ParsedNode{name=author, value=bob}, ParsedNode{name=id, value=1}, ParsedNode{name=logicalPath, value=com/example/path}], name=changeSet}]}, ParsedNode{name=logicalPath, value=com/example/logical.xml}], name=changeLog}"]
        ]
    }
}