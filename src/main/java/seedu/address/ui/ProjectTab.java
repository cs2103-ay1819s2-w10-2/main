package seedu.address.ui;

import javafx.scene.control.Tab;
import javafx.scene.layout.Region;
import javafx.scene.Node;

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