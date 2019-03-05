package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.employee.Employee;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import seedu.address.model.project.Project;

public class SideTabPanel extends UiPart<Region> {
    private static final String FXML = "SideTabPanel.fxml";

    //public EmployeeListPanel employeeListPanel;
    //public ProjectListPanel projectListPanel;

    @javafx.fxml.FXML
    private TabPane tabpane;

    public SideTabPanel() {
        super(FXML);
    }

    public TabPane getPane() {
        return this.tabpane;
    }

}
