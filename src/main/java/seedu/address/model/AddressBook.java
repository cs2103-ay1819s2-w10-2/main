package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.UniqueEmployeeList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameEmployee comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueEmployeeList Employees;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        Employees = new UniqueEmployeeList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Employees in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the employee list with {@code Employees}.
     * {@code Employees} must not contain duplicate Employees.
     */
    public void setEmployees(List<Employee> Employees) {
        this.Employees.setEmployees(Employees);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setEmployees(newData.getEmployeeList());
    }

    //// employee-level operations

    /**
     * Returns true if a employee with the same identity as {@code employee} exists in the address book.
     */
    public boolean hasEmployee(Employee Employee) {
        requireNonNull(Employee);
        return Employees.contains(Employee);
    }

    /**
     * Adds a employee to the address book.
     * The employee must not already exist in the address book.
     */
    public void addEmployee(Employee p) {
        Employees.add(p);
        indicateModified();
    }

    /**
     * Replaces the given employee {@code target} in the list with {@code editedEmployee}.
     * {@code target} must exist in the address book.
     * The employee identity of {@code editedEmployee} must not be the same as another existing employee in the address book.
     */
    public void setEmployee(Employee target, Employee editedEmployee) {
        requireNonNull(editedEmployee);

        Employees.setEmployee(target, editedEmployee);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeEmployee(Employee key) {
        Employees.remove(key);
        indicateModified();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the address book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return Employees.asUnmodifiableObservableList().size() + " Employees";
        // TODO: refine later
    }

    @Override
    public ObservableList<Employee> getEmployeeList() {
        return Employees.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && Employees.equals(((AddressBook) other).Employees));
    }

    @Override
    public int hashCode() {
        return Employees.hashCode();
    }
}
