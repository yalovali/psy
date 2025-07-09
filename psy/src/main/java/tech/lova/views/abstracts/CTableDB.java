package tech.lova.views.abstracts;

public abstract class CTableDB<ObjectType> extends CTable<ObjectType> {

	// Serial version UID for serialization compatibility
	private static final long serialVersionUID = 1L; // Added serialVersionUID
	// List<ObjectType> people = DataService.getPeople();

	/**
	 * Constructor for the CTableDB class. Initializes the table with padding.
	 */
	public CTableDB() {
		super(); // Call the constructor of the parent class
		init(); // Initialize the table with data and columns
	}

	// abstract void createGrid();
	protected abstract void createGrid();

	private void createTable() {
		// Create a grid to display data
		createGrid();
	}

	private void init() {
		// Initialize the table with data and columns
		createTable();
	}

}
