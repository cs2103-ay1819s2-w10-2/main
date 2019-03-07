package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.employee.Employee;

/**
 * Provides a handle to a employee card in the employee list panel.
 */
public class EmployeeCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String SKILLS_FIELD_ID = "#skills";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final List<Label> skillLabels;

    public EmployeeCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);

        Region skillsContainer = getChildNode(SKILLS_FIELD_ID);
        skillLabels = skillsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public List<String> getSkills() {
        return skillLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code employee}.
     */
    public boolean equals(Employee employee) {
        return getName().equals(employee.getName().fullName)
                && getAddress().equals(employee.getAddress().value)
                && getPhone().equals(employee.getPhone().value)
                && getEmail().equals(employee.getEmail().value)
                && ImmutableMultiset.copyOf(getSkills()).equals(ImmutableMultiset.copyOf(employee.getSkills().stream()
                        .map(skill -> skill.skillName)
                        .collect(Collectors.toList())));
    }
}
