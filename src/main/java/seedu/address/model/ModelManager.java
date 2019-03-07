package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.exceptions.EmployeeNotFoundException;
import seedu.address.model.project.exceptions.ProjectNotFoundException;
import seedu.address.model.project.Project;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Employee> filteredEmployees;
    private final FilteredList<Project> filteredProjects;
    private final SimpleObjectProperty<Employee> selectedEmployee = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Project> selectedProject = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredEmployees = new FilteredList<>(versionedAddressBook.getEmployeeList());
        filteredEmployees.addListener(this::ensureSelectedEmployeeIsValid);

        filteredProjects = new FilteredList<>(versionedAddressBook.getProjectList());
        filteredProjects.addListener(this::ensureSelectedProjectIsValid);

    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        versionedAddressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public boolean hasEmployee(Employee employee) {
        requireNonNull(employee);
        return versionedAddressBook.hasEmployee(employee);
    }

    @Override
    public boolean hasProject(Project project) {
        requireNonNull(project);
        return versionedAddressBook.hasProject(project);
    }

    @Override
    public void deleteEmployee(Employee target) {
        versionedAddressBook.removeEmployee(target);
    }

    @Override
    public void deleteProject(Project target) {
        versionedAddressBook.removeProject(target);
    }

    @Override
    public void addEmployee(Employee employee) {
        versionedAddressBook.addEmployee(employee);
        updateFilteredEmployeeList(PREDICATE_SHOW_ALL_EMPLOYEES);
    }

    @Override
    public void addProject(Project project) {
        versionedAddressBook.addProject(project);
        updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
    }

    @Override
    public void setEmployee(Employee target, Employee editedEmployee) {
        requireAllNonNull(target, editedEmployee);

        versionedAddressBook.setEmployee(target, editedEmployee);
    }

    //=========== Filtered Employee List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Employee} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Employee> getFilteredEmployeeList() {
        return filteredEmployees;
    }

    @Override
    public void updateFilteredEmployeeList(Predicate<Employee> predicate) {
        requireNonNull(predicate);
        filteredEmployees.setPredicate(predicate);
    }

    //=========== Filtered Project List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Project} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Project> getFilteredProjectList() {
        return filteredProjects;
    }

    @Override
    public void updateFilteredProjectList(Predicate<Project> predicate) {
        requireNonNull(predicate);
        filteredProjects.setPredicate(predicate);
    }

    //=========== Whole Project List Accessors ==============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Project} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Project> getProjectList() {
        return versionedAddressBook.getProjectList();
    }


    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    //=========== Selected employee ===========================================================================

    @Override
    public ReadOnlyProperty<Employee> selectedEmployeeProperty() {
        return selectedEmployee;
    }

    @Override
    public Employee getSelectedEmployee() {
        return selectedEmployee.getValue();
    }

    @Override
    public void setSelectedEmployee(Employee employee) {
        if (employee != null && !filteredEmployees.contains(employee)) {
            throw new EmployeeNotFoundException();
        }
        selectedEmployee.setValue(employee);
    }

    /**
     * Ensures {@code selectedEmployee} is a valid employee in {@code filteredEmployees}.
     */
    private void ensureSelectedEmployeeIsValid(ListChangeListener.Change<? extends Employee> change) {
        while (change.next()) {
            if (selectedEmployee.getValue() == null) {
                // null is always a valid selected employee, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedEmployeeReplaced = change.wasReplaced()
                    && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedEmployee.getValue());
            if (wasSelectedEmployeeReplaced) {
                // Update selectedEmployee to its new value.
                int index = change.getRemoved().indexOf(selectedEmployee.getValue());
                selectedEmployee.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedEmployeeRemoved = change.getRemoved().stream()
                    .anyMatch(removedEmployee -> selectedEmployee.getValue().isSameEmployee(removedEmployee));
            if (wasSelectedEmployeeRemoved) {
                // Select the employee that came before it in the list,
                // or clear the selection if there is no such employee.
                selectedEmployee.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    //========= Selected project ==================================================================================

    @Override
    public ReadOnlyProperty<Project> selectedProjectProperty() {
        return selectedProject;
    }
    @Override
    public Project getSelectedProject() {
        return selectedProject.getValue();
    }

    //TODO CHANGE THIS
    @Override
    public void setSelectedProject(Project project) {
        if (project != null && !filteredProjects.contains(project)) {
            throw new ProjectNotFoundException();
        }
        selectedProject.setValue(project);
    }

    /**
     * Ensures {@code selectedProject} is a valid project in {@code filteredProject}.
     */
    private void ensureSelectedProjectIsValid(ListChangeListener.Change<? extends Project> change) {
        while (change.next()) {
            if (selectedProject.getValue() == null) {
                // null is always a valid selected project, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedProjectReplaced = change.wasReplaced()
                    && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedProject.getValue());
            if (wasSelectedProjectReplaced) {
                // Update selectedProject to its new value.
                int index = change.getRemoved().indexOf(selectedProject.getValue());
                selectedProject.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedProjectRemoved = change.getRemoved().stream()
                    .anyMatch(removedProject -> selectedProject.getValue().isSameProject(removedProject));
            if (wasSelectedProjectRemoved) {
                // Select the project that came before it in the list,
                // or clear the selection if there is no such project.
                selectedProject.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

//===================================================================================================

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredEmployees.equals(other.filteredEmployees)
                && Objects.equals(selectedEmployee.get(), other.selectedEmployee.get());
    }

}
