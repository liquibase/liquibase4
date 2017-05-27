package liquibase.item.core;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.item.AbstractRelationBasedObject;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.item.AbstractItemPreprocessor;
import liquibase.parser.unprocessor.AbstractItemUnprocessor;
import liquibase.parser.unprocessor.ParsedNodeUnprocessor;

import java.util.ArrayList;
import java.util.List;

public class UniqueConstraint extends AbstractRelationBasedObject<UniqueConstraintReference> {

	public List<String> columns = new ArrayList<>();
	public Boolean deferrable;
	public Boolean initiallyDeferred;
	public Boolean disabled;
	public IndexReference backingIndex;
	public String tablespace;

	public UniqueConstraint() {
	}

	public UniqueConstraint(String constraintName) {
		super(Table.class, constraintName);
	}

	public UniqueConstraint(String constraintName, RelationReference relation) {
		super(constraintName, relation);
	}

	public UniqueConstraint(String constraintName, RelationReference relation, String... columns) {
		super(constraintName, relation);
		for (String column : columns) {
			this.columns.add(column);
		}
	}

	@Override
	public UniqueConstraintReference toReference() {
		return new UniqueConstraintReference(name, relation);
	}


	@Override
	public ParsedNodePreprocessor createPreprocessor() {
		return new AbstractItemPreprocessor(UniqueConstraint.class) {
			@Override
			protected void processItemNode(ParsedNode itemNode, Scope scope) throws ParseException {
				ParsedNode backingIndex = convertToIndexReferenceNode("backingIndexCatalogName", "backingIndexSchemaName", "backingIndexTableName", "backingIndexName", itemNode);
				if (backingIndex != null) {
					backingIndex.rename("backingIndex");
				}
                itemNode.renameChildren("tableName", "relationName");
                convertToRelationReferenceNode("catalogName", "schemaName", "relationName", itemNode);


				groupChildren("columns", itemNode, "column");
			}
		};
	}

	@Override
	public ParsedNodeUnprocessor createUnprocessor() {
		return new AbstractItemUnprocessor(UniqueConstraint.class) {
			@Override
			protected void unprocessItem(ParsedNode typeNode, Scope scope) throws ParseException {
				ParsedNode columnsNode = typeNode.getChild("columns", false);
				if (columnsNode != null) {
					columnsNode.renameChildren("value", "column");
				}
			}
		};
	}
}