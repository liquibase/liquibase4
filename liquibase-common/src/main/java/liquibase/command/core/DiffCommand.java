package liquibase.command.core;

import liquibase.ExtensibleObjectAttribute;
import liquibase.Scope;
import liquibase.command.AbstractSnapshotCommand;
import liquibase.command.CommandResult;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.diff.*;
import liquibase.exception.LiquibaseException;
import liquibase.item.Item;
import liquibase.item.ItemReference;
import liquibase.snapshot.Snapshot;
import liquibase.util.StringUtil;

import java.io.PrintStream;
import java.util.*;

public class DiffCommand extends AbstractSnapshotCommand<DiffCommand.DiffCommandResult> {

    @ExtensibleObjectAttribute(description = "Reference database url", required = true)
    public String referenceUrl;

    @ExtensibleObjectAttribute(description = "Reference database username")
    public String referenceUsername;

    @ExtensibleObjectAttribute(description = "Reference database password")
    public String referencePassword;

    @ExtensibleObjectAttribute(description = "Reference database driver")
    public String referenceDriver;

    @ExtensibleObjectAttribute(description = "Reference Liquibase Database class to use")
    public String referenceDatabaseClass;

    @Override
    public String getName() {
        return "diff";
    }

    @Override
    protected DiffCommandResult run(Scope scope) throws Exception {

        Scope referenceScope = setupReferenceDatabase(scope);
        Scope currentScope = setupDatabase(scope);

        Snapshot referenceSnapshot = snapshot(referenceScope);
        Snapshot currentSnapshot = snapshot(currentScope);

        Scope comparisonScope = currentScope.child(Scope.Attr.referenceDatabase, referenceScope.getDatabase());

        DiffCommandResult commandResult = new DiffCommandResult();
        commandResult.referenceDatabase = referenceScope.getDatabase();
        commandResult.currentDatabase = currentScope.getDatabase();
        commandResult.diffResult = comparisonScope.getSingleton(DiffResultFactory.class).diff(referenceSnapshot, currentSnapshot, comparisonScope);

        return commandResult;
    }

    /**
     * Returns a scope with with the referenceDatabase attribute set.
     * <p/>
     * If the passed scope already has a database, returns the same scope object. Otherwise, creates a new database object from the paramaters in this command.
     */
    protected Scope setupReferenceDatabase(Scope scope) throws LiquibaseException {
        if (scope.getDatabase() == null) {
            DatabaseConnection.ConnectionParameters connectionParameters = new DatabaseConnection.ConnectionParameters();
            connectionParameters.url = referenceUrl;
            connectionParameters.username = referenceUsername;
            connectionParameters.password = referencePassword;
            connectionParameters.driverClass = referenceDriver;
            connectionParameters.additionalDriverPropertiesPath = null; //todo
            connectionParameters.driverPropertiesClass = null; //todo

            scope = scope.child(Scope.Attr.database, scope.getSingleton(DatabaseFactory.class).connect(connectionParameters, referenceDatabaseClass, scope));
        }
        return scope;
    }


    public static class DiffCommandResult extends CommandResult {

        public DiffResult diffResult;
        public Database referenceDatabase;
        public Database currentDatabase;

        public DiffCommandResult() {
        }

        public DiffCommandResult(DiffResult diffResult) {
            this.diffResult = diffResult;
        }

        public DiffCommandResult(String message) {
            super(message);
        }

        public DiffCommandResult(String message, boolean succeeded) {
            super(message, succeeded);
        }

        @Override
        public String print(Scope scope) throws LiquibaseException {
            StringBuilder out = new StringBuilder();

            out.append("Reference Database: ").append(referenceDatabase).append(scope.getLineSeparator());
            out.append("Comparison Database: ").append(currentDatabase).append(scope.getLineSeparator());

            printComparison("Product Name", diffResult.getSnapshotDifference("databaseProductName"), out, scope);
            printComparison("Product Version", diffResult.getSnapshotDifference("databaseProductVersion"), out, scope);

            out.append(scope.getLineSeparator());

            Comparator<Item> comparator = new Comparator<Item>() {
                @Override
                public int compare(Item o1, Item o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            };

            for (Class<? extends Item> type : diffResult.comparedTypes) {
                printSetComparison("Missing " + getTypeName(type) + "(s)", diffResult.getMissingObjects(type, comparator), out, scope);
                printSetComparison("Unexpected " + getTypeName(type) + "(s)", diffResult.getUnexpectedObjects(type, comparator), out, scope);

                printChangedComparison("Changed " + getTypeName(type) + "(s)", diffResult.getChangedObjects(type, comparator), out, scope);

            }

            return out.toString();
        }

        protected String getTypeName(Class<? extends Item> type) {
            return type.getSimpleName().replaceAll("([A-Z])", " $1").trim();
        }


        protected void printComparison(String title, StringDiff string, StringBuilder out, Scope scope) {
            out.append(title).append(":");

            if (string == null) {
                out.append(" NULL").append(scope.getLineSeparator());
                return;
            }

            if (string.areEqual()) {
                out.append(" EQUAL").append(scope.getLineSeparator());
            } else {
                String referenceVersion = string.getReferenceVersion();
                if (referenceVersion == null) {
                    referenceVersion = "NULL";
                } else {
                    referenceVersion = "'" + referenceVersion + "'";
                }

                String targetVersion = string.getCurrentVersion();
                if (targetVersion == null) {
                    targetVersion = "NULL";
                } else {
                    targetVersion = "'" + targetVersion + "'";
                }


                out.append(scope.getLineSeparator());
                out.append(StringUtil.indent("Reference: ")).append(referenceVersion).append(scope.getLineSeparator());
                out.append(StringUtil.indent("Current:   ")).append(targetVersion).append(scope.getLineSeparator());
            }

        }

        protected void printSetComparison(String title, Set<? extends Item> objects, StringBuilder out, Scope scope) {
            out.append(title).append(": ");
//            Schema lastSchema = null;
            if (objects.size() == 0) {
                out.append("NONE").append(scope.getLineSeparator());
            } else {
                out.append(scope.getLineSeparator());
                SortedSet<ItemReference> containers = new TreeSet<>();
                for (Item object : objects) {
//                    if (getIncludeSchema() && object.getSchema() != null && (lastSchema == null || !lastSchema.equals(object.getSchema()))) {
//                        lastSchema = object.getSchema();
//                        String schemaName = object.getSchema().getName();
//                        if (schemaName == null) {
//                            schemaName = object.getSchema().getCatalogName();
//                        }
//                        schemaName = includeSchemaComparison(schemaName);
//
//                        out.println("  SCHEMA: " + schemaName);
//                    }
                    ItemReference container = object.toReference().container;
                    if (container == null) {
                        out.append(StringUtil.indent(object.toReference().toString())).append(scope.getLineSeparator());
                    } else {
                        containers.add(container);
                    }
                }
                for (ItemReference container : containers) {
                    String containerTitle = container.toString() + ":";
                    if (container.type != null) {
                        containerTitle = getTypeName(container.type)+" " + containerTitle;
                    }
                    out.append(StringUtil.indent(containerTitle)).append(scope.getLineSeparator());

                    for (Item object : objects) {
                        ItemReference objectContainer = object.toReference().container;
                        if (container.equals(objectContainer)) {
                            out.append(StringUtil.indent(object.getName(), 8)).append(scope.getLineSeparator());
                        }
                    }
                }
            }
        }

        protected void printChangedComparison(String title, Map<? extends Item, ObjectDifferences> objects, StringBuilder out, Scope scope) {
            out.append(title).append(": ");
            if (objects.size() == 0) {
                out.append("NONE").append(scope.getLineSeparator());
            } else {
                out.append(scope.getLineSeparator());
                for (Map.Entry<? extends Item, ObjectDifferences> object : objects.entrySet()) {
                    if (object.getValue().hasDifferences()) {
                        out.append(StringUtil.indent(object.getKey().toReference().toString())).append(scope.getLineSeparator());
                        for (Difference difference : object.getValue().getDifferences()) {
                            out.append(StringUtil.indent(difference.toString(), 8)).append(scope.getLineSeparator());
                        }
                    }
                }
            }
        }

    }
}
