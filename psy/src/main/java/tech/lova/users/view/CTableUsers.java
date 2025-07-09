package tech.lova.users.view;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringPageRequest;

import com.vaadin.flow.component.grid.Grid;

import tech.lova.users.domain.CUser;
import tech.lova.users.service.CUserService;
import tech.lova.views.abstracts.CTableDB;

public class CTableUsers extends CTableDB<CUser> {

	// Serial version UID for serialization compatibility
	private static final long serialVersionUID = 1L; // Added serialVersionUID
	private final CUserService service; // Service to handle user data

	/**
	 * Constructor for the users table. Initializes the table with user data.
	 */
	public CTableUsers(final CUserService service) {
		super(); // Call the constructor of the parent class
		// Additional initialization for user-specific table can be added here
		this.service = service; // Set the user service
	}

	protected void createGrid() {
		grid = new Grid<>(CUser.class, false);
		grid.addColumn(CUser::getId).setHeader("Id");
		grid.addColumn(CUser::getName).setHeader("Name");
		grid.addColumn(CUser::getEmail).setHeader("Email");
		getContent().add(grid); // Add the grid to the content of the table
		grid.setItems(query -> service.list(toSpringPageRequest(query)).stream());
		grid.getDataProvider().refreshAll();

	}
	// Additional methods specific to user table can be added here

}
