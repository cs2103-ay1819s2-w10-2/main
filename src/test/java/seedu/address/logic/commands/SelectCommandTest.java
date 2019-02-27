package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEmployeeAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_Employee;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_Employee;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_Employee;
import static seedu.address.testutil.TypicalEmployees.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastEmployeeIndex = Index.fromOneBased(model.getFilteredEmployeeList().size());

        assertExecutionSuccess(INDEX_FIRST_Employee);
        assertExecutionSuccess(INDEX_THIRD_Employee);
        assertExecutionSuccess(lastEmployeeIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredEmployeeList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_Employee_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showEmployeeAtIndex(model, INDEX_FIRST_Employee);
        showEmployeeAtIndex(expectedModel, INDEX_FIRST_Employee);

        assertExecutionSuccess(INDEX_FIRST_Employee);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showEmployeeAtIndex(model, INDEX_FIRST_Employee);
        showEmployeeAtIndex(expectedModel, INDEX_FIRST_Employee);

        Index outOfBoundsIndex = INDEX_SECOND_Employee;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getEmployeeList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_Employee_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_Employee);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_Employee);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_Employee);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different employee -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index},
     * and checks that the model's selected employee is set to the employee at {@code index} in the filtered employee list.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_Employee_SUCCESS, index.getOneBased());
        expectedModel.setSelectedEmployee(model.getFilteredEmployeeList().get(index.getZeroBased()));

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
    }
}
