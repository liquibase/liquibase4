package liquibase.item;

import liquibase.JUnitScope;
import liquibase.Scope;
import liquibase.database.ConnectionSupplier;
import liquibase.database.Database;
import liquibase.database.core.GenericDatabase;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.plugin.AbstractPlugin;
import liquibase.item.core.Relation;
import liquibase.util.CollectionUtil;
import liquibase.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class TestItemReferenceSupplier<T extends Item> extends AbstractPlugin {

    abstract int getPriority(Class<? extends Item> type, Scope scope);

    List<String> getSimpleItemNames(Class<T> type, Scope scope) {
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

    List<String> getComplexItemNames(Class<T> type, Scope scope) {
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

        if (scope.getDatabase().getIdentifierCaseHandling(type, true, scope) == Database.IdentifierCaseHandling.LOWERCASE) {
            returnList = CollectionUtil.select(returnList, new CollectionUtil.CollectionFilter<String>() {
                @Override
                public boolean include(String obj) {
                    return !obj.matches(".*[A-Z].*");
                }
            });
        } else if (scope.getDatabase().getIdentifierCaseHandling(type, true, scope) == Database.IdentifierCaseHandling.UPPERCASE) {
            returnList = CollectionUtil.select(returnList, new CollectionUtil.CollectionFilter<String>() {
                @Override
                public boolean include(String obj) {
                    return !obj.matches(".*[a-z].*");
                }
            });
        }

        return returnList;
    }

    public List<ItemReference> getItemReferences(Class<T> type, List<? extends ItemReference> containers, ItemNameStrategy nameStrategy, Scope scope) {
        try {
            List<ItemReference> returnList = new ArrayList<>();

            Class<? extends ItemReference> referenceType = type.getConstructor().newInstance().toReference().getClass();

            List<String> baseNames = new ArrayList<>(getItemNames(type, nameStrategy, scope));

            for (ItemReference container : containers) {
                List<String> names = new ArrayList<>(baseNames);
                if (container != null) {
                    if (scope.getDatabase().getIdentifierCaseHandling(type, false, scope) == Database.IdentifierCaseHandling.LOWERCASE) {
                        names.add("only_in_" + container.name.toLowerCase());
                    } else if (scope.getDatabase().getIdentifierCaseHandling(type, false, scope) == Database.IdentifierCaseHandling.UPPERCASE) {
                        names.add("ONLY_IN_" + container.name.toUpperCase());
                    } else {
                        names.add("only_in_" + container.name);
                    }
                }

                for (String name : names) {
                    ItemReference reference = referenceType.newInstance();
                    if (reference.type == null || reference.type.equals(Relation.class)) {
                        reference.type = type;
                    }
                    reference.name = name;
                    reference.container = container;

                    returnList.add(reference);
                }
            }

            return returnList;
        } catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public List<String> getItemNames(Class<T> type, ItemNameStrategy nameStrategy, Scope scope) {
        if (nameStrategy == ItemNameStrategy.COMPLEX_NAMES) {
            return getComplexItemNames(type, scope);
        } else {
            return getSimpleItemNames(type, scope);
        }
    }

    protected List<? extends ItemReference> getObjectContainers(Scope scope) {
        return scope.get(JUnitScope.Attr.connectionSupplier, ConnectionSupplier.class).getAllSchemas();
    }
}
