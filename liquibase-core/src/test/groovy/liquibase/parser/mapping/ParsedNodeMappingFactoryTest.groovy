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

        mappingFactory.toParsedNode(null, ChangeLog, "changeLogEntries", null, scope) == null
        mappingFactory.toParsedNode(null, null, "changeLogEntries", null, scope) == null
    }

    /**
     * Tests mapping logic across configured ParsedNodeMappings by converting an object to a parsed node then converting back, comparing that everything matches.
     */
    @Unroll("#featureName: #desc")
    def "parsed nodes convert to and from objects correctly"() {
        when:
        def scope = JUnitScope.instance
        def mappingFactory = scope.getSingleton(ParsedNodeMappingFactory)
        def node = mappingFactory.toParsedNode(object, null, null, null, scope)

        then:
        node.describe() == expectedNodeDesc

        when:
        def rebuiltObject = mappingFactory.toObject(node, object.getClass(), null, null, scope)

        then:
        rebuiltObject.describe() == object.describe()
        mappingFactory.toParsedNode(rebuiltObject, null, null, null, scope).describe() == expectedNodeDesc

        where:
        [desc, object, expectedNodeDesc] << [
                ["simple empty changelog", new ChangeLog(), "ParsedNode{changeLog}"],

                ["changelog with string properties set",
                 new ChangeLog().each {
                     it.logicalPath = "com/example/logical.xml"
                     it.physicalPath = "com/example/physical.xml"
                 },
                 "ParsedNode{changeLog, children=[ParsedNode{logicalPath=com/example/logical.xml}, ParsedNode{physicalPath=com/example/physical.xml}]}"],

                ["changelog with empty changeSets",
                 new ChangeLog().each {
                     it.logicalPath = "com/example/logical.xml"
                     it.physicalPath = "com/example/physical.xml"
                     it.addEntry(new ChangeSet("1", "bob", "com/example/path"))
                     it.addEntry(new ChangeSet("2", "bob", "com/example/path"))
                 },
                 "ParsedNode{changeLog, children=[ParsedNode{changeLogEntries, children=[ParsedNode{changeSet, children=[ParsedNode{author=bob}, ParsedNode{id=1}, ParsedNode{logicalPath=com/example/path}]}, ParsedNode{changeSet, children=[ParsedNode{author=bob}, ParsedNode{id=2}, ParsedNode{logicalPath=com/example/path}]}]}, ParsedNode{logicalPath=com/example/logical.xml}, ParsedNode{physicalPath=com/example/physical.xml}]}"],

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
                 "ParsedNode{changeLog, children=[ParsedNode{changeLogEntries, children=[ParsedNode{changeSet, children=[ParsedNode{actions, children=[ParsedNode{renameColumnAction, children=[ParsedNode{columnDefinition=int not null}, ParsedNode{newName=new_name}, ParsedNode{oldName=old_name}, ParsedNode{relation, children=[ParsedNode{container, children=[ParsedNode{name=schema_name}, ParsedNode{type=class liquibase.item.core.Schema}, ParsedNode{virtual=false}]}, ParsedNode{container=ParsedNode{container, children=[ParsedNode{name=schema_name}, ParsedNode{type=class liquibase.item.core.Schema}, ParsedNode{virtual=false}]}}, ParsedNode{name=table_name}, ParsedNode{type=class liquibase.item.core.Table}, ParsedNode{virtual=false}]}, ParsedNode{relation=ParsedNode{relation, children=[ParsedNode{container, children=[ParsedNode{name=schema_name}, ParsedNode{type=class liquibase.item.core.Schema}, ParsedNode{virtual=false}]}, ParsedNode{container=ParsedNode{container, children=[ParsedNode{name=schema_name}, ParsedNode{type=class liquibase.item.core.Schema}, ParsedNode{virtual=false}]}}, ParsedNode{name=table_name}, ParsedNode{type=class liquibase.item.core.Table}, ParsedNode{virtual=false}]}}]}]}, ParsedNode{author=bob}, ParsedNode{id=1}, ParsedNode{logicalPath=com/example/path}]}]}, ParsedNode{logicalPath=com/example/logical.xml}]}"]
        ]
    }
}