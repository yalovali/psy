package tech.lova.views.abstracts;

import com.vaadin.flow.component.grid.Grid;

public class CTable<ObjectType> extends CPage {

	// Serial version UID for serialization compatibility
	private static final long serialVersionUID = 1L; // Added serialVersionUID
	protected Grid<ObjectType> grid; // Grid to display data

	/**
	 * Constructor for the CTable class. Initializes the table with padding.
	 */
	public CTable() {
		super(); // Call the constructor of the parent class
	}

	public Grid<ObjectType> getGrid() {
		return grid;
	}

	// Additional methods and properties for the table can be added here

}
