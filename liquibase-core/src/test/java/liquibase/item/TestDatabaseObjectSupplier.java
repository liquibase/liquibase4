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
     * Returns a list of various names, some complex, for the given type.
     */
    @Override
    public List<String> getNames(Class<T> type, Scope scope) {
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
     * Uses {@link TestItemSupplier#getNames(Class, Scope)} to construct references, plus if containers has non-null values, adds object names "ONLY_IN_X" where X is the container's name.
     */
    @Override
    public List<ItemReference> getReferences(Class<T> type, List<? extends ItemReference> containers, Scope scope) {
        try {
            List<ItemReference> returnList = new ArrayList<>();
            if (containers == null) {
                containers = new ArrayList<>();
                containers.add(null);
            }

            Class<? extends ItemReference> referenceType = type.getConstructor().newInstance().toReference().getClass();

            List<String> baseNames = new ArrayList<>(getNames(type, scope));

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
