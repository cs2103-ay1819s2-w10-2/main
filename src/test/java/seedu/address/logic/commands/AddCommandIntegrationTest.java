package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEmployees.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.employee.Employee;
import seedu.address.testutil.EmployeeBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newEmployee_success() {
        Employee validEmployee = new EmployeeBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addEmployee(validEmployee);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddCommand(validEmployee), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validEmployee), expectedModel);
    }

    @Test
    public void execute_duplicateEmployee_throwsCommandException() {
        Employee EmployeeInList = model.getAddressBook().getEmployeeList().get(0);
        assertCommandFailure(new AddCommand(EmployeeInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_Employee);
    }

}
