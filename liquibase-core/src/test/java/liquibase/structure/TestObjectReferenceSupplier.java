package liquibase.structure;

import liquibase.JUnitScope;
import liquibase.Scope;
import liquibase.database.ConnectionSupplier;
import liquibase.database.Database;
import liquibase.database.core.GenericDatabase;
import liquibase.plugin.AbstractPlugin;
import liquibase.structure.core.ForeignKey;
import liquibase.util.CollectionUtil;
import liquibase.util.ObjectUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class TestObjectReferenceSupplier<T extends LiquibaseObject> extends AbstractPlugin {

    abstract int getPriority(Class<? extends LiquibaseObject> type, Scope scope);

    List<String> getSimpleObjectNames(Class<T> type, Scope scope) {
        List<String> returnList = new ArrayList<>();

        int objectsToCreate = 10;

        Database database = ObjectUtil.defaultIfEmpty(scope.getDatabase(), new GenericDatabase());
        if (database.getIdentifierCaseHandling(type, false, scope) == Database.IdentifierCaseHandling.LOWERCASE) {
            for (int i = 1; i <= objectsToCreate; i++) {
                returnList.add(type.getSimpleName().toLowerCase() + i);
            }
        } else {
            for (int i = 1; i <= objectsToCreate; i++) {
                returnList.add(type.getSimpleName().toUpperCase() + i);
            }
        }

        return returnList;
    }

    List<String> getComplexObjectNames(Class<T> type, Scope scope) {
        List<String> returnList = new ArrayList<>();

        returnList.add("lower" + type.getSimpleName().toLowerCase());
        returnList.add("UPPER" + type.getSimpleName().toUpperCase());
        returnList.add("Mixed" + type.getSimpleName());
        returnList.add("anotherlower" + type.getSimpleName().toLowerCase());
        returnList.add("ANOTHERUPPER" + type.getSimpleName().toUpperCase());
        returnList.add("AnotherMixed" + type.getSimpleName());
        returnList.add("4test_" + type.getSimpleName().toLowerCase());
        returnList.add("4TEST_" + type.getSimpleName().toLowerCase());
        returnList.add("crazy!@#\\$%^&*()_+{}[]'\"" + type.getSimpleName().toLowerCase());
        returnList.add("CRAZY!@#\\$%^&*()_+{}[]'\"" + type.getSimpleName().toUpperCase());

        return returnList;
    }

    public List<ObjectReference> getObjectNames(Class<T> type, Scope scope) {
        return getObjectNames(type, scope.get(JUnitScope.Attr.objectNameStrategy, ObjectNameStrategy.SIMPLE_NAMES), scope);
    }

    public List<ObjectReference> getObjectNames(Class<T> type, ObjectNameStrategy nameStrategy, Scope scope) {
        List<ObjectReference> returnList = new ArrayList<>();

        List<ObjectReference> containers = ObjectUtil.defaultIfEmpty(getObjectContainers(scope), Collections.singletonList((ObjectReference) null));

        for (ObjectReference container : containers) {
            List<String> objectNames;
            if (nameStrategy == ObjectNameStrategy.COMPLEX_NAMES) {
                objectNames = getComplexObjectNames(type, scope);
            } else {
                objectNames = getSimpleObjectNames(type, scope);
            }

            for (String simpleName : objectNames) {
                returnList.add(new ObjectReference(type, container, simpleName));
            }
            if (container != null) {
                returnList.add(new ObjectReference(type, container, "only_in_" + container.name));
            }
        }

        if (scope.getDatabase().getIdentifierCaseHandling(type, true, scope) == Database.IdentifierCaseHandling.LOWERCASE) {
            returnList = CollectionUtil.select(returnList, new CollectionUtil.CollectionFilter<ObjectReference>() {
                @Override
                public boolean include(ObjectReference obj) {
                    return !obj.name.matches(".*[A-Z].*");
                }
            });
        } else if (scope.getDatabase().getIdentifierCaseHandling(type, true, scope) == Database.IdentifierCaseHandling.UPPERCASE) {
            returnList = CollectionUtil.select(returnList, new CollectionUtil.CollectionFilter<ObjectReference>() {
                @Override
                public boolean include(ObjectReference obj) {
                    return !obj.name.matches(".*[a-z].*");
                }
            });
        }

        if (AbstractTableObject.class.isAssignableFrom(type)) { //add in a null table name placeholder
            for (ObjectReference ref : returnList) {
                ref.container = new ObjectReference(LiquibaseObject.class, ref.container, (String) null);
            }
        }

        List<ObjectReference> finalReturnList = new ArrayList<>();
        for (ObjectReference reference : returnList) {
            if (reference.instanceOf(ForeignKey.class)) {
                reference = new ForeignKey.ForeignKeyReference(reference.container, reference.name);
            }
            finalReturnList.add(reference);
        }

        return finalReturnList;
    }

    protected List<ObjectReference> getObjectContainers(Scope scope) {
        return scope.get(JUnitScope.Attr.connectionSupplier, ConnectionSupplier.class).getAllSchemas();
    }
}
