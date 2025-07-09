package tech.lova.views.abstracts;

import java.util.Optional;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

public abstract class CMasterDetailView<CEntityClass> extends CAbstractPage implements BeforeEnterObserver {

	protected final Grid<CEntityClass> grid;
	protected final Button btnCancel = new Button("Cancel");
	protected final Button btnSave = new Button("Save");
	protected final BeanValidationBinder<CEntityClass> binder;
	public CEntityClass currentItem;
	CAbstractService<CEntityClass> service; // Service for managing CEntityClass entities

	protected final String CUSER_ID = "id"; // Route parameter for CUser ID

	// constructor to initialize the view
	public CMasterDetailView(final CAbstractService<CEntityClass> service) {
		super(); // Call the superclass constructor
		this.service = service; // Initialize the service for data access
		binder = createBinder(); // Create a binder for data binding
		grid = createGrid(); // Create the grid for displaying CEntityClass items
	}

	@Override
	public void beforeEnter(final BeforeEnterEvent event) {
		final Optional<Long> userID = event.getRouteParameters().get(CUSER_ID).map(Long::parseLong);
		if (userID.isPresent()) {
			/*
			 * final Optional<CEntityClass> cUserFromBackend = service.get(userID.get()); if
			 * (cUserFromBackend.isPresent()) populateForm(cUserFromBackend.get()); //
			 * Populate the form with the retrieved CUser else { // Show notification if the
			 * user is not found
			 * Notification.show(String.format("The requested user was not found, ID = %s",
			 * userID.get()), 3000, Notification.Position.BOTTOM_START); refreshGrid(); //
			 * Refresh the grid event.forwardTo(CUsersView.class); // Navigate back to the
			 * main view }
			 */
		}
	}

	protected void clearForm() {
		populateForm(null); // Clear the form by setting the binder to null
	}

	protected abstract BeanValidationBinder<CEntityClass> createBinder();

	// abstract method to create the grid
	protected abstract Grid<CEntityClass> createGrid();

	public void populateForm(final CEntityClass value) {
		currentItem = value; // Set the current CUser
		binder.readBean(currentItem); // Bind the CUser to the form fields
	}

	public void refreshGrid() {
		grid.select(null); // Deselect any selected row
		grid.getDataProvider().refreshAll(); // Refresh the grid's data provider
	}

}
