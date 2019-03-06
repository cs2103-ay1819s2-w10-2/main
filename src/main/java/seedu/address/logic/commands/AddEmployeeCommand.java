package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;

/**
 * Adds an employee to the address book.
 */
public class AddEmployeeCommand extends AddCommand {

    public static final String ADD_EMPLOYEE_KEYWORD = "employee";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " employee"
            + ": Adds an employee to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_SKILL + "SKILL]...\n"
            + "Example: " + COMMAND_WORD + " employee "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_SKILL + "Java "
            + PREFIX_SKILL + "UI";

    public static final String MESSAGE_ADD_EMPLOYEE_SUCCESS = "New employee added: %1$s";
    public static final String MESSAGE_DUPLICATE_EMPLOYEE = "This employee already exists in the pocket project";

    private final Employee toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Employee}
     */
    public AddEmployeeCommand(Employee employee) {
        requireNonNull(employee);
        toAdd = employee;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasEmployee(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EMPLOYEE);
        }

        model.addEmployee(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_ADD_EMPLOYEE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEmployeeCommand // instanceof handles nulls
                && toAdd.equals(((AddEmployeeCommand) other).toAdd));
    }
}

