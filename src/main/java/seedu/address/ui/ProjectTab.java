package seedu.address.ui;

import javafx.scene.control.Tab;
import javafx.scene.layout.Region;
import javafx.scene.Node;

public class ProjectTab extends UiPart<Region> {
    private static final String FXML = "ProjectTab.fxml";
    public ProjectListPanel panel;

    @javafx.fxml.FXML
    private Tab tab;

    public ProjectTab(Node value) {
        super(FXML);
        this.tab = new Tab();
        this.tab.setContent(value);
    }
    public Tab getTab() {
        return this.tab;
    }

}