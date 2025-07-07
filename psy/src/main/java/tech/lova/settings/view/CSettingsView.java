package tech.lova.settings.view;

import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;
import tech.lova.views.abstracts.CAbstractTabPage;
import tech.lova.views.abstracts.CPage;

/**
 * Represents the settings page view in the application.
 *
 * This view is accessible at the '/settings' route and is displayed to all
 * authenticated users. It is annotated with @Menu to include it in the
 * application's navigation menu.
 *
 * Features: - Displays a welcome message for the settings page. - Styled with
 * Lumo utility classes for consistent padding. - Configured with menu metadata
 * such as order, icon, and title.
 */
//menu annotation doesnot support parents
@Route("settings")
@Menu(order = 0, icon = "vaadin:asterisk", title = "Administration.Settings")
@PermitAll // When security is enabled, allow all authenticated users
public final class CSettingsView extends CAbstractTabPage {
	// Serial version UID for serialization compatibility
	private static final long serialVersionUID = 1L; // Added serialVersionUID

	/**
	 * Constructor for the settings view. Initializes the view with padding and adds
	 * a welcome message.
	 */
	public CSettingsView() {
		super(); // Call the constructor of the parent class
		final CPage page1 = new CPage(); // Create a new page instance
		page1.addMessage("This is the first page!"); // Add a welcome message to the page
		final CPage page2 = new CPage(); // Create another page instance
		page2.addMessage("This is the second page!"); // Add a message to the second page
		addTab("Database", page1); // Add a tab for the users list
		addTab("System", page2); // Add a tab for the users list

	}
}
