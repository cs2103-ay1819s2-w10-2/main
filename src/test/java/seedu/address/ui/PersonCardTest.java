package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysEmployee;

import org.junit.Test;

import guitests.guihandles.EmployeeCardHandle;
import seedu.address.model.employee.Employee;
import seedu.address.testutil.EmployeeBuilder;

public class EmployeeCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Employee EmployeeWithNoTags = new EmployeeBuilder().withTags(new String[0]).build();
        EmployeeCard EmployeeCard = new EmployeeCard(EmployeeWithNoTags, 1);
        uiPartRule.setUiPart(EmployeeCard);
        assertCardDisplay(EmployeeCard, EmployeeWithNoTags, 1);

        // with tags
        Employee EmployeeWithTags = new EmployeeBuilder().build();
        EmployeeCard = new EmployeeCard(EmployeeWithTags, 2);
        uiPartRule.setUiPart(EmployeeCard);
        assertCardDisplay(EmployeeCard, EmployeeWithTags, 2);
    }

    @Test
    public void equals() {
        Employee Employee = new EmployeeBuilder().build();
        EmployeeCard EmployeeCard = new EmployeeCard(Employee, 0);

        // same employee, same index -> returns true
        EmployeeCard copy = new EmployeeCard(Employee, 0);
        assertTrue(EmployeeCard.equals(copy));

        // same object -> returns true
        assertTrue(EmployeeCard.equals(EmployeeCard));

        // null -> returns false
        assertFalse(EmployeeCard.equals(null));

        // different types -> returns false
        assertFalse(EmployeeCard.equals(0));

        // different employee, same index -> returns false
        Employee differentEmployee = new EmployeeBuilder().withName("differentName").build();
        assertFalse(EmployeeCard.equals(new EmployeeCard(differentEmployee, 0)));

        // same employee, different index -> returns false
        assertFalse(EmployeeCard.equals(new EmployeeCard(Employee, 1)));
    }

    /**
     * Asserts that {@code EmployeeCard} displays the details of {@code expectedEmployee} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(EmployeeCard EmployeeCard, Employee expectedEmployee, int expectedId) {
        guiRobot.pauseForHuman();

        EmployeeCardHandle EmployeeCardHandle = new EmployeeCardHandle(EmployeeCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", EmployeeCardHandle.getId());

        // verify employee details are displayed correctly
        assertCardDisplaysEmployee(expectedEmployee, EmployeeCardHandle);
    }
}
