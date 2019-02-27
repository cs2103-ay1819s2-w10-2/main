package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EmployeeS;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.BENSON;
import static seedu.address.testutil.TypicalEmployees.BOB;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.employee.NameContainsKeywordsPredicate;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.exceptions.EmployeeNotFoundException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.EmployeeBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(null, modelManager.getSelectedEmployee());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setAddressBookFilePath(null);
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasEmployee_nullEmployee_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasEmployee(null);
    }

    @Test
    public void hasEmployee_EmployeeNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasEmployee(ALICE));
    }

    @Test
    public void hasEmployee_EmployeeInAddressBook_returnsTrue() {
        modelManager.addEmployee(ALICE);
        assertTrue(modelManager.hasEmployee(ALICE));
    }

    @Test
    public void deleteEmployee_EmployeeIsSelectedAndFirstEmployeeInFilteredEmployeeList_selectionCleared() {
        modelManager.addEmployee(ALICE);
        modelManager.setSelectedEmployee(ALICE);
        modelManager.deleteEmployee(ALICE);
        assertEquals(null, modelManager.getSelectedEmployee());
    }

    @Test
    public void deleteEmployee_EmployeeIsSelectedAndSecondEmployeeInFilteredEmployeeList_firstEmployeeSelected() {
        modelManager.addEmployee(ALICE);
        modelManager.addEmployee(BOB);
        assertEquals(Arrays.asList(ALICE, BOB), modelManager.getFilteredEmployeeList());
        modelManager.setSelectedEmployee(BOB);
        modelManager.deleteEmployee(BOB);
        assertEquals(ALICE, modelManager.getSelectedEmployee());
    }

    @Test
    public void setEmployee_EmployeeIsSelected_selectedEmployeeUpdated() {
        modelManager.addEmployee(ALICE);
        modelManager.setSelectedEmployee(ALICE);
        Employee updatedAlice = new EmployeeBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        modelManager.setEmployee(ALICE, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedEmployee());
    }

    @Test
    public void getFilteredEmployeeList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredEmployeeList().remove(0);
    }

    @Test
    public void setSelectedEmployee_EmployeeNotInFilteredEmployeeList_throwsEmployeeNotFoundException() {
        thrown.expect(EmployeeNotFoundException.class);
        modelManager.setSelectedEmployee(ALICE);
    }

    @Test
    public void setSelectedEmployee_EmployeeInFilteredEmployeeList_setsSelectedEmployee() {
        modelManager.addEmployee(ALICE);
        assertEquals(Collections.singletonList(ALICE), modelManager.getFilteredEmployeeList());
        modelManager.setSelectedEmployee(ALICE);
        assertEquals(ALICE, modelManager.getSelectedEmployee());
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withEmployee(ALICE).withEmployee(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredEmployeeList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_EmployeeS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
