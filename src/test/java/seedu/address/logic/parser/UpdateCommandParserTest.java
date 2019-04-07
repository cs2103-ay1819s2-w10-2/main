package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateUserStoryCommand;
import seedu.address.model.project.ProjectName;
import seedu.address.model.project.UserStoryStatus;

public class UpdateCommandParserTest {

    private UpdateCommandParser parser = new UpdateCommandParser();

    @Test
    public void parse_validArgs_returnsUpdateCommand() {
        assertParseSuccess(parser, "Project Apollo userstory 1 ongoing",
                new UpdateUserStoryCommand(new ProjectName("Project Apollo"), Index.fromOneBased(1),
                        new UserStoryStatus("ongoing")));
        assertParseSuccess(parser, "Project X userstory 1 on hold",
                new UpdateUserStoryCommand(new ProjectName("Project X"), Index.fromOneBased(1),
                        new UserStoryStatus("on hold")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "Project Apollo invalid 1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE)); //wrong format
        assertParseFailure(parser, "Project Apollo userstory 1 invalid",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        UpdateUserStoryCommand.MESSAGE_USAGE)); //wrong status

    }
}