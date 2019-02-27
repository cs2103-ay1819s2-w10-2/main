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
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedEmployee}.
     */
    public static void assertCardDisplaysEmployee(Employee expectedEmployee, EmployeeCardHandle actualCard) {
        assertEquals(expectedEmployee.getName().fullName, actualCard.getName());
        assertEquals(expectedEmployee.getPhone().value, actualCard.getPhone());
        assertEquals(expectedEmployee.getEmail().value, actualCard.getEmail());
        assertEquals(expectedEmployee.getAddress().value, actualCard.getAddress());
        assertEquals(expectedEmployee.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code EmployeeListPanelHandle} displays the details of {@code Employees} correctly and
     * in the correct order.
     */
    public static void assertListMatching(EmployeeListPanelHandle EmployeeListPanelHandle, Employee... Employees) {
        for (int i = 0; i < Employees.length; i++) {
            EmployeeListPanelHandle.navigateToCard(i);
            assertCardDisplaysEmployee(Employees[i], EmployeeListPanelHandle.getEmployeeCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code EmployeeListPanelHandle} displays the details of {@code Employees} correctly and
     * in the correct order.
     */
    public static void assertListMatching(EmployeeListPanelHandle EmployeeListPanelHandle, List<Employee> Employees) {
        assertListMatching(EmployeeListPanelHandle, Employees.toArray(new Employee[0]));
    }

    /**
     * Asserts the size of the list in {@code EmployeeListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(EmployeeListPanelHandle EmployeeListPanelHandle, int size) {
        int numberOfPeople = EmployeeListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
