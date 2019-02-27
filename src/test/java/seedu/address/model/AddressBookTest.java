package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.exceptions.DuplicateEmployeeException;
import seedu.address.testutil.EmployeeBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getEmployeeList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateEmployees_throwsDuplicateEmployeeException() {
        // Two Employees with the same identity fields
        Employee editedAlice = new EmployeeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Employee> newEmployees = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newEmployees);

        thrown.expect(DuplicateEmployeeException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasEmployee_nullEmployee_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasEmployee(null);
    }

    @Test
    public void hasEmployee_EmployeeNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasEmployee(ALICE));
    }

    @Test
    public void hasEmployee_EmployeeInAddressBook_returnsTrue() {
        addressBook.addEmployee(ALICE);
        assertTrue(addressBook.hasEmployee(ALICE));
    }

    @Test
    public void hasEmployee_EmployeeWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addEmployee(ALICE);
        Employee editedAlice = new EmployeeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasEmployee(editedAlice));
    }

    @Test
    public void getEmployeeList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getEmployeeList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        addressBook.addListener(listener);
        addressBook.addEmployee(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        addressBook.addListener(listener);
        addressBook.removeListener(listener);
        addressBook.addEmployee(ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyAddressBook whose Employees list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Employee> Employees = FXCollections.observableArrayList();

        AddressBookStub(Collection<Employee> Employees) {
            this.Employees.setAll(Employees);
        }

        @Override
        public ObservableList<Employee> getEmployeeList() {
            return Employees;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
