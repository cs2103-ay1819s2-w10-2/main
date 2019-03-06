package seedu.address.model.project;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DeadlineTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Deadline(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidDeadline = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Deadline(invalidDeadline));
    }

    @Test
    public void isValidEmail() {
        // null deadline
        Assert.assertThrows(NullPointerException.class, () -> Deadline.isValidDate(null));

        // blank email
        assertFalse(Deadline.isValidDate("")); // empty string
        assertFalse(Deadline.isValidDate(" ")); // spaces only

        // missing parts
        assertFalse(Deadline.isValidDate("12")); // missing both the month/date and the year value
        assertFalse(Deadline.isValidDate("12/12")); // missing the year value
        assertFalse(Deadline.isValidDate("12/2019")); // missing the month/date value

        // invalid parts
        assertFalse(Deadline.isValidDate("12//12/2019")); // wrong input pattern
        assertFalse(Deadline.isValidDate("12/12/12/2019")); // too many fields
        assertFalse(Deadline.isValidDate("111/11/2019")); // too many digits in the date field
        assertFalse(Deadline.isValidDate("11/111/2019")); // too many digits in the month field
        assertFalse(Deadline.isValidDate("11/11/11111")); // too many digits in the date field
        assertFalse(Deadline.isValidDate(" 11/11/2019")); // leading space
        assertFalse(Deadline.isValidDate("11/11/2019 ")); // trailing space
        assertFalse(Deadline.isValidDate("aa/11/2019")); // alphabetical date
        assertFalse(Deadline.isValidDate("11/aa/2019")); // alphabetical month
        assertFalse(Deadline.isValidDate("11/11/aaaa")); // alphabetical year
        assertFalse(Deadline.isValidDate("44/11/2019")); // invalid date
        assertFalse(Deadline.isValidDate("11/44/aaaa")); // invalid month
        assertFalse(Deadline.isValidDate("11/11/1800")); // invalid year too old

        // valid deadlines
        assertTrue(Deadline.isValidDate("11/11/2019"));
        assertTrue(Deadline.isValidDate("1/1/2000")); // minimal
    }
}
