package tech.lova.users.view;

import java.util.Optional;

import org.springframework.orm.ObjectOptimisticLockingFailureException;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

import jakarta.annotation.security.PermitAll;
import tech.lova.users.domain.CUser;
import tech.lova.users.service.CUserService;
import tech.lova.views.abstracts.CAbstractPage;

// Define the route for this view and set its menu properties
@Route("users/:id?/:action?(edit)")
@Menu(order = 0, icon = "vaadin:users", title = "Settings.Users")
@PermitAll // Allow all authenticated users to access this view
public class CUsersView extends CAbstractPage implements BeforeEnterObserver {

	// Constants for route parameters and navigation templates
	private final String CUSER_ID = "id";
	private final String CUSER_EDIT_ROUTE_TEMPLATE = "users/%s/edit";

	// Grid to display a list of CUser entities
	private final Grid<CUser> grid = new Grid<>(CUser.class, false);

	// Form fields for editing CUser properties
	// @PropertyId("name")
	private TextField txtName;
	@PropertyId("email")
	private TextField txtEmail;
	@PropertyId("password")
	private TextField txtPassword;

	// Buttons for form actions
	private final Button btnCancel = new Button("Cancel");
	private final Button btnSave = new Button("Save");

	// Binder for binding form fields to the CUser entity
	private final BeanValidationBinder<CUser> binder;

	// Currently selected CUser entity
	private CUser currentUser;

	// Service for managing CUser entities
	private final CUserService userService;

	// Constructor to initialize the view
	public CUsersView(final CUserService userService) {
		this.userService = userService;
		addClassNames("users-view"); // Add CSS class for styling

		// Create the main layout with a split view
		final SplitLayout splitLayout = new SplitLayout();
		splitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);

		// Create the grid and editor layouts
		// getContent().add(new Div("Users are here!"));

		// splitLayout.addToSecondary(new Div("asdfsaf"));
		createGridLayout(splitLayout);
		createEditorLayout(splitLayout);

		// Add the split layout to the view
		getContent().add(splitLayout);

		// Configure the grid columns
		grid.addColumn("name").setAutoWidth(true);
		grid.addColumn("email").setAutoWidth(true);
		grid.addColumn("password").setAutoWidth(true);
		// grid.addColumn(importantRenderer).setHeader("Important").setAutoWidth(true);

		grid.setItems(query -> userService.list(VaadinSpringDataHelpers.toSpringPageRequest(query)).stream());

		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER); // Add grid theme

		// Add a listener for row selection
		grid.asSingleSelect().addValueChangeListener(event -> {
			if (event.getValue() != null)
				// Navigate to the edit route for the selected user
				UI.getCurrent().navigate(String.format(CUSER_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
			else {
				// Clear the form and navigate back to the main view
				clearForm();
				UI.getCurrent().navigate(CUsersView.class);
			}
		});

		// Initialize the binder and bind form fields
		binder = new BeanValidationBinder<>(CUser.class);
		binder.bindInstanceFields(this);
		binder.bind(txtName, CUser::getName, CUser::setName);

		// Configure the cancel button
		btnCancel.addClickListener(e -> {
			clearForm(); // Clear the form
			refreshGrid(); // Refresh the grid
		});

		// Configure the save button
		btnSave.addClickListener(e -> {
			try {
				if (currentUser == null)
					currentUser = new CUser(); // Create a new CUser if none is selected
				binder.writeBean(currentUser); // Write form values to the CUser entity
				userService.save(currentUser); // Save the CUser entity
				clearForm(); // Clear the form
				refreshGrid(); // Refresh the grid
				Notification.show("Data updated"); // Show success notification
				UI.getCurrent().navigate(CUsersView.class); // Navigate back to the main view
			} catch (final ObjectOptimisticLockingFailureException exception) {
				// Show error notification if the record was updated by someone else
				final Notification n = Notification.show(
						"Error updating the data. Somebody else has updated the record while you were making changes.");
				n.setPosition(Position.MIDDLE);
				n.addThemeVariants(NotificationVariant.LUMO_ERROR);
			} catch (final ValidationException validationException) {
				// Show error notification if validation fails
				Notification.show("Failed to update the data. Check again that all values are valid");
			}
		});
	}

	// Method to handle navigation to this view
	@Override
	public void beforeEnter(final BeforeEnterEvent event) {
		final Optional<Long> cUserId = event.getRouteParameters().get(CUSER_ID).map(Long::parseLong);
		if (cUserId.isPresent()) {
			final Optional<CUser> cUserFromBackend = userService.get(cUserId.get());
			if (cUserFromBackend.isPresent())
				populateForm(cUserFromBackend.get()); // Populate the form with the retrieved CUser
			else {
				// Show notification if the user is not found
				Notification.show(String.format("The requested user was not found, ID = %s", cUserId.get()), 3000,
						Notification.Position.BOTTOM_START);
				refreshGrid(); // Refresh the grid
				event.forwardTo(CUsersView.class); // Navigate back to the main view
			}
		}
	}

	// Method to clear the form
	private void clearForm() {
		populateForm(null); // Clear the form by setting the binder to null
	}

	// Method to create the button layout
	private void createButtonLayout(final Div editorLayoutDiv) {
		final HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setClassName("button-layout");
		btnCancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY); // Style the cancel button
		btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY); // Style the save button
		buttonLayout.add(btnSave, btnCancel); // Add buttons to the layout
		editorLayoutDiv.add(buttonLayout); // Add the button layout to the editor layout
	}

	// Method to create the editor layout
	private void createEditorLayout(final SplitLayout splitLayout) {
		final Div editorLayoutDiv = new Div();
		editorLayoutDiv.setClassName("editor-layout");

		final Div editorDiv = new Div();
		editorDiv.setClassName("editor");
		editorLayoutDiv.add(editorDiv);

		// Create the form layout and add form fields
		final FormLayout formLayout = new FormLayout();
		txtName = new TextField("name");

		txtEmail = new TextField("email");
		txtPassword = new TextField("password");
		formLayout.add(txtName, txtEmail, txtPassword);

		editorDiv.add(formLayout); // Add the form layout to the editor
		createButtonLayout(editorLayoutDiv); // Add the button layout

		splitLayout.addToSecondary(editorLayoutDiv); // Add the editor layout to the split layout
	}

	// Method to create the grid layout
	private void createGridLayout(final SplitLayout splitLayout) {
		final Div wrapper = new Div();
		splitLayout.addToPrimary(wrapper); // Add the grid wrapper to the primary split layout
		wrapper.add(grid); // Add the grid to the wrapper
		wrapper.setClassName("grid-wrapper");
	}

	// Method to populate the form with a CUser entity
	private void populateForm(final CUser value) {
		currentUser = value; // Set the current CUser
		binder.readBean(currentUser); // Bind the CUser to the form fields
	}

	// Method to refresh the grid
	private void refreshGrid() {
		grid.select(null); // Deselect any selected row
		grid.getDataProvider().refreshAll(); // Refresh the grid's data provider
	}
}
