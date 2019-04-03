package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PROJECT_1;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PROJECT_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_ZULU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showProjectAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;
import static seedu.address.testutil.TypicalProjectNames.NON_EXISTENT_PROJECT_NAME;
import static seedu.address.testutil.TypicalProjectNames.TYPICAL_PROJECT_NAME_INDEX_1;
import static seedu.address.testutil.TypicalProjectNames.TYPICAL_PROJECT_NAME_INDEX_2;
import static seedu.address.testutil.TypicalProjects.getTypicalPocketProjectWithProjects;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PocketProject;
import seedu.address.model.UserPrefs;
import seedu.address.model.project.Project;
import seedu.address.testutil.EditProjectDescriptorBuilder;
import seedu.address.testutil.ProjectBuilder;
import seedu.address.testutil.TypicalEmployees;
import seedu.address.testutil.TypicalMilestones;
import seedu.address.testutil.TypicalUserStories;




public class EditProjectInfoCommandTest {

    private Model model = new ModelManager(getTypicalPocketProjectWithProjects(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Project editedProject = new ProjectBuilder().withProjectName(VALID_PROJECT_NAME_ALICE)
            .withClient(VALID_CLIENT_ALICE).withDeadline(VALID_DEADLINE_ZULU).withDescrption(VALID_DESCRIPTION)
            .withEmployees(Arrays.asList(TypicalEmployees.BENSON, TypicalEmployees.CARL))
            .withMilestones(Arrays.asList(TypicalMilestones.TYPICAL_MILESTONE_START,
                TypicalMilestones.TYPICAL_MILESTONE_END))
            .withUserStories(Arrays.asList(TypicalUserStories.USER_STORY_FIRST_MANAGER)).build();
        EditProjectInfoCommand.EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder(editedProject)
            .build();
        EditProjectInfoCommand editProjectCommand = new EditProjectInfoCommand(TYPICAL_PROJECT_NAME_INDEX_1,
            descriptor);

        String expectedMessage = EditProjectInfoCommand.MESSAGE_EDIT_PROJECT_SUCCESS;

        Model expectedModel = new ModelManager(new PocketProject(model.getPocketProject()), new UserPrefs());
        expectedModel.setProject(model.getFilteredProjectList().get(0), editedProject);
        expectedModel.commitPocketProject();

        assertCommandSuccess(editProjectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastProject = Index.fromOneBased(model.getFilteredProjectList().size());
        Project lastProject = model.getFilteredProjectList().get(indexLastProject.getZeroBased());

        ProjectBuilder ProjectInList = new ProjectBuilder(lastProject);
        Project editedProject = ProjectInList.withProjectName(VALID_NAME_BOB).withClient(VALID_CLIENT_BOB)
            .withDescrption(VALID_DESCRIPTION).build();

        EditProjectInfoCommand.EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder()
            .withName(VALID_NAME_BOB).withClient(VALID_CLIENT_BOB).withDescription(VALID_DESCRIPTION).build();
        EditProjectCommand editProjectCommand = new EditProjectInfoCommand(lastProject.getProjectName(), descriptor);

        String expectedMessage = EditProjectInfoCommand.MESSAGE_EDIT_PROJECT_SUCCESS;

        Model expectedModel = new ModelManager(new PocketProject(model.getPocketProject()), new UserPrefs());
        expectedModel.setProject(lastProject, editedProject);
        expectedModel.commitPocketProject();

        assertCommandSuccess(editProjectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditProjectCommand editProjectCommand = new EditProjectInfoCommand(TYPICAL_PROJECT_NAME_INDEX_1,
            new EditProjectInfoCommand.EditProjectDescriptor());
        Project editedProject = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());

        String expectedMessage = EditProjectInfoCommand.MESSAGE_EDIT_PROJECT_SUCCESS;

        Model expectedModel = new ModelManager(new PocketProject(model.getPocketProject()), new UserPrefs());
        expectedModel.commitPocketProject();

        assertCommandSuccess(editProjectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showProjectAtIndex(model, INDEX_FIRST_PROJECT);

        Project ProjectInFilteredList = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        Project editedProject = new ProjectBuilder(ProjectInFilteredList).withProjectName(VALID_NAME_BOB).build();
        EditProjectCommand editProjectCommand = new EditProjectInfoCommand(ProjectInFilteredList.getProjectName(),
            new EditProjectDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = EditProjectInfoCommand.MESSAGE_EDIT_PROJECT_SUCCESS;

        Model expectedModel = new ModelManager(new PocketProject(model.getPocketProject()), new UserPrefs());
        expectedModel.setProject(model.getFilteredProjectList().get(0), editedProject);
        expectedModel.commitPocketProject();

        assertCommandSuccess(editProjectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateProjectUnfilteredList_failure() {
        Project firstProject = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        EditProjectInfoCommand.EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder(firstProject)
            .build();
        EditProjectCommand editProjectCommand = new EditProjectInfoCommand(TYPICAL_PROJECT_NAME_INDEX_2,
            descriptor);

        assertCommandFailure(editProjectCommand, model, commandHistory,
            EditProjectInfoCommand.MESSAGE_DUPLICATE_PROJECT);
    }

    @Test
    public void execute_duplicateProjectFilteredList_failure() {
        showProjectAtIndex(model, INDEX_FIRST_PROJECT);

        // edit Project in filtered list into a duplicate in pocket project
        Project ProjectInList = model.getPocketProject().getProjectList().get(INDEX_SECOND_PROJECT.getZeroBased());
        EditProjectCommand editProjectCommand = new EditProjectInfoCommand(TYPICAL_PROJECT_NAME_INDEX_1,
            new EditProjectDescriptorBuilder(ProjectInList).build());

        assertCommandFailure(editProjectCommand, model, commandHistory,
            EditProjectInfoCommand.MESSAGE_DUPLICATE_PROJECT);
    }

    @Test
    public void execute_invalidProjectName_failure() throws ParseException {
        EditProjectInfoCommand.EditProjectDescriptor descriptor =
            new EditProjectDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditProjectCommand editProjectCommand = new EditProjectInfoCommand(NON_EXISTENT_PROJECT_NAME , descriptor);

        assertCommandFailure(editProjectCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_PROJECT_NAME);
    }

    @Test
    public void executeUndoRedo_validProjectName_success() throws Exception {
        Project editedProject = new ProjectBuilder().withProjectName(VALID_PROJECT_NAME_ALICE)
            .withClient(VALID_CLIENT_ALICE).withDeadline(VALID_DEADLINE_ZULU).withDescrption(VALID_DESCRIPTION)
            .withEmployees(Arrays.asList(TypicalEmployees.BENSON, TypicalEmployees.CARL))
            .withMilestones(Arrays.asList(TypicalMilestones.TYPICAL_MILESTONE_START,
                TypicalMilestones.TYPICAL_MILESTONE_END))
            .withUserStories(Arrays.asList(TypicalUserStories.USER_STORY_FIRST_MANAGER)).build();

        Project projectToEdit = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        EditProjectInfoCommand.EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder(editedProject)
            .build();
        EditProjectCommand editProjectCommand = new EditProjectInfoCommand(TYPICAL_PROJECT_NAME_INDEX_1, descriptor);
        Model expectedModel = new ModelManager(new PocketProject(model.getPocketProject()), new UserPrefs());
        expectedModel.setProject(projectToEdit, editedProject);
        expectedModel.commitPocketProject();

        // edit -> first Project edited
        editProjectCommand.execute(model, commandHistory);

        // undo -> reverts pocket project back to previous state and filtered Project list to show all Projects
        expectedModel.undoPocketProject();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first Project edited again
        expectedModel.redoPocketProject();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidProjectName_failure() {
        EditProjectInfoCommand.EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder()
            .withName(VALID_NAME_BOB).build();
        EditProjectInfoCommand editProjectCommand = new EditProjectInfoCommand(NON_EXISTENT_PROJECT_NAME, descriptor);

        // execution failed -> pocket project state not added into model
        assertCommandFailure(editProjectCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_PROJECT_NAME);

        // single pocket project state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        final EditProjectCommand standardCommand = new EditProjectInfoCommand(TYPICAL_PROJECT_NAME_INDEX_1,
            DESC_PROJECT_1);
        // same values -> returns true
        EditProjectInfoCommand.EditProjectDescriptor copyDescriptor =
            new EditProjectInfoCommand.EditProjectDescriptor(DESC_PROJECT_1);
        EditProjectCommand commandWithSameValues = new EditProjectInfoCommand(TYPICAL_PROJECT_NAME_INDEX_1,
            copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different Project name -> returns false
        assertFalse(standardCommand.equals(new EditProjectInfoCommand(TYPICAL_PROJECT_NAME_INDEX_2, DESC_PROJECT_1)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditProjectInfoCommand(TYPICAL_PROJECT_NAME_INDEX_1, DESC_PROJECT_2)));
    }
}