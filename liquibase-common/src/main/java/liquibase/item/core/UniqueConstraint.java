package liquibase.item.core;

import liquibase.item.AbstractRelationBasedObject;

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

}