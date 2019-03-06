package seedu.address.model.project;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ClientTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Client(null));
    }

    @Test
    public void constructor_invalidClient_throwsIllegalArgumentException() {
        String invalidClient = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Client(invalidClient));
    }

    @Test
    public void isValidClient() {
        // null client
        Assert.assertThrows(NullPointerException.class, () -> Client.isValidName(null));

        // invalid names
        assertFalse(Client.isValidName("")); // empty string
        assertFalse(Client.isValidName(" ")); // spaces only

        // valid names
        assertTrue(Client.isValidName("Jothipillay Dumdum"));
        assertTrue(Client.isValidName("a")); // one character
        assertTrue(Client.isValidName("Jothipillay Jothipillary Jothipillaryas Jjothiiithihihi")); // long names
    }
}
