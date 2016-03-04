package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateViewsAction extends AbstractAction {

    public List<View> views = new ArrayList<>();

    public CreateViewsAction() {
    }

    public CreateViewsAction(View... views) {
        if (views != null) {
            this.views.addAll(Arrays.asList(views));
        }
    }

}
