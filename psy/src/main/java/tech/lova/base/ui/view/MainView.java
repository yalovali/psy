package tech.lova.base.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import jakarta.annotation.security.PermitAll;
import tech.lova.base.ui.component.ViewToolbar;

/**
 * Represents the main view of the application.
 *
 * This view is displayed when a user navigates to the root ('/') of the
 * application. It provides a default layout with a toolbar and a placeholder
 * message.
 *
 * Features: - Styled with Lumo utility classes for consistent padding. -
 * Includes a toolbar with the title "Main". - Displays a message prompting the
 * user to select a view from the menu. - Accessible to all authenticated users
 * due to the @PermitAll annotation.
 *
 * Usage: - The static method `showMainView()` can be used to navigate to this
 * view programmatically. - This view serves as the entry point for the
 * application.
 */
@Route
@PermitAll // When security is enabled, allow all authenticated users
public final class MainView extends Main {

	private static final long serialVersionUID = 1L;

	/**
	 * Navigates to the main view.
	 *
	 * This method can be called to programmatically navigate to the main view.
	 */
	public static void showMainView() {
		UI.getCurrent().navigate(MainView.class);
	}

	/**
	 * Constructor for the main view.
	 *
	 * Initializes the view with default padding, a toolbar, and a placeholder
	 * message.
	 */
	MainView() {
		addClassName(LumoUtility.Padding.MEDIUM); // Apply medium padding to the view
		add(new ViewToolbar("Main")); // Add a toolbar with the title "Main"
		add(new Div("Please select a view from the menu on the left.")); // Add a placeholder message
	}
}
