package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;

/**
 * Panel of the main window to show all information or employees and projects
 */
public class DetailsPanel extends UiPart<Region> {

    private static final int INITIAL_PANEL_INDEX = 0;
    private static final Logger logger = LogsCenter.getLogger(DetailsPanel.class);
    private static final String FXML = "DetailsPanel.fxml";
    private EmployeeDetails employeeDetails;
    private ProjectDetails projectDetails;

    private List<Node> contentList;
    private int currentPanelIndex;

    @FXML
    private BorderPane borderPanel;

    @FXML
    private StackPane informationPanel;

    @FXML
    private Button prevBtn;

    @FXML
    private Button nextBtn;

    public DetailsPanel(ObservableValue<Employee> selectedEmployee, ObservableValue<Project> selectedProject) {
        super(FXML);
        initPrevBtn();
        initNextBtn();
        initDefaultView();
        this.currentPanelIndex = INITIAL_PANEL_INDEX;

        selectedEmployee.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.info("Loading new employee details");
                employeeDetails = new EmployeeDetails(selectedEmployee.getValue());
                refreshEmployeeContent();
            }
        });

        selectedProject.addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.info("loading new project details");
                projectDetails = new ProjectDetails(selectedProject.getValue());
                refreshProjectContent();
            }
        }));
    }

    /**
     * Refreshes the content of the panel to the most updated employee selection
     */
    private void refreshEmployeeContent() {
        currentPanelIndex = INITIAL_PANEL_INDEX;
        setContent(employeeDetails.getEmployeeDetails());
        updateInformationPanel(contentList.get(currentPanelIndex));
    }

    /**
     * Refreshes the content of the panel to the most updated project selection
     */
    private void refreshProjectContent() {
        currentPanelIndex = INITIAL_PANEL_INDEX;
        setContent(projectDetails.getProjectDetails());
        updateInformationPanel(contentList.get(currentPanelIndex));
    }

    /**
     * Initialises a default list of panels to show when nothing is selected.
     */
    private void initDefaultView() {
        Pane pane = new Pane();
        contentList = new ArrayList<>();
        contentList.add(pane);
        updateInformationPanel(pane);
    }

    private void updateInformationPanel(Node e) {
        informationPanel.getChildren().clear();
        informationPanel.getChildren().add(e);
    }

    /**
     * Updates the content in the information panel to the list of content provided.
     * @param content
     */
    private void setContent(List<Node> content) {
        contentList.clear();
        contentList.addAll(content);
    }

    private void initPrevBtn() {
        prevBtn.setOnAction((actionEvent) -> showPrevPanel());
    }

    private void initNextBtn() {
        nextBtn.setOnAction((actionEvent) -> showNextPanel());
    }

    /**
     * Shows the panel before the current panel in the contents list
     */
    private void showPrevPanel() {
        int nextIndex = currentPanelIndex - 1;
        if (nextIndex < 0) {
            currentPanelIndex = 0;
        } else {
            currentPanelIndex = nextIndex;
        }
        logger.info("Current pane: " + currentPanelIndex);
        updateInformationPanel(contentList.get(currentPanelIndex));
    }

    /**
     * Shows the panel after the current panel in the contents list
     */
    private void showNextPanel() {
        int nextIndex = currentPanelIndex + 1;
        if (nextIndex > contentList.size() - 1) {
            currentPanelIndex = contentList.size() - 1;
        } else {
            currentPanelIndex = nextIndex;
        }
        logger.info("Current pane: " + currentPanelIndex);
        updateInformationPanel(contentList.get(currentPanelIndex));
    }

}