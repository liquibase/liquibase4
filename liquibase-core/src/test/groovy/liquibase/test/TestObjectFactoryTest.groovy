package liquibase.test

import liquibase.JUnitScope
import liquibase.action.core.AddAutoIncrementAction
import liquibase.action.core.DropTableAction
import liquibase.item.core.ColumnReference
import liquibase.item.core.RelationReference
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import spock.lang.Specification

class TestObjectFactoryTest extends Specification {

    def "createAllPermutations"() {
        when:
        def permutations = JUnitScope.instance.getSingleton(TestObjectFactory).createAllPermutations(DropTableAction, [
                table             : [new RelationReference(Table, "table1"), new RelationReference(Table, "table2"), new RelationReference(Table, "table3")],
                cascadeConstraints: [true, false],
        ])

        then:
        permutations.size() == 4 * 3 //3 + 1 null * 2 + 1 null
        assert permutations*.toString().contains("dropTable()") //empty permutation not included
        assert !permutations.contains(null) //no null value
        permutations*.toString().sort() == (permutations.collect { it.toString() }).unique().sort() //nothing is duplicated
    }

    def "createAllPermutationsWithoutNulls"() {
        when:
        def permutations = JUnitScope.instance.getSingleton(TestObjectFactory).createAllPermutationsWithoutNulls(DropTableAction, [
                table             : [new RelationReference(Table, "table1"), new RelationReference(Table, "table2"), new RelationReference(Table, "table3")],
                cascadeConstraints: [true, false],
        ])

        then:
        permutations.size() == 3 * 2
        assert !permutations*.toString().contains("dropTable()") //empty permutation not included
        assert !permutations.contains(null) //no null value
        permutations*.toString().sort() == (permutations.collect { it.toString() }).unique().sort() //nothing is duplicated
    }

    def "createAllPermutations with nested fields"() {
        when:
        def testObjectFactory = JUnitScope.instance.getSingleton(TestObjectFactory)
        def permutations = testObjectFactory.createAllPermutations(AddAutoIncrementAction, [
                column  : testObjectFactory.createAllPermutations(ColumnReference, [
                        name     : ["a", "b", "c"],
                        container: [new RelationReference(Table, "x"), new RelationReference(Table, "y"), new RelationReference(Table, "z")],
                ]),
                dataType: [new DataType(DataType.StandardType.INTEGER)]
        ])

        then:
        permutations.size() == ((4 * 4) + 1) * 2 //column permutations plus null, times dataType
        assert permutations*.toString().contains("addAutoIncrement()") //empty permutation not included
        assert !permutations.contains(null) //no null value
        permutations*.toString().sort() == (permutations.collect { it.toString() }).unique().sort() //nothing is duplicated
    }
}
