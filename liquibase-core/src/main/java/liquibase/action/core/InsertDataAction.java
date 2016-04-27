package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.FunctionCall;
import liquibase.item.core.RowData;
import liquibase.item.function.SequenceCurrentValueFunction;
import liquibase.item.function.SequenceNextValueFunction;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.CollectionUtil;
import liquibase.util.ObjectUtil;

import java.math.BigDecimal;
import java.util.*;


/**
 * Insert data into a database.
 * Data can be conditionally inserted or updated using the {@link #columnsForUpdateCheck}
 */
public class InsertDataAction extends AbstractAction {

    public String dbms;
    public List<RowData> data = new ArrayList<>();

    /**
     * If non-empty, the column names in this list are used to check for previously existing rows that should be updated rather than inserted.
     * Standard implementation uses "MERGE" sql statement, but actual SQL will vary by database.
     * For best cross-databse compatibility, use only primary key columns.
     */
    public List<String> columnsForUpdateCheck = new ArrayList<>();

    public InsertDataAction() {
    }

    public InsertDataAction(List<RowData> data) {
        this.data = data;
    }

    public InsertDataAction(RowData... data) {
        this.data = Arrays.asList(CollectionUtil.createIfNull(data));
    }

    @Override
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[]{
                new AbstractActionPreprocessor(InsertDataAction.class) {

                    @Override
                    protected String[] getAliases() {
                        return new String[]{"insert"};
                    }

                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                        ParsedNode data = actionNode.getChild("data", true);

                        ParsedNode relation = convertToRelationReferenceNode("catalogName", "schemaName", "tableName", actionNode);
                        if (relation != null) {
                            ParsedNode rowData = null;
                            ParsedNode dataNode = null;
                            for (ParsedNode column : actionNode.getChildren("column", false)) {
                                if (rowData == null) {
                                    rowData = data.addChild("rowData");
                                    relation.moveTo(rowData);
                                    dataNode = rowData.addChild("data");
                                }

                                column.rename("rowData").moveTo(dataNode);
                                column.renameChildren("name", "columnName");

                                fixValueNode(column);
                            }
                        }

                    }

                    private void fixValueNode(ParsedNode column) throws ParseException {
                        Map<String, ParsedNode> valueOptions = new HashMap<>();
                        valueOptions.put("value", column.getChild("value", false));
                        valueOptions.put("valueNumeric", column.getChild("valueNumeric", false));
                        valueOptions.put("valueDate", column.getChild("valueDate", false));
                        valueOptions.put("valueBoolean", column.getChild("valueBoolean", false));
                        valueOptions.put("valueComputed", column.getChild("valueComputed", false));
                        valueOptions.put("valueSequenceCurrent", column.getChild("valueSequenceCurrent", false));
                        valueOptions.put("valueSequenceNext", column.getChild("valueSequenceNext", false));

                        valueOptions = CollectionUtil.select(valueOptions, new CollectionUtil.CollectionFilter<Map.Entry<String, ParsedNode>>() {
                            @Override
                            public boolean include(Map.Entry<String, ParsedNode> obj) {
                                return obj.getValue() != null;
                            }
                        });

                        if (valueOptions.size() == 0) {
                            ;//nothing to do
                        } else if (valueOptions.size() > 1) {
                            throw new ParseException("Cannot specify multiple value* attributes", column);
                        } else {
                            Map.Entry<String, ParsedNode> valueOption = valueOptions.entrySet().iterator().next();
                            String valueType = valueOption.getKey();
                            ParsedNode originalValueNode = valueOption.getValue();

                            if (valueType.equals("value")) {
                                ;//nothing do to
                            } else {
                                ParsedNode valueNode = column.addChild("value");
                                switch (originalValueNode.name) {
                                    case "valueNumeric":
                                        valueNode.value = originalValueNode.getValue(null, BigDecimal.class);
                                        break;
                                    case "valueDate":
                                        valueNode.value = originalValueNode.getValue(null, Date.class);
                                        break;
                                    case "valueBoolean":
                                        valueNode.value = originalValueNode.getValue(null, Boolean.class);
                                        break;
                                    case "valueComputed":
                                        valueNode.value = originalValueNode.value;
                                        if (!(valueNode.value instanceof FunctionCall)) {
                                            valueNode.value = new FunctionCall(valueNode.getValue(null, String.class));
                                        }
                                        break;
                                    case "valueSequenceCurrent":
                                        valueNode.value = originalValueNode.value;
                                        if (!(valueNode.value instanceof SequenceCurrentValueFunction)) {
                                            valueNode.value = new SequenceCurrentValueFunction(valueNode.getValue(null, String.class));
                                        }
                                        break;
                                    case "valueSequenceNext":
                                        valueNode.value = originalValueNode.value;
                                        if (!(valueNode.value instanceof SequenceNextValueFunction)) {
                                            valueNode.value = new SequenceNextValueFunction(valueNode.getValue(null, String.class));
                                        }
                                        break;
                                    default:
                                        throw new ParseException("Unknown value attribute: " + originalValueNode.name, originalValueNode);
                                }
                            }
                            Boolean computed = column.getChildValue("computed", Boolean.class, true);
                            if (ObjectUtil.defaultIfNull(computed, false)) {
                                ParsedNode valueNode = column.getChild("valueNode", false);
                                if (valueNode != null && valueNode.value != null && !(valueNode.value instanceof FunctionCall)) {
                                    valueNode.setValue(new FunctionCall(valueNode.value.toString()));
                                }
                            }
                        }
                    }
                }
        };
    }
}
