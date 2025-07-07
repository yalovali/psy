package tech.lova.base.ui.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.IconSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

import jakarta.annotation.security.PermitAll;
import tech.lova.security.CurrentUser;

/**
 * Represents the main layout of the application.
 *
 * This layout serves as the base structure for all views in the application. It
 * includes a header, a side navigation menu, and a user menu.
 *
 * Features: - Header: - Displays the application logo and name. - Styled with
 * Lumo utility classes for consistent spacing and alignment. - Side Navigation:
 * - Dynamically populated with menu entries from `MenuConfiguration`. - Each
 * menu entry is represented as a `SideNavItem` with optional icons. - Wrapped
 * in a `Scroller` for scrollable navigation. - User Menu: - Displays the
 * current user's avatar and name. - Includes options for viewing the user's
 * profile and logging out. - Styled with Lumo utility classes for consistent
 * spacing.
 *
 * Security: - Annotated with `@PermitAll` to allow access to all authenticated
 * users.
 *
 * Usage: - This layout is applied to views using the `@Layout` annotation. -
 * Provides a consistent structure and navigation experience across the
 * application.
 */
@Layout
@PermitAll // When security is enabled, allow all authenticated users
public final class MainLayout extends AppLayout {

	private static final long serialVersionUID = 1L;
	private final CurrentUser currentUser;
	private final AuthenticationContext authenticationContext;

	/**
	 * Constructor for the main layout.
	 *
	 * Initializes the layout with a header, side navigation, and user menu.
	 *
	 * @param currentUser           The current user information.
	 * @param authenticationContext The authentication context for managing user
	 *                              sessions.
	 */
	MainLayout(CurrentUser currentUser, AuthenticationContext authenticationContext) {
		this.currentUser = currentUser;
		this.authenticationContext = authenticationContext;
		setPrimarySection(Section.DRAWER); // Set the primary section to the drawer
		addToDrawer(createHeader(), new Scroller(createSideNav()), createUserMenu()); // Add components to the drawer
	}

	/**
	 * Creates the header component.
	 *
	 * The header includes the application logo and name, styled with Lumo utility
	 * classes.
	 *
	 * @return A `Div` containing the header components.
	 */
	private Div createHeader() {
		var appLogo = VaadinIcon.CUBES.create(); // Create the application logo
		appLogo.addClassNames(TextColor.PRIMARY, IconSize.LARGE); // Style the logo

		var appName = new Span("Psy"); // Create the application name
		appName.addClassNames(FontWeight.SEMIBOLD, FontSize.LARGE); // Style the name

		var header = new Div(appLogo, appName); // Combine logo and name into a header
		header.addClassNames(Display.FLEX, Padding.MEDIUM, Gap.MEDIUM, AlignItems.CENTER); // Style the header
		return header;
	}

	/**
	 * Creates the side navigation menu.
	 *
	 * The navigation menu is dynamically populated with menu entries from
	 * `MenuConfiguration`. Each entry is represented as a `SideNavItem` with
	 * optional icons.
	 *
	 * @return A `SideNav` component containing the navigation items.
	 */
	private SideNav createSideNav() {
		var nav = new SideNav(); // Create the side navigation
		nav.addClassNames(Margin.Horizontal.MEDIUM); // Style the navigation
		MenuConfiguration.getMenuEntries().forEach(entry -> createSideNavItem(nav, entry)); // Add menu
																							// entries
		return nav;
	}

	/**
	 * Creates a side navigation item for a given menu entry.
	 *
	 * Each menu entry is represented as a `SideNavItem` with optional icons.
	 *
	 * @param menuEntry The menu entry to create a navigation item for.
	 * @return A `SideNavItem` representing the menu entry.
	 */
	private void createSideNavItem(SideNav nav, MenuEntry menuEntry) {
		if (menuEntry == null)
			return; // Return null if the menu entry is null
		// read the menu entry properties
		String title = menuEntry.title();
		String path = menuEntry.path();
		String icon = menuEntry.icon();
		// if title contains a dot, it is a sub-menu entry
		if (title.contains(".")) {
			var parts = title.split("\\.");
			title = parts[parts.length - 1]; // Use the last part as the title
			String parent_title = parts[0]; // Use the first part as the parent title
			// find the parent menu entry
			SideNavItem parentItem = nav.getItems().stream().filter(item -> item.getLabel().equals(parent_title))
					.findFirst().orElse(null);
			if (parentItem == null) {
				parentItem = new SideNavItem(parent_title);
				parentItem.setPrefixComponent(new Icon(icon)); // Set the icon for the parent item
				nav.addItem(parentItem); // Add the parent item to the navigation
			}
			// Create a sub-menu item under the parent entry
			parentItem.addItem(new SideNavItem(title, path, new Icon(icon)));
		}
		else
			// Create a top-level menu item
			nav.addItem(new SideNavItem(title, path, new Icon(icon))); // Create item with
	}

	/**
	 * Creates the user menu component.
	 *
	 * The user menu displays the current user's avatar and name, along with options
	 * for viewing the profile and logging out.
	 *
	 * @return A `Component` representing the user menu.
	 */
	private Component createUserMenu() {
		var user = currentUser.require(); // Get the current user

		var avatar = new Avatar(user.getFullName(), user.getPictureUrl()); // Create the user's avatar
		avatar.addThemeVariants(AvatarVariant.LUMO_XSMALL); // Style the avatar
		avatar.addClassNames(Margin.Right.SMALL); // Add margin to the avatar
		avatar.setColorIndex(5); // Set the avatar color index

		var userMenu = new MenuBar(); // Create the user menu
		userMenu.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE); // Style the menu
		userMenu.addClassNames(Margin.MEDIUM); // Add margin to the menu

		var userMenuItem = userMenu.addItem(avatar); // Add the avatar to the menu
		userMenuItem.add(user.getFullName()); // Add the user's name to the menu
		if (user.getProfileUrl() != null)
			userMenuItem.getSubMenu().addItem("View Profile",
					event -> UI.getCurrent().getPage().open(user.getProfileUrl())); // Add "View Profile" option
		userMenuItem.getSubMenu().addItem("Logout", event -> authenticationContext.logout()); // Add "Logout" option

		return userMenu;
	}
}
