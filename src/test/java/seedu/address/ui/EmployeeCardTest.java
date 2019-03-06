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
        // no skills
        Employee employeeWithNoSkills = new EmployeeBuilder().withSkills(new String[0]).build();
        EmployeeCard employeeCard = new EmployeeCard(employeeWithNoSkills, 1);
        uiPartRule.setUiPart(employeeCard);
        assertCardDisplay(employeeCard, employeeWithNoSkills, 1);

        // with skills
        Employee employeeWithSkills = new EmployeeBuilder().build();
        employeeCard = new EmployeeCard(employeeWithSkills, 2);
        uiPartRule.setUiPart(employeeCard);
        assertCardDisplay(employeeCard, employeeWithSkills, 2);
    }

    @Test
    public void equals() {
        Employee employee = new EmployeeBuilder().build();
        EmployeeCard employeeCard = new EmployeeCard(employee, 0);

        // same employee, same index -> returns true
        EmployeeCard copy = new EmployeeCard(employee, 0);
        assertTrue(employeeCard.equals(copy));

        // same object -> returns true
        assertTrue(employeeCard.equals(employeeCard));

        // null -> returns false
        assertFalse(employeeCard.equals(null));

        // different types -> returns false
        assertFalse(employeeCard.equals(0));

        // different employee, same index -> returns false
        Employee differentEmployee = new EmployeeBuilder().withName("differentName").build();
        assertFalse(employeeCard.equals(new EmployeeCard(differentEmployee, 0)));

        // same employee, different index -> returns false
        assertFalse(employeeCard.equals(new EmployeeCard(employee, 1)));
    }

    /**
     * Asserts that {@code employeeCard} displays the details of {@code expectedEmployee} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(EmployeeCard employeeCard, Employee expectedEmployee, int expectedId) {
        guiRobot.pauseForHuman();

        EmployeeCardHandle employeeCardHandle = new EmployeeCardHandle(employeeCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", employeeCardHandle.getId());

        // verify employee details are displayed correctly
        assertCardDisplaysEmployee(expectedEmployee, employeeCardHandle);
    }
}
