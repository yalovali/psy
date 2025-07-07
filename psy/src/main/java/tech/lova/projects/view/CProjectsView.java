package tech.lova.projects.view;

import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;
import tech.lova.views.abstracts.CAbstractTabPage;
import tech.lova.views.abstracts.CPage;

@Route("projects") // Route for the users view
@Menu(order = 0, icon = "vaadin:chart-3d", title = "Settings.Projects")
@PermitAll // When security is enabled, allow all authenticated users
public class CProjectsView extends CAbstractTabPage {
	// Serial version UID for serialization compatibility
	private static final long serialVersionUID = 1L; // Added serialVersionUID

	/**
	 * Constructor for the users view. Initializes the view with padding and sets up
	 * the tab sheet.
	 */
	public CProjectsView() {
		super(); // Call the constructor of the parent class
		final CPage page1 = new CPage(); // Create a new page instance
		page1.addMessage("This is the first page!"); // Add a welcome message to the page
		final CPage page2 = new CPage(); // Create another page instance
		page2.addMessage("This is the second page!"); // Add a message to the second page
		addTab("Projects List", page1); // Add a tab for the users list
		addTab("Allocation", page2); // Add a tab for the users list
	}

}
