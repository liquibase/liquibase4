package liquibase.item;

import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.core.GenericDatabase;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.plugin.AbstractPlugin;
import liquibase.item.core.Relation;
import liquibase.util.CollectionUtil;
import liquibase.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates {@link DatabaseObject}s for test.
 * This implementation will create names and references for all database object types, so there is no need to subclass unless you want non-standard logic for any types.
 */
public class TestDatabaseObjectSupplier<T extends DatabaseObject> extends AbstractPlugin implements TestItemSupplier<T> {

    public int getPriority(Class<T> type, Scope scope) {
        return PRIORITY_DEFAULT;
    }

    /**
     * Calls to {@link #getSimpleNames(Class, Scope)} or {@link #getComplexNames(Class, Scope)} depending on the passed {@link NameStrategy}
     */
    @Override
    public List<String> getNames(Class<T> type, NameStrategy nameStrategy, Scope scope) {
        if (nameStrategy == NameStrategy.COMPLEX_NAMES) {
            return getComplexNames(type, scope);
        } else {
            return getSimpleNames(type, scope);
        }
    }

    /**
     * Returns a list of simple names for the given type. Used by {@link #getNames(Class, NameStrategy, Scope)} for {@link NameStrategy#SIMPLE_NAMES}.
     */
    protected List<String> getSimpleNames(Class<T> type, Scope scope) {
        List<String> returnList = new ArrayList<>();

        int objectsToCreate = 10;

        Database database = ObjectUtil.defaultIfNull(scope.getDatabase(), new GenericDatabase());
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

    /**
     * Returns a list of complex names for the given type. Used by {@link #getNames(Class, NameStrategy, Scope)} for {@link NameStrategy#COMPLEX_NAMES}.
     */
    protected  List<String> getComplexNames(Class<T> type, Scope scope) {
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

    /**
     * Uses {@link #getNames(Class, NameStrategy, Scope)} to construct references, plus if containers has non-null values, adds object names "ONLY_IN_X" where X is the container's name.
     */
    @Override
    public List<ItemReference> getReferences(Class<T> type, List<? extends ItemReference> containers, NameStrategy nameStrategy, Scope scope) {
        try {
            List<ItemReference> returnList = new ArrayList<>();
            if (containers == null) {
                containers = new ArrayList<>();
                containers.add(null);
            }

            Class<? extends ItemReference> referenceType = type.getConstructor().newInstance().toReference().getClass();

            List<String> baseNames = new ArrayList<>(getNames(type, nameStrategy, scope));

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
}
