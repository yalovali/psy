package tech.lova.views.abstracts;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

/*
 * Abstract base class for application pages.
 * Provides common properties and functionality for all pages.
 */
@SuppressWarnings("serial")
public abstract class CAbstractPage extends Composite<VerticalLayout> {
//extends Main {
	/**
	 * Constructor for the abstract page. Initializes the page with default padding.
	 */
	protected CAbstractPage() {
		addClassName(LumoUtility.Padding.MEDIUM); // Apply medium padding to all pages
		getContent().setSizeFull(); // Set the content to take full size
		getContent().getStyle().set("flex-grow", "1"); // Allow the content to grow
		getContent().setDefaultHorizontalComponentAlignment(VerticalLayout.Alignment.CENTER); // Center align content
		getContent().setPadding(true); // Enable padding for the content
		getContent().setSpacing(true); // Enable spacing between components
		getContent().setMargin(true); // Enable margin for the content
		getContent().setWidth("100%"); // Set the content width to 100%
		getContent().getStyle().set("flex-grow", "1"); // Ensure the content can grow
		getContent().setHeightFull(); // Set the content height to full
		getContent().setAlignItems(VerticalLayout.Alignment.CENTER); // Center align items in the content

		getContent().setJustifyContentMode(VerticalLayout.JustifyContentMode.CENTER); // Center justify content

		// Additional styles can be added here if needed
		getContent().getStyle().set("overflow", "auto"); // Enable scrolling if content overflows
		getContent().getStyle().set("box-sizing", "border-box"); // Ensure box-sizing is set to border-box
		getContent().getStyle().set("display", "flex"); // Use flexbox for layout

	}

	/**
	 * Adds a message to the page.
	 *
	 * @param message The message to display.
	 */
	public void addMessage(final String message) {
		getContent().add(new Div(message)); // Ensure the content is initialized
	}
}