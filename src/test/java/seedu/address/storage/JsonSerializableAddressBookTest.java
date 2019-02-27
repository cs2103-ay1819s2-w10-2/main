package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalEmployees;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_EmployeeS_FILE = TEST_DATA_FOLDER.resolve("typicalEmployeesAddressBook.json");
    private static final Path INVALID_Employee_FILE = TEST_DATA_FOLDER.resolve("invalidEmployeeAddressBook.json");
    private static final Path DUPLICATE_Employee_FILE = TEST_DATA_FOLDER.resolve("duplicateEmployeeAddressBook.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalEmployeesFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_EmployeeS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalEmployeesAddressBook = TypicalEmployees.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalEmployeesAddressBook);
    }

    @Test
    public void toModelType_invalidEmployeeFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_Employee_FILE,
                JsonSerializableAddressBook.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateEmployees_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_Employee_FILE,
                JsonSerializableAddressBook.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableAddressBook.MESSAGE_DUPLICATE_Employee);
        dataFromFile.toModelType();
    }

}
