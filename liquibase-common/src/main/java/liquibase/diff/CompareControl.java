package liquibase.diff;

import liquibase.AbstractExtensibleObject;
import liquibase.item.Item;
import liquibase.item.core.SchemaReference;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CompareControl {

    private SchemaMapping[] schemaComparisons;
    private Set<Class<? extends Item>> compareTypes = new HashSet<>();
    private Map<Class<? extends Item>, Set<String>> suppressedFields = new HashMap<>();

    public static CompareControl STANDARD = new CompareControl();


    public CompareControl() {
//        this(null);
    }

//    public CompareControl(Set<Class<? extends Item>> compareTypes) {
//        schemaComparisons = new SchemaMapping[]{new SchemaMapping(null, null)};
//        setTypes(compareTypes);
//    }
//
//    public CompareControl(SchemaMapping[] schemaComparison, Set<Class<? extends Item>> compareTypes) {
//        this.schemaComparisons = schemaComparison;
//        setTypes(compareTypes);
//    }
//
//    public CompareControl(SchemaMapping[] schemaComparison, String compareTypes) {
//        if (schemaComparison != null && schemaComparison.length > 0) {
//            this.schemaComparisons = schemaComparison;
//        } else {
//            this.schemaComparisons = new SchemaMapping[]{new SchemaMapping(new CatalogAndSchema(null, null), new CatalogAndSchema(null, null))};
//        }
//        setTypes(ItemFactory.getInstance().parseTypes(compareTypes));
//    }
//
//    public CompareControl(String[] referenceVsComparisonSchemas, Set<Class<? extends Item>> compareTypes) {
//        String[] splitReferenceSchemas = referenceVsComparisonSchemas[0].split(",");
//        String[] splitComparisonSchemas = referenceVsComparisonSchemas[1].split(",");
//        this.schemaComparisons = new SchemaMapping[splitReferenceSchemas.length];
//        for (int i = 0; i < splitReferenceSchemas.length; i++) {
//            String referenceCatalogName = null;
//            String referenceSchemaName = splitReferenceSchemas[i];
//            if (referenceSchemaName.contains(".")) {
//                referenceCatalogName = referenceSchemaName.split(".", 2)[0];
//                referenceSchemaName = referenceSchemaName.split(".", 2)[1];
//            }
//
//            String comparisonCatalogName = null;
//            String comparisonSchemaName = splitComparisonSchemas[i];
//            if (comparisonSchemaName.contains(".")) {
//                comparisonCatalogName = comparisonSchemaName.split(".", 2)[0];
//                comparisonSchemaName = comparisonSchemaName.split(".", 2)[1];
//            }
//
//            CatalogAndSchema referenceSchema = new CatalogAndSchema(referenceCatalogName, referenceSchemaName);
//            CatalogAndSchema comparisonSchema = new CatalogAndSchema(comparisonCatalogName, comparisonSchemaName);
//            this.schemaComparisons[i] = new SchemaMapping(referenceSchema, comparisonSchema);
//
//            setTypes(compareTypes);
//        }
//    }

//    protected void setTypes(Set<Class<? extends Item>> types) {
//        if (types == null || types.size() == 0) {
//            types = ItemFactory.getInstance().getStandardTypes();
//        }
//        this.compareTypes = types;
//    }

    public Set<Class<? extends Item>> getComparedTypes() {
        return compareTypes;
    }


    public CompareControl addSuppressedField(Class<? extends Item> type, String field) {
        if (!suppressedFields.containsKey(type)) {
            suppressedFields.put(type, new HashSet<String>());
        }
        suppressedFields.get(type).add(field);

        return this;
    }

    public boolean isSuppressedField(Class<? extends Item> type, String field) {
        if (!suppressedFields.containsKey(type)) {
            return false;
        }
        return suppressedFields.get(type).contains(field);
    }

    public SchemaMapping[] getSchemaComparisons() {
        return schemaComparisons;
    }

//    public CatalogAndSchema[] getSchemas(DatabaseRole databaseRole) {
//        CatalogAndSchema[] schemas = new CatalogAndSchema[schemaComparisons.length];
//        for (int i=0; i<schemaComparisons.length; i++) {
//            if (databaseRole.equals(DatabaseRole.COMPARISON)) {
//                schemas[i] = schemaComparisons[i].getComparisonSchema();
//            } else if (databaseRole.equals(DatabaseRole.REFERENCE)) {
//                schemas[i] = schemaComparisons[i].getReferenceSchema();
//            } else {
//                throw new UnexpectedLiquibaseException("Unknkown diff type: " + databaseRole);
//            }
//        }
//        return schemas;
//    }

//    public static enum DatabaseRole {
//        REFERENCE,
//        COMPARISON
//    }

    public static class SchemaMapping extends AbstractExtensibleObject {
        public SchemaReference comparisonSchema;
        public SchemaReference referenceSchema;
        public String outputSchemaAs;

        public SchemaMapping(SchemaReference reference, SchemaReference comparison) {
            this.referenceSchema = reference;
            this.comparisonSchema = comparison;
        }

        public static String convertSchema(String schemaName, SchemaMapping[] schemaComparisons) {
//            if (schemaComparisons == null || schemaComparisons.length == 0 || schemaName == null) {
                return schemaName;
//            }
//
//            String convertedSchemaName = null;
//            for (SchemaMapping comparison : schemaComparisons) {
//                if (schemaName.equals(comparison.comparisonSchema.name)) {
//                    convertedSchemaName = comparison.referenceSchema.name;
//                } else if (schemaName.equals(comparison.getComparisonSchema().getCatalogName())) {
//                    convertedSchemaName = comparison.getReferenceSchema().getCatalogName();
//
//                } else if (schemaName.equals(comparison.getReferenceSchema().getSchemaName())) {
//                    convertedSchemaName = comparison.getComparisonSchema().getSchemaName();
//                } else if (schemaName.equals(comparison.getReferenceSchema().getCatalogName())) {
//                    convertedSchemaName = comparison.getComparisonSchema().getCatalogName();
//                }
//            }
//
//            if (convertedSchemaName == null) {
//                return schemaName;
//            } else {
//                return convertedSchemaName;
//            }
        }
    }
}
