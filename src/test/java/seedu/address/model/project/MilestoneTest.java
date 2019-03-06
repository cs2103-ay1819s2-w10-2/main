package seedu.address.model.project;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MilestoneTest {

    //TODO: Add JUnit tests for constructor of milestones
    /*
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Milestone(null, null));
    }

    @Test
    public void constructor_invalidMilestone_throwsIllegalArgumentException() {
        String invalidDate = "";
        String invalidMilestone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Milestone(invalidMilestone, invalidDate));
    }
    */

    @Test
    public void isValidMilestone() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> Milestone.isValidMilestoneDate(null));

        // null milestone
        Assert.assertThrows(NullPointerException.class, () -> Milestone.isValidMilestoneString(null));

        // invalid dates
        assertFalse(Milestone.isValidMilestoneDate(" ")); // missing date year and month value
        assertFalse(Milestone.isValidMilestoneDate("12")); // missing both year and month value
        assertFalse(Milestone.isValidMilestoneDate("12/12")); // missing the year value
        assertFalse(Milestone.isValidMilestoneDate("12/2019")); // missing the month/date value

        // invalid dates
        assertFalse(Milestone.isValidMilestoneDate("12//12/2019")); // wrong input pattern
        assertFalse(Milestone.isValidMilestoneDate("12/12/12/2019")); // too many fields
        assertFalse(Milestone.isValidMilestoneDate("111/11/2019")); // too many digits in the date field
        assertFalse(Milestone.isValidMilestoneDate("11/111/2019")); // too many digits in the month field
        assertFalse(Milestone.isValidMilestoneDate("11/11/11111")); // too many digits in the date field
        assertFalse(Milestone.isValidMilestoneDate(" 11/11/2019")); // leading space
        assertFalse(Milestone.isValidMilestoneDate("11/11/2019 ")); // trailing space
        assertFalse(Milestone.isValidMilestoneDate("aa/11/2019")); // alphabetical date
        assertFalse(Milestone.isValidMilestoneDate("11/aa/2019")); // alphabetical month
        assertFalse(Milestone.isValidMilestoneDate("11/11/aaaa")); // alphabetical year
        assertFalse(Milestone.isValidMilestoneDate("44/11/2019")); // invalid date
        assertFalse(Milestone.isValidMilestoneDate("11/44/aaaa")); // invalid month
        assertFalse(Milestone.isValidMilestoneDate("11/11/1800")); // invalid year too old

        // invalid milestone strings
        assertFalse(Milestone.isValidMilestoneString(" ")); //empty string
        assertFalse(Milestone.isValidMilestoneString("?")); //non alphanumerical values
        assertFalse(Milestone.isValidMilestoneString("Is this a valid string.")); //non alphanumerical values

        // valid deadlines
        assertTrue(Milestone.isValidMilestoneDate("11/11/2019"));
        assertTrue(Milestone.isValidMilestoneDate("1/1/2000")); // minimal

        //valid milestone strings
        assertTrue(Milestone.isValidMilestoneString("This is a valid string"));

        //valid milestones
        assertTrue(Milestone.isValidMilestone("valid string", "12/12/2019"));
    }
}
