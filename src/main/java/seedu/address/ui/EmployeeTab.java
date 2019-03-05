package seedu.address.ui;

import javafx.scene.control.Tab;
import javafx.scene.layout.Region;
import javafx.scene.Node;
import seedu.address.model.employee.Employee;
import javafx.scene.control.ListView;

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
