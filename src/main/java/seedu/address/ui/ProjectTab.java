package seedu.address.ui;

import javafx.scene.control.Tab;
import javafx.scene.Node;
import javafx.scene.layout.Region;

/**
 * Tab to contain project list
 */
public class ProjectTab extends UiPart<Region> {
    private static final String FXML = "ProjectTab.fxml";

    @javafx.fxml.FXML
    private Tab tab;

    public ProjectTab(Node value) {
        super(FXML);
        this.tab = new Tab("Project", value);
    }

    public Tab getTab() {
        return this.tab;
    }
}
