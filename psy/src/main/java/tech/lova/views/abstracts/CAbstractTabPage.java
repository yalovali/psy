package tech.lova.views.abstracts;

import com.vaadin.flow.component.tabs.TabSheet;

public class CAbstractTabPage extends CAbstractPage {
	// Serial version UID for serialization compatibility
	private static final long serialVersionUID = 1L; // Added serialVersionUID
	private final TabSheet tabSheet; // Placeholder for the tab component, e.g., TabSheet

	/**
	 * Constructor for the abstract tab page. Initializes the page with default
	 * padding.
	 */
	protected CAbstractTabPage() {
		super(); // Call the constructor of the parent class
		tabSheet = new TabSheet(); // Initialize the tab sheet component
		tabSheet.setSizeFull(); // Set the tab sheet to take full size
		getContent().add(tabSheet); // Add the tab sheet to the page content
	}

	/**
	 * Sets the tab component for this page.
	 *
	 * @param tabSheet The TabSheet or similar component to be used for tabs.
	 */
	public void addTab(final String tabName, final CAbstractPage page) {
		if ((tabName == null) || (page == null))
			throw new IllegalArgumentException("Tab name and page cannot be null"); // Validate input parameters
		final int pos = getTabIndex(tabName);
		if (pos != -1)
			tabSheet.remove(tabSheet.getTabAt(pos)); // Remove existing tab if it already exists
		tabSheet.add(tabName, page);
	}

	public CAbstractPage getSelectedTab() {
		return getSelectedTab(); // Placeholder return, actual implementation needed
	}

	public CAbstractPage getTabByName(final String tabName) {
		final int pos = getTabIndex(tabName);
		if (pos == -1)
			return null;
		return (CAbstractPage) tabSheet.getComponent(tabSheet.getTabAt(pos)); // Return the tab component at the
																				// specified index
	}

	public int getTabIndex(final String tabName) {
		for (int i = 0; i < tabSheet.getTabCount(); i++)
			if (tabSheet.getTabAt(i).getLabel().equals(tabName))
				return i; // Return the index of the matching tab
		return -1; // Return null if no matching tab is found
	}

	public void selectTab(final String tabName) {
		final int pos = getTabIndex(tabName);
		if (pos == -1)
			return; // If the tab does not exist, do nothing
		tabSheet.setSelectedIndex(pos); // Select the tab at the specified index
	}
}
