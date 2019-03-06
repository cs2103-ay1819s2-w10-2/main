package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalEmployees.ALICE;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;

public class BrowserPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Employee> selectedEmployee = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Project> selectedProject = new SimpleObjectProperty<>();
    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> browserPanel = new BrowserPanel(selectedEmployee, selectedProject));
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        assertEquals(BrowserPanel.DEFAULT_PAGE, browserPanelHandle.getLoadedUrl());

        // associated web page of a employee
        guiRobot.interact(() -> selectedEmployee.set(ALICE));
        URL expectedEmployeeUrl = new URL(BrowserPanel.SEARCH_PAGE_URL
                                                + ALICE.getName().fullName.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedEmployeeUrl, browserPanelHandle.getLoadedUrl());
    }
}
