package seedu.address.model.employee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.employee.exceptions.DuplicateEmployeeException;
import seedu.address.model.employee.exceptions.EmployeeNotFoundException;
import seedu.address.testutil.EmployeeBuilder;

public class UniqueEmployeeListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueEmployeeList uniqueEmployeeList = new UniqueEmployeeList();

    @Test
    public void contains_nullEmployee_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEmployeeList.contains(null);
    }

    @Test
    public void contains_EmployeeNotInList_returnsFalse() {
        assertFalse(uniqueEmployeeList.contains(ALICE));
    }

    @Test
    public void contains_EmployeeInList_returnsTrue() {
        uniqueEmployeeList.add(ALICE);
        assertTrue(uniqueEmployeeList.contains(ALICE));
    }

    @Test
    public void contains_EmployeeWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEmployeeList.add(ALICE);
        Employee editedAlice = new EmployeeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueEmployeeList.contains(editedAlice));
    }

    @Test
    public void add_nullEmployee_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEmployeeList.add(null);
    }

    @Test
    public void add_duplicateEmployee_throwsDuplicateEmployeeException() {
        uniqueEmployeeList.add(ALICE);
        thrown.expect(DuplicateEmployeeException.class);
        uniqueEmployeeList.add(ALICE);
    }

    @Test
    public void setEmployee_nullTargetEmployee_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEmployeeList.setEmployee(null, ALICE);
    }

    @Test
    public void setEmployee_nullEditedEmployee_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEmployeeList.setEmployee(ALICE, null);
    }

    @Test
    public void setEmployee_targetEmployeeNotInList_throwsEmployeeNotFoundException() {
        thrown.expect(EmployeeNotFoundException.class);
        uniqueEmployeeList.setEmployee(ALICE, ALICE);
    }

    @Test
    public void setEmployee_editedEmployeeIsSameEmployee_success() {
        uniqueEmployeeList.add(ALICE);
        uniqueEmployeeList.setEmployee(ALICE, ALICE);
        UniqueEmployeeList expectedUniqueEmployeeList = new UniqueEmployeeList();
        expectedUniqueEmployeeList.add(ALICE);
        assertEquals(expectedUniqueEmployeeList, uniqueEmployeeList);
    }

    @Test
    public void setEmployee_editedEmployeeHasSameIdentity_success() {
        uniqueEmployeeList.add(ALICE);
        Employee editedAlice = new EmployeeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueEmployeeList.setEmployee(ALICE, editedAlice);
        UniqueEmployeeList expectedUniqueEmployeeList = new UniqueEmployeeList();
        expectedUniqueEmployeeList.add(editedAlice);
        assertEquals(expectedUniqueEmployeeList, uniqueEmployeeList);
    }

    @Test
    public void setEmployee_editedEmployeeHasDifferentIdentity_success() {
        uniqueEmployeeList.add(ALICE);
        uniqueEmployeeList.setEmployee(ALICE, BOB);
        UniqueEmployeeList expectedUniqueEmployeeList = new UniqueEmployeeList();
        expectedUniqueEmployeeList.add(BOB);
        assertEquals(expectedUniqueEmployeeList, uniqueEmployeeList);
    }

    @Test
    public void setEmployee_editedEmployeeHasNonUniqueIdentity_throwsDuplicateEmployeeException() {
        uniqueEmployeeList.add(ALICE);
        uniqueEmployeeList.add(BOB);
        thrown.expect(DuplicateEmployeeException.class);
        uniqueEmployeeList.setEmployee(ALICE, BOB);
    }

    @Test
    public void remove_nullEmployee_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEmployeeList.remove(null);
    }

    @Test
    public void remove_EmployeeDoesNotExist_throwsEmployeeNotFoundException() {
        thrown.expect(EmployeeNotFoundException.class);
        uniqueEmployeeList.remove(ALICE);
    }

    @Test
    public void remove_existingEmployee_removesEmployee() {
        uniqueEmployeeList.add(ALICE);
        uniqueEmployeeList.remove(ALICE);
        UniqueEmployeeList expectedUniqueEmployeeList = new UniqueEmployeeList();
        assertEquals(expectedUniqueEmployeeList, uniqueEmployeeList);
    }

    @Test
    public void setEmployees_nullUniqueEmployeeList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEmployeeList.setEmployees((UniqueEmployeeList) null);
    }

    @Test
    public void setEmployees_uniqueEmployeeList_replacesOwnListWithProvidedUniqueEmployeeList() {
        uniqueEmployeeList.add(ALICE);
        UniqueEmployeeList expectedUniqueEmployeeList = new UniqueEmployeeList();
        expectedUniqueEmployeeList.add(BOB);
        uniqueEmployeeList.setEmployees(expectedUniqueEmployeeList);
        assertEquals(expectedUniqueEmployeeList, uniqueEmployeeList);
    }

    @Test
    public void setEmployees_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEmployeeList.setEmployees((List<Employee>) null);
    }

    @Test
    public void setEmployees_list_replacesOwnListWithProvidedList() {
        uniqueEmployeeList.add(ALICE);
        List<Employee> EmployeeList = Collections.singletonList(BOB);
        uniqueEmployeeList.setEmployees(EmployeeList);
        UniqueEmployeeList expectedUniqueEmployeeList = new UniqueEmployeeList();
        expectedUniqueEmployeeList.add(BOB);
        assertEquals(expectedUniqueEmployeeList, uniqueEmployeeList);
    }

    @Test
    public void setEmployees_listWithDuplicateEmployees_throwsDuplicateEmployeeException() {
        List<Employee> listWithDuplicateEmployees = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateEmployeeException.class);
        uniqueEmployeeList.setEmployees(listWithDuplicateEmployees);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueEmployeeList.asUnmodifiableObservableList().remove(0);
    }
}
