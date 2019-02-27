package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.employee.Employee;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook extends Observable {

    /**
     * Returns an unmodifiable view of the Employees list.
     * This list will not contain any duplicate Employees.
     */
    ObservableList<Employee> getEmployeeList();

}
