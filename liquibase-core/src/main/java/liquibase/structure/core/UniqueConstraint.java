package liquibase.structure.core;

import liquibase.structure.AbstractObject;
import liquibase.structure.AbstractTableObject;
import liquibase.structure.ObjectReference;
import liquibase.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UniqueConstraint extends AbstractTableObject {

	public List<ObjectReference> columns = new ArrayList<>();
	public Boolean deferrable;
	public Boolean initiallyDeferred;
	public Boolean disabled;
	public ObjectReference backingIndex;
	public String tablespace;

	public UniqueConstraint() {
	}

	public UniqueConstraint(String name) {
		super(name);
	}

	public UniqueConstraint(ObjectReference reference) {
		super(reference);
	}

	public UniqueConstraint(ObjectReference table, String name, String... columns) {
		super(table, name);
		for (String column : columns) {
			this.columns.add(new Column.ColumnReference(table, column));
		}
	}

	@Override
	public String toString() {
		return getName() + "(" + StringUtils.join(this.columns, ",", new StringUtils.ToStringFormatter()) + ")";
	}

}