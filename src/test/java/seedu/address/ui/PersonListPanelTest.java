package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_Employee;
import static seedu.address.testutil.TypicalEmployees.getTypicalEmployees;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysEmployee;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.util.Collections;

import org.junit.Test;

import guitests.guihandles.EmployeeCardHandle;
import guitests.guihandles.EmployeeListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.employee.Address;
import seedu.address.model.employee.Email;
import seedu.address.model.employee.Name;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Phone;

public class EmployeeListPanelTest extends GuiUnitTest {
    private static final ObservableList<Employee> TYPICAL_EmployeeS =
            FXCollections.observableList(getTypicalEmployees());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Employee> selectedEmployee = new SimpleObjectProperty<>();
    private EmployeeListPanelHandle EmployeeListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_EmployeeS);

        for (int i = 0; i < TYPICAL_EmployeeS.size(); i++) {
            EmployeeListPanelHandle.navigateToCard(TYPICAL_EmployeeS.get(i));
            Employee expectedEmployee = TYPICAL_EmployeeS.get(i);
            EmployeeCardHandle actualCard = EmployeeListPanelHandle.getEmployeeCardHandle(i);

            assertCardDisplaysEmployee(expectedEmployee, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedEmployeeChanged_selectionChanges() {
        initUi(TYPICAL_EmployeeS);
        Employee secondEmployee = TYPICAL_EmployeeS.get(INDEX_SECOND_Employee.getZeroBased());
        guiRobot.interact(() -> selectedEmployee.set(secondEmployee));
        guiRobot.pauseForHuman();

        EmployeeCardHandle expectedEmployee = EmployeeListPanelHandle.getEmployeeCardHandle(INDEX_SECOND_Employee.getZeroBased());
        EmployeeCardHandle selectedEmployee = EmployeeListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedEmployee, selectedEmployee);
    }

    /**
     * Verifies that creating and deleting large number of Employees in {@code EmployeeListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Employee> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of employee cards exceeded time limit");
    }

    /**
     * Returns a list of Employees containing {@code EmployeeCount} Employees that is used to populate the
     * {@code EmployeeListPanel}.
     */
    private ObservableList<Employee> createBackingList(int EmployeeCount) {
        ObservableList<Employee> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < EmployeeCount; i++) {
            Name name = new Name(i + "a");
            Phone phone = new Phone("000");
            Email email = new Email("a@aa");
            Address address = new Address("a");
            Employee Employee = new Employee(name, phone, email, address, Collections.emptySet());
            backingList.add(Employee);
        }
        return backingList;
    }

    /**
     * Initializes {@code EmployeeListPanelHandle} with a {@code EmployeeListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code EmployeeListPanel}.
     */
    private void initUi(ObservableList<Employee> backingList) {
        EmployeeListPanel EmployeeListPanel =
                new EmployeeListPanel(backingList, selectedEmployee, selectedEmployee::set);
        uiPartRule.setUiPart(EmployeeListPanel);

        EmployeeListPanelHandle = new EmployeeListPanelHandle(getChildNode(EmployeeListPanel.getRoot(),
                EmployeeListPanelHandle.Employee_LIST_VIEW_ID));
    }
}
