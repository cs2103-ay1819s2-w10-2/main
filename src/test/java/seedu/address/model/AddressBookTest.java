package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_JAVA;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.getTypicalAddressBookWithEmployees;
import static seedu.address.testutil.TypicalProjects.PROJECT_ALICE;

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
import seedu.address.model.project.Project;
import seedu.address.testutil.EmployeeBuilder;
import seedu.address.testutil.ProjectBuilder;
import seedu.address.testutil.TypicalProjects;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {

        assertEquals(Collections.emptyList(), addressBook.getEmployeeList());
        assertEquals(Collections.emptyList(), addressBook.getProjectList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = TypicalProjects.addTypicalProjects(getTypicalAddressBookWithEmployees());
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateEmployees_throwsDuplicateEmployeeException() {
        // Two employees with the same identity fields
        Employee editedAlice = new EmployeeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withSkills(VALID_SKILL_JAVA)
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
    public void hasProject_nullProject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasProject(null);
    }



    @Test
    public void hasEmployee_employeeNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasEmployee(ALICE));
    }

    @Test
    public void hasProject_projectNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasProject(PROJECT_ALICE));
    }

    @Test
    public void hasEmployee_employeeInAddressBook_returnsTrue() {
        addressBook.addEmployee(ALICE);
        assertTrue(addressBook.hasEmployee(ALICE));
    }

    @Test
    public void hasProject_projectInAddressBook_returnsTrue() {
        addressBook.addProject(PROJECT_ALICE);
        assertTrue(addressBook.hasProject(PROJECT_ALICE));
    }

    @Test
    public void hasEmployee_employeeWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addEmployee(ALICE);
        Employee editedAlice = new EmployeeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withSkills(VALID_SKILL_JAVA)
                .build();
        assertTrue(addressBook.hasEmployee(editedAlice));
    }

    @Test
    public void hasProject_projectWithSameNameInAddressBook_returnsTrue() {
        addressBook.addProject(PROJECT_ALICE);
        Project editedAlice = new ProjectBuilder(PROJECT_ALICE).withClient(VALID_CLIENT_BOB)
                .withDeadline(VALID_DEADLINE_AMY)
                .build();
        assertTrue(addressBook.hasProject(editedAlice));
    }

    @Test
    public void getEmployeeList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getEmployeeList().remove(0);
    }

    @Test
    public void getProjectList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getProjectList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        addressBook.addListener(listener);
        addressBook.addEmployee(ALICE);
        assertEquals(1, counter.get());
        addressBook.addProject(PROJECT_ALICE);
        assertEquals(2, counter.get());

    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        addressBook.addListener(listener);
        addressBook.removeListener(listener);
        addressBook.addEmployee(ALICE);
        addressBook.addProject(PROJECT_ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyAddressBook whose employees/projects list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Employee> employees = FXCollections.observableArrayList();
        private final ObservableList<Project> projects = FXCollections.observableArrayList();

        AddressBookStub(Collection<Employee> employees) {
            this.projects.setAll(projects);
            this.employees.setAll(employees);
        }


        @Override
        public ObservableList<Employee> getEmployeeList() {
            return employees;
        }

        @Override
        public ObservableList<Project> getProjectList() {
            return projects;
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
