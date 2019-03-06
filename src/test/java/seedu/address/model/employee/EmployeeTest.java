package seedu.address.model.employee;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_JAVA;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.EmployeeBuilder;

public class EmployeeTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Employee employee = new EmployeeBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        employee.getSkills().remove(0);
    }

    @Test
    public void isSameEmployee() {
        // same object -> returns true
        assertTrue(ALICE.isSameEmployee(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameEmployee(null));

        // different phone and email -> returns false
        Employee editedAlice = new EmployeeBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameEmployee(editedAlice));

        // different name -> returns false
        editedAlice = new EmployeeBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameEmployee(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new EmployeeBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withSkills(VALID_SKILL_JAVA).build();
        assertTrue(ALICE.isSameEmployee(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new EmployeeBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withSkills(VALID_SKILL_JAVA).build();
        assertTrue(ALICE.isSameEmployee(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new EmployeeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withSkills(VALID_SKILL_JAVA).build();
        assertTrue(ALICE.isSameEmployee(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Employee aliceCopy = new EmployeeBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different employee -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Employee editedAlice = new EmployeeBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new EmployeeBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new EmployeeBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new EmployeeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new EmployeeBuilder(ALICE).withSkills(VALID_SKILL_JAVA).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
