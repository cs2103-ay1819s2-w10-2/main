package seedu.address.testutil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.project.Project;

/**
 * A utility class containing a list of {@code Project} objects to be used in tests.
 */
public class TypicalProjects {

    public static final Project PROJECT_ALICE = new ProjectBuilder().withProjectName("Project Alice")
            .withClient("Dehui").withDeadline("11/02/2019").build();
    public static final Project PROJECT_BENSON = new ProjectBuilder().withProjectName("Project Benson")
            .withClient("Jeff")
            .withDeadline("23/01/2011").build();
    public static final Project PROJECT_CARL = new ProjectBuilder().withProjectName("Project Carl").withClient("Darryl")
            .withDeadline("12/12/2012").build();
    public static final Project PROJECT_DANIEL = new ProjectBuilder().withProjectName("Project Daniel")
            .withClient("Shune")
            .withDeadline("21/12/2012").build();
    public static final Project PROJECT_ELLE = new ProjectBuilder().withProjectName("Project Elle").withClient("Jothi")
            .withDeadline("22/03/2019").build();
    public static final Project PROJECT_FIONA = new ProjectBuilder().withProjectName("Project Fiona").withClient("SOC")
            .withDeadline("01/01/2020").build();
    public static final Project PROJECT_GEORGE = new ProjectBuilder()
            .withProjectName("Project George").withClient("FASS")
            .withDeadline("11/02/2021").build();

    // Manually added
    public static final Project PROJECT_HOON = new ProjectBuilder()
            .withProjectName("Project Hoon Meier").withClient("FOE")
            .withDeadline("21/11/2018").build();
    public static final Project PROJECT_IDA = new ProjectBuilder()
            .withProjectName("Project Ida Mueller").withClient("FOS")
            .withDeadline("3/3/2013").build();
    /*
    // Manually added - Project's details found in {@code CommandTestUtil}
    public static final Project AMY = new ProjectBuilder().withProjectName(VALID_NAME_AMY).withClient(VALID_PHONE_AMY)
            .withDeadline(VALID_EMAIL_AMY).withClient(VALID_ADDRESS_AMY).withSkills(VALID_SKILL_C).build();
    public static final Project BOB = new ProjectBuilder().withProjectName(VALID_NAME_BOB).withClient(VALID_PHONE_BOB)
            .withDeadline(VALID_EMAIL_BOB).withClient(VALID_ADDRESS_BOB)
            .withSkills(VALID_SKILL_JAVA, VALID_SKILL_C).build();
    */
    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalProjects() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical projects.
     */
    public static AddressBook getTypicalAddressBookWithProjects() {
        AddressBook ab = new AddressBook();
        for (Project project : getTypicalProjects()) {
            ab.addProject(project);
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical projects added to the given {@code AddressBook}.
     */
    public static AddressBook addTypicalProjects(AddressBook ab) {
        for (Project project : getTypicalProjects()) {
            ab.addProject(project);
        }
        return ab;
    }

    public static List<Project> getTypicalProjects() {
        return new ArrayList<>(Arrays.asList(PROJECT_ALICE, PROJECT_BENSON, PROJECT_CARL,
                PROJECT_DANIEL, PROJECT_ELLE, PROJECT_FIONA, PROJECT_GEORGE));
    }
}
