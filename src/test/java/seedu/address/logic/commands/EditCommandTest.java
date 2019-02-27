package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEmployeeAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_Employee;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_Employee;
import static seedu.address.testutil.TypicalEmployees.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditEmployeeDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.employee.Employee;
import seedu.address.testutil.EditEmployeeDescriptorBuilder;
import seedu.address.testutil.EmployeeBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Employee editedEmployee = new EmployeeBuilder().build();
        EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder(editedEmployee).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_Employee, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_Employee_SUCCESS, editedEmployee);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEmployee(model.getFilteredEmployeeList().get(0), editedEmployee);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastEmployee = Index.fromOneBased(model.getFilteredEmployeeList().size());
        Employee lastEmployee = model.getFilteredEmployeeList().get(indexLastEmployee.getZeroBased());

        EmployeeBuilder EmployeeInList = new EmployeeBuilder(lastEmployee);
        Employee editedEmployee = EmployeeInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastEmployee, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_Employee_SUCCESS, editedEmployee);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEmployee(lastEmployee, editedEmployee);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_Employee, new EditEmployeeDescriptor());
        Employee editedEmployee = model.getFilteredEmployeeList().get(INDEX_FIRST_Employee.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_Employee_SUCCESS, editedEmployee);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showEmployeeAtIndex(model, INDEX_FIRST_Employee);

        Employee EmployeeInFilteredList = model.getFilteredEmployeeList().get(INDEX_FIRST_Employee.getZeroBased());
        Employee editedEmployee = new EmployeeBuilder(EmployeeInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_Employee,
                new EditEmployeeDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_Employee_SUCCESS, editedEmployee);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEmployee(model.getFilteredEmployeeList().get(0), editedEmployee);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateEmployeeUnfilteredList_failure() {
        Employee firstEmployee = model.getFilteredEmployeeList().get(INDEX_FIRST_Employee.getZeroBased());
        EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder(firstEmployee).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_Employee, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_Employee);
    }

    @Test
    public void execute_duplicateEmployeeFilteredList_failure() {
        showEmployeeAtIndex(model, INDEX_FIRST_Employee);

        // edit employee in filtered list into a duplicate in address book
        Employee EmployeeInList = model.getAddressBook().getEmployeeList().get(INDEX_SECOND_Employee.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_Employee,
                new EditEmployeeDescriptorBuilder(EmployeeInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_Employee);
    }

    @Test
    public void execute_invalidEmployeeIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEmployeeList().size() + 1);
        EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_Employee_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidEmployeeIndexFilteredList_failure() {
        showEmployeeAtIndex(model, INDEX_FIRST_Employee);
        Index outOfBoundIndex = INDEX_SECOND_Employee;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEmployeeList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditEmployeeDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_Employee_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Employee editedEmployee = new EmployeeBuilder().build();
        Employee EmployeeToEdit = model.getFilteredEmployeeList().get(INDEX_FIRST_Employee.getZeroBased());
        EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder(editedEmployee).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_Employee, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEmployee(EmployeeToEdit, editedEmployee);
        expectedModel.commitAddressBook();

        // edit -> first employee edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered employee list to show all Employees
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first employee edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEmployeeList().size() + 1);
        EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_Employee_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Employee} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited employee in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the employee object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameEmployeeEdited() throws Exception {
        Employee editedEmployee = new EmployeeBuilder().build();
        EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder(editedEmployee).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_Employee, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showEmployeeAtIndex(model, INDEX_SECOND_Employee);
        Employee EmployeeToEdit = model.getFilteredEmployeeList().get(INDEX_FIRST_Employee.getZeroBased());
        expectedModel.setEmployee(EmployeeToEdit, editedEmployee);
        expectedModel.commitAddressBook();

        // edit -> edits second employee in unfiltered employee list / first employee in filtered employee list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered employee list to show all Employees
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredEmployeeList().get(INDEX_FIRST_Employee.getZeroBased()), EmployeeToEdit);
        // redo -> edits same second employee in unfiltered employee list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_Employee, DESC_AMY);

        // same values -> returns true
        EditEmployeeDescriptor copyDescriptor = new EditEmployeeDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_Employee, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_Employee, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_Employee, DESC_BOB)));
    }

}
