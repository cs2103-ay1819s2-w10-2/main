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

/**
 * Panel containing the list of Employees.
 */
public class EmployeeListPanel extends UiPart<Region> {
    private static final String FXML = "EmployeeListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EmployeeListPanel.class);

    @FXML
    private ListView<Employee> EmployeeListView;

    public EmployeeListPanel(ObservableList<Employee> EmployeeList, ObservableValue<Employee> selectedEmployee,
            Consumer<Employee> onSelectedEmployeeChange) {
        super(FXML);
        EmployeeListView.setItems(EmployeeList);
        EmployeeListView.setCellFactory(listView -> new EmployeeListViewCell());
        EmployeeListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in employee list panel changed to : '" + newValue + "'");
            onSelectedEmployeeChange.accept(newValue);
        });
        selectedEmployee.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected employee changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected employee,
            // otherwise we would have an infinite loop.
            if (Objects.equals(EmployeeListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                EmployeeListView.getSelectionModel().clearSelection();
            } else {
                int index = EmployeeListView.getItems().indexOf(newValue);
                EmployeeListView.scrollTo(index);
                EmployeeListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Employee} using a {@code EmployeeCard}.
     */
    class EmployeeListViewCell extends ListCell<Employee> {
        @Override
        protected void updateItem(Employee Employee, boolean empty) {
            super.updateItem(Employee, empty);

            if (empty || Employee == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EmployeeCard(Employee, getIndex() + 1).getRoot());
            }
        }
    }

}
