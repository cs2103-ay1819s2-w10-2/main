package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CompleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.project.ProjectDate;
import seedu.address.model.project.ProjectName;

/**
 * Parses input arguments and creates a new CompleteCommand object
 */
public class CompleteCommandParser implements Parser<CompleteCommand> {

    /**
     * Used for separation of delete type word and args.
     */
    private static final Pattern COMPLETE_COMMAND_FORMAT =
            Pattern.compile("(?<project>(((-?)[0-9]+)|(.*))\\s?<date>.*)");
    private static final String INTEGER_STRING_FORMAT = "(-?)[0-9]+";

    /**
     * Parses the given {@code String} of arguments in the context of the CompleteCommand
     * and returns a CompleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CompleteCommand parse(String args) throws ParseException {

        final Matcher matcher = COMPLETE_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }
        final String projectReference = matcher.group("project");
        final ProjectDate date = ParserUtil.parseDeadline(matcher.group("date"));
        String argument = projectReference.trim();
        if (argument.matches(INTEGER_STRING_FORMAT)) {
            try {
                Index index = ParserUtil.parseIndex(argument);
                return new CompleteCommand(index, date);
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        CompleteCommand.MESSAGE_USAGE, pe));
            }
        } else {
            return new CompleteCommand(new ProjectName(argument), date);
        }
    }

}
