package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.employee.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindEmployeeCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String FIND_EMPLOYEE_TYPE = "employee";

    //will shift these to the new classes later
    public static final String FIND_PROJECT_TYPE = "project";
    public static final String FIND_SKILL_TYPE = "skill";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain "
        + "the specified keyword (case-insensitive) and displays them as a list with index numbers.\n"
        + "Parameters: KEYWORD \n"
        + "Example: " + COMMAND_WORD + " employee " + "alice";

    private final NameContainsKeywordsPredicate predicate;

    public FindEmployeeCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredEmployeeList(predicate);
        return new CommandResult(
            String.format(Messages.MESSAGE_EMPLOYEES_LISTED_OVERVIEW, model.getFilteredEmployeeList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindEmployeeCommand // instanceof handles nulls
            && predicate.equals(((FindEmployeeCommand) other).predicate)); // state check
    }
}