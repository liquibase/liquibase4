package liquibase.action;

import java.util.Map;

public class MockAction extends AbstractAction {

    public MockAction() {
    }

    public MockAction(Map<String, ?> values) {
        for (Map.Entry<String, ?> entry: values.entrySet()) {
            this.set(entry.getKey(), entry.getValue());
        }
    }
}
