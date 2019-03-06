package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.EmployeeCardHandle;
import guitests.guihandles.EmployeeListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;

import seedu.address.model.employee.Employee;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(EmployeeCardHandle expectedCard, EmployeeCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getSkills(), actualCard.getSkills());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedEmployee}.
     */
    public static void assertCardDisplaysEmployee(Employee expectedEmployee, EmployeeCardHandle actualCard) {
        assertEquals(expectedEmployee.getName().fullName, actualCard.getName());
        assertEquals(expectedEmployee.getPhone().value, actualCard.getPhone());
        assertEquals(expectedEmployee.getEmail().value, actualCard.getEmail());
        assertEquals(expectedEmployee.getAddress().value, actualCard.getAddress());
        assertEquals(expectedEmployee.getSkills().stream().map(skill -> skill.skillName).collect(Collectors.toList()),
                actualCard.getSkills());
    }

    /**
     * Asserts that the list in {@code employeeListPanelHandle} displays the details of {@code employees} correctly and
     * in the correct order.
     */
    public static void assertListMatching(EmployeeListPanelHandle employeeListPanelHandle, Employee... employees) {
        for (int i = 0; i < employees.length; i++) {
            employeeListPanelHandle.navigateToCard(i);
            assertCardDisplaysEmployee(employees[i], employeeListPanelHandle.getEmployeeCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code employeeListPanelHandle} displays the details of {@code employees} correctly and
     * in the correct order.
     */
    public static void assertListMatching(EmployeeListPanelHandle employeeListPanelHandle, List<Employee> employees) {
        assertListMatching(employeeListPanelHandle, employees.toArray(new Employee[0]));
    }

    /**
     * Asserts the size of the list in {@code employeeListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(EmployeeListPanelHandle employeeListPanelHandle, int size) {
        int numberOfPeople = employeeListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
