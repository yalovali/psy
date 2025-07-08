package tech.lova.users.view;

import com.vaadin.flow.component.grid.Grid;

import tech.lova.users.domain.CUser;
import tech.lova.views.abstracts.CTableDB;

public class CTableUsers extends CTableDB<CUser> {

	// Serial version UID for serialization compatibility
	private static final long serialVersionUID = 1L; // Added serialVersionUID

	/**
	 * Constructor for the users table. Initializes the table with user data.
	 */
	public CTableUsers() {
		super(); // Call the constructor of the parent class
		// Additional initialization for user-specific table can be added here
	}

	protected void createGrid() {
		grid = new Grid<>(CUser.class, false);
		grid.addColumn(CUser::getId).setHeader("Id");
		grid.addColumn(CUser::getName).setHeader("Name");
		grid.addColumn(CUser::getEmail).setHeader("Email");
		getContent().add(grid); // Add the grid to the content of the table
	}
	// Additional methods specific to user table can be added here

}
