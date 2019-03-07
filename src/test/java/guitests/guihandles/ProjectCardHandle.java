package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.project.Project;

/**
 * Provides a handle to a project card in the project list panel.
 */
public class ProjectCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String CLIENT_ID = "#client";
    private static final String DEADLINE_ID = "#deadline";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label clientLabel;
    private final Label deadlineLabel;

    public ProjectCardHandle(Node cardNode) {
        super(cardNode);
        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        clientLabel = getChildNode(CLIENT_ID);
        deadlineLabel = getChildNode(DEADLINE_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getProjectName() {
        return nameLabel.getText();
    }

    public String getClient() {
        return clientLabel.getText();
    }

    public String getDeadline() {
        return deadlineLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code project}.
     */
    public boolean equals(Project project) {
        return getProjectName().equals(project.getProjectName())
                && getClient().equals(project.getClient())
                && getDeadline().equals(project.getDeadline());
    }
}