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
        node.prettyPrint() == expectedNodeDesc.trim()

        when:
        def rebuiltObject = mappingFactory.toObject(node, object.getClass(), null, null, scope)

        then:
        rebuiltObject.describe() == object.describe()
        mappingFactory.toParsedNode(rebuiltObject, null, null, null, scope).prettyPrint() == expectedNodeDesc.trim()

        where:
        [desc, object, expectedNodeDesc] << [
                ["simple empty changelog", new ChangeLog(), "changeLog"],

                ["changelog with string properties set",
                 new ChangeLog().each {
                     it.logicalPath = "com/example/logical.xml"
                     it.physicalPath = "com/example/physical.xml"
                 },
                 """
changeLog
    logicalPath: com/example/logical.xml
    physicalPath: com/example/physical.xml
    """.trim()],

                ["changelog with empty changeSets",
                 new ChangeLog().each {
                     it.logicalPath = "com/example/logical.xml"
                     it.physicalPath = "com/example/physical.xml"
                     it.addEntry(new ChangeSet("1", "bob", "com/example/path"))
                     it.addEntry(new ChangeSet("2", "bob", "com/example/path"))
                 },
                 """
changeLog
    changeLogEntries
        changeSet
            author: bob
            id: 1
            logicalPath: com/example/path
        changeSet
            author: bob
            id: 2
            logicalPath: com/example/path
    logicalPath: com/example/logical.xml
    physicalPath: com/example/physical.xml
"""],

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
                 """
changeLog
    changeLogEntries
        changeSet
            actions
                renameColumn
                    columnDefinition: int not null
                    newName: new_name
                    oldName: old_name
                    relation
                        container
                            name: schema_name
                            type: class liquibase.item.core.Schema
                            virtual: false
                        name: table_name
                        type: class liquibase.item.core.Table
                        virtual: false
            author: bob
            id: 1
            logicalPath: com/example/path
    logicalPath: com/example/logical.xml
"""]
        ]
    }
}