package seedu.address.model.project;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ProjectNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ProjectName(null));
    }

    @Test
    public void constructor_invalidProjectName_throwsIllegalArgumentException() {
        String invalidProjectName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ProjectName(invalidProjectName));
    }

    @Test
    public void isValidProjectName() {
        // null client
        Assert.assertThrows(NullPointerException.class, () -> ProjectName.isValidName(null));

        // invalid names
        assertFalse(ProjectName.isValidName("")); // empty string
        assertFalse(ProjectName.isValidName(" ")); // spaces only

        // valid names
        assertTrue(ProjectName.isValidName("First Project"));
        assertTrue(ProjectName.isValidName("a")); // one character
        assertTrue(ProjectName.isValidName("P in Project stands for Project which is recursive")); // long names
    }
}
