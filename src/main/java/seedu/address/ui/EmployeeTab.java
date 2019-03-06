package seedu.address.ui;

import javafx.scene.control.Tab;
import javafx.scene.Node;
import javafx.scene.layout.Region;

/**
 * Tab to contain the employee list
 */
public class EmployeeTab extends UiPart<Region> {
    private static final String FXML = "EmployeeTab.fxml";

    @javafx.fxml.FXML
    private Tab tab;

    public EmployeeTab(Node value) {
        super(FXML);
        this.tab = new Tab("Employee", value);
    }

    public Tab getTab() {
        return this.tab;
    }

}
