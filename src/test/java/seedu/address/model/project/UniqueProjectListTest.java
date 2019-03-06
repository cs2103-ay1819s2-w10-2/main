package seedu.address.model.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_BOB;
import static seedu.address.testutil.TypicalProjects.PROJECT_ALICE;
import static seedu.address.testutil.TypicalEmployees.BOB;
import static seedu.address.testutil.TypicalProjects.PROJECT_BENSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.project.exceptions.DuplicateProjectException;
import seedu.address.model.project.exceptions.ProjectNotFoundException;
import seedu.address.model.project.exceptions.DuplicateProjectException;
import seedu.address.testutil.ProjectBuilder;

public class UniqueProjectListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueProjectList uniqueProjectList = new UniqueProjectList();

    @Test
    public void contains_nullProject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueProjectList.contains(null);
    }

    @Test
    public void contains_projectNotInList_returnsFalse() {
        assertFalse(uniqueProjectList.contains(PROJECT_ALICE));
    }

    @Test
    public void contains_projectInList_returnsTrue() {
        uniqueProjectList.add(PROJECT_ALICE);
        assertTrue(uniqueProjectList.contains(PROJECT_ALICE));
    }

    @Test
    public void contains_projectWithSameIdentityFieldsInList_returnsTrue() {
        uniqueProjectList.add(PROJECT_ALICE);
        Project editedAlice = new ProjectBuilder(PROJECT_ALICE).withClient(VALID_CLIENT_BOB)
                .withDeadline(VALID_DEADLINE_BOB).build();
        assertTrue(uniqueProjectList.contains(editedAlice));
    }

    @Test
    public void add_nullProject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueProjectList.add(null);
    }

    @Test
    public void add_duplicateProject_throwsDuplicateEmployeeException() {
        uniqueProjectList.add(PROJECT_ALICE);
        thrown.expect(DuplicateProjectException.class);
        uniqueProjectList.add(PROJECT_ALICE);
    }

    @Test
    public void setEmployee_nullTargetProject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueProjectList.setProject(null, PROJECT_ALICE);
    }

    @Test
    public void setProject_nullEditedProject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueProjectList.setProject(PROJECT_ALICE, null);
    }

    @Test
    public void setProject_targetProjectNotInList_throwsProjectNotFoundException() {
        thrown.expect(ProjectNotFoundException.class);
        uniqueProjectList.setProject(PROJECT_ALICE, PROJECT_ALICE);
    }

    @Test
    public void setProject_editedProjectIsSameProject_success() {
        uniqueProjectList.add(PROJECT_ALICE);
        uniqueProjectList.setProject(PROJECT_ALICE, PROJECT_ALICE);
        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        expectedUniqueProjectList.add(PROJECT_ALICE);
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProject_editedProjectHasSameIdentity_success() {
        uniqueProjectList.add(PROJECT_ALICE);
        Project editedAlice = new ProjectBuilder(PROJECT_ALICE).withClient(VALID_CLIENT_BOB)
                .withDeadline(VALID_DEADLINE_BOB).build();
        uniqueProjectList.setProject(PROJECT_ALICE, editedAlice);
        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        expectedUniqueProjectList.add(editedAlice);
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProject_editedProjectHasDifferentIdentity_success() {
        uniqueProjectList.add(PROJECT_ALICE);
        uniqueProjectList.setProject(PROJECT_ALICE, PROJECT_BENSON);
        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        expectedUniqueProjectList.add(PROJECT_BENSON);
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProject_editedProjectHasNonUniqueIdentity_throwsDuplicateProjectException() {
        uniqueProjectList.add(PROJECT_ALICE);
        uniqueProjectList.add(PROJECT_BENSON);
        thrown.expect(DuplicateProjectException.class);
        uniqueProjectList.setProject(PROJECT_ALICE, PROJECT_BENSON);
    }

    @Test
    public void remove_nullProject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueProjectList.remove(null);
    }

    @Test
    public void remove_projectDoesNotExist_throwsProjectNotFoundException() {
        thrown.expect(ProjectNotFoundException.class);
        uniqueProjectList.remove(PROJECT_ALICE);
    }

    @Test
    public void remove_existingProject_removesProject() {
        uniqueProjectList.add(PROJECT_ALICE);
        uniqueProjectList.remove(PROJECT_ALICE);
        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProjects_nullUniqueProjectList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueProjectList.setProjects((UniqueProjectList) null);
    }

    @Test
    public void setProject_uniqueProjectList_replacesOwnListWithProvidedUniqueProjectList() {
        uniqueProjectList.add(PROJECT_ALICE);
        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        expectedUniqueProjectList.add(PROJECT_BENSON);
        uniqueProjectList.setProjects(expectedUniqueProjectList);
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProjects_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueProjectList.setProjects((List<Project>) null);
    }

    @Test
    public void setProjects_list_replacesOwnListWithProvidedList() {
        uniqueProjectList.add(PROJECT_ALICE);
        List<Project> projectList = Collections.singletonList(PROJECT_BENSON);
        uniqueProjectList.setProjects(projectList);
        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        expectedUniqueProjectList.add(PROJECT_BENSON);
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProjects_listWithDuplicateProjects_throwsDuplicateProjectException() {
        List<Project> listWithDuplicateProjects = Arrays.asList(PROJECT_ALICE, PROJECT_ALICE);
        thrown.expect(DuplicateProjectException.class);
        uniqueProjectList.setProjects(listWithDuplicateProjects);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueProjectList.asUnmodifiableObservableList().remove(0);
    }
}
