package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.core.InsertDataAction;
import liquibase.action.core.LoadDataAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.Column;
import liquibase.item.core.RowData;
import liquibase.item.datatype.DataType;
import liquibase.resource.InputStreamList;
import liquibase.util.CollectionUtil;
import liquibase.util.ObjectUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

/**
 * Default implementation for {@link LoadDataAction} uses https://commons.apache.org/proper/commons-csv/ as the file parser.
 * However, that implementation is wrapped behind a {@link liquibase.actionlogic.core.LoadDataLogic.CsvParser} and related interfaces
 * so the actual implementation can be swapped out as needed.
 */
public class LoadDataLogic extends AbstractActionLogic<LoadDataAction> {

    public static final Character DEFAULT_COMMENT_PATTERN = '#';

    @Override
    protected Class<? extends LoadDataAction> getSupportedAction() {
        return LoadDataAction.class;
    }

    @Override
    public boolean executeInteractsExternally(LoadDataAction action, Scope scope) {
        return true;
    }

    @Override
    public ValidationErrors validate(LoadDataAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("path")
                .checkRequiredFields("table", "table.name");
    }


    @Override
    public ActionStatus checkStatus(LoadDataAction action, Scope scope) {
        return new ActionStatus().nothingToCheck();
    }

    @Override
    public ActionResult execute(LoadDataAction action, Scope scope) throws ActionPerformException {
        Reader data = getData(action, scope);
        try {
            CsvParser parser = createParser(action, scope);
            CsvFile records = parser.parse(data);

            String[] headers = records.getHeaders();
            LoadDataAction.LoadDataColumn[] columnConfigs = new LoadDataAction.LoadDataColumn[headers.length];

            for (int i = 0; i < headers.length; i++) {
                columnConfigs[i] = getColumnConfig(headers[i], i, action, scope);
            }

            InsertDataAction insertAction = new InsertDataAction();
            insertAction.columnsForUpdateCheck = action.columnsForUpdateCheck;
            for (CsvRecord record : records) {
                RowData rowData = new RowData(action.table);
                for (int i = 0; i < headers.length; i++) {
                    String header = headers[i];
                    LoadDataAction.LoadDataColumn column = columnConfigs[i];

                    if (ObjectUtil.defaultIfNull(column.skip, false)) {
                        continue;
                    }
                    rowData.add(column.columnName, getValue(record.get(header), column, action, scope), column.dataType);
                }
                insertAction.data.add(rowData);
            }
            return new DelegateResult(action, null, insertAction);

        } catch (Throwable e) {
            throw new ActionPerformException(e.getMessage(), e);
        }
    }

    protected CsvParser createParser(LoadDataAction action, Scope scope) {
        CSVFormat format;
        if (action.path.toLowerCase().endsWith(".tsv")) {
            format = CSVFormat.TDF;
        } else {
            format = CSVFormat.DEFAULT;
        }
        format = format
                .withHeader()
                .withIgnoreEmptyLines()
                .withIgnoreSurroundingSpaces()
                .withCommentMarker(action.commentLineStart == null ? DEFAULT_COMMENT_PATTERN : action.commentLineStart.charAt(0))
                .withEscape('\\')
                .withNullString("NULL");

        return new CommonsCsvParser(format);
    }

    protected LoadDataAction.LoadDataColumn getColumnConfig(String header, int index, LoadDataAction action, Scope scope) {
        LoadDataAction.LoadDataColumn foundColumn = null;
        for (LoadDataAction.LoadDataColumn column : CollectionUtil.createIfNull(action.columns)) {
            if (column.index != null && column.index == index) {
                foundColumn = column;
                break;
            } else if (column.header != null && header != null && column.header.equalsIgnoreCase(header)) {
                foundColumn = column;
                break;
            }
        }

        LoadDataAction.LoadDataColumn column;
        if (foundColumn == null) {
            column = new LoadDataAction.LoadDataColumn(header, null);
        } else {
            column = (LoadDataAction.LoadDataColumn) foundColumn.clone();
        }

        if (column.columnName == null) {
            column.columnName = createColumnName(header, scope);
        }
        if (column.dataType == null) {
            column.dataType = new DataType((String) null);
        }

        return column;

    }

    protected Object getValue(String originalValue, LoadDataAction.LoadDataColumn column, LoadDataAction action, Scope scope) {
        try {
            if (column.dataType.standardType == null || String.class.equals(column.dataType.standardType.valueType)) {
                return originalValue;
            } else {
                Method valueOfMethod = column.dataType.standardType.valueType.getMethod("valueOf", String.class);
                return valueOfMethod.invoke(null, originalValue);
            }
        } catch (Exception e) {
            return originalValue;
        }
    }

    protected Reader getData(LoadDataAction action, Scope scope) throws ActionPerformException {
        try {
            InputStream inputStream = scope.getResourceAccessor().openStream(action.path);

            String encoding = action.encoding;
            if (encoding == null) {
                return new InputStreamReader(inputStream);
            } else {
                return new InputStreamReader(inputStream, encoding);
            }
        } catch (Exception e) {
            throw new ActionPerformException(e);
        }
    }

    protected String createColumnName(String header, Scope scope) {
        if (header == null) {
            return null;
        }

        Database database = scope.getDatabase();
        if (database == null) {
            return header;
        }

        switch (database.getIdentifierCaseHandling(Column.class, false, scope)) {
            case LOWERCASE:
                return header.toLowerCase();
            case UPPERCASE:
                return header.toUpperCase();
            default:
                return header;
        }
    }

    public interface CsvParser {
        CsvFile parse(Reader reader) throws IOException;
    }

    public interface CsvFile extends Iterable<CsvRecord> {

        String[] getHeaders();
    }

    public interface CsvRecord {

        String get(String header);
    }


    public static class CommonsCsvParser implements CsvParser {
        private CSVFormat format;

        public CommonsCsvParser(CSVFormat format) {
            this.format = format;
        }

        public CsvFile parse(Reader reader) throws IOException {
            return new CommonsCsvFile(format.parse(reader));
        }

        @Override
        public String toString() {
            return "CSV Parser:"
                    + " delimiter=" + format.getDelimiter()
                    + " commentMarker=" + format.getCommentMarker()
                    + " nullString=" + format.getNullString()
                    + " escapeChar=" + format.getEscapeCharacter()
                    + " ignoreEmptyLines=" + format.getIgnoreEmptyLines()
                    + " ignoreSurroundingSpaces=" + format.getIgnoreSurroundingSpaces()
                    + " quoteCharacter=" + format.getQuoteCharacter()
                    ;
        }
    }


    public static class CommonsCsvFile implements CsvFile {
        private org.apache.commons.csv.CSVParser records;
        private String[] headers;

        public CommonsCsvFile(org.apache.commons.csv.CSVParser parser) {
            this.records = parser;

            this.headers = new String[records.getHeaderMap().size()];
            for (Map.Entry<String, Integer> header : records.getHeaderMap().entrySet()) {
                headers[header.getValue()] = header.getKey();

            }
        }

        @Override
        public String[] getHeaders() {
            return headers;
        }

        @Override
        public Iterator<CsvRecord> iterator() {
            return new IteratorAdapter(records.iterator());
        }

        private class IteratorAdapter implements Iterator<CsvRecord> {

            private final Iterator<CSVRecord> iterator;

            public IteratorAdapter(Iterator<CSVRecord> iterator) {
                this.iterator = iterator;
            }

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public CsvRecord next() {
                return new CommonsCsvRecord(iterator.next());
            }

            @Override
            public void remove() {
                iterator.remove();
            }
        }
    }

    private static class CommonsCsvRecord implements CsvRecord {

        private final CSVRecord record;

        public CommonsCsvRecord(CSVRecord record) {
            this.record = record;
        }

        @Override
        public String get(String header) {
            return record.get(header);
        }
    }

}
