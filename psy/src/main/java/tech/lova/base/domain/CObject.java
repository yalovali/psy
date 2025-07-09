package tech.lova.base.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;

/**
 * CObject serves as a base class for all objects in the application. It extends
 * AbstractEntity with a Long identifier type and provides a common structure
 * for entity classes, including methods for equality checks, hash code
 * generation, and string representation.
 */
@MappedSuperclass
public abstract class CObject {
	public static final int DESCRIPTION_MAX_LENGTH = 255;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgenerator")
	// The initial value is to account for data.sql demo data ids
	@SequenceGenerator(name = "idgenerator", initialValue = 1000)
	private Long id;

	// field for name of the object, the name is mandatory for all objects
	@Column(nullable = false)
	private String name;

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof final AbstractEntity that))
			return false; // null or not an AbstractEntity class
		if (getId() != null)
			return getId().equals(that.getId());
		return super.equals(that);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		if (getId() != null)
			return getId().hashCode();
		return super.hashCode();
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
