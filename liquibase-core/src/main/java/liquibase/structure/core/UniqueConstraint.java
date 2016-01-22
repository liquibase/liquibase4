package liquibase.structure.core;

import liquibase.structure.AbstractTableObject;
import liquibase.structure.ObjectReference;

import java.util.ArrayList;
import java.util.List;

public class UniqueConstraint extends AbstractTableObject {

	public List<String> columns = new ArrayList<>();
	public Boolean deferrable;
	public Boolean initiallyDeferred;
	public Boolean disabled;
	public ObjectReference backingIndex;
	public String tablespace;

	public UniqueConstraint() {
	}

	public UniqueConstraint(String constraintName) {
		super(constraintName);
	}

	public UniqueConstraint(UniqueConstraintReference reference) {
		super(reference);
	}

	public UniqueConstraint(ObjectReference table, String constraintName) {
		super(table, constraintName);
	}

	public UniqueConstraint(String constraintName, ObjectReference table, String... columns) {
		super(table, constraintName);
		for (String column : columns) {
			this.columns.add(column);
		}
	}

	/**
	 * Object container is the constrained table ObjectReference
	 */
	public static class UniqueConstraintReference extends ObjectReference {

		public UniqueConstraintReference() {
			super(UniqueConstraint.class);
		}

		public UniqueConstraintReference(ObjectReference table, String constraintName) {
			super(UniqueConstraint.class, table, constraintName);
		}

		public ObjectReference getTable() {
			return container;
		}
	}

}