package tech.lova.base.domain;

import org.jspecify.annotations.Nullable;
import org.springframework.data.util.ProxyUtils;

import jakarta.persistence.MappedSuperclass;

/*
 * AbstractEntity serves as a base class for all entities in the application.
 * It provides a common structure for entity classes, including methods for
 * equality checks, hash code generation, and string representation.
 */
@MappedSuperclass
public abstract class AbstractEntity<ID> {
	// ID is the type of the identifier for the entity, allowing flexibility in the
	// type of ID used.
	// ID can be any type, such as Long, String, etc., depending on the specific
	// entity implementation.

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		else if (obj == this)
			return true;

		var thisUserClass = ProxyUtils.getUserClass(getClass());
		var otherUserClass = ProxyUtils.getUserClass(obj);
		if (thisUserClass != otherUserClass)
			return false;

		var id = getId();
		return (id != null) && id.equals(((AbstractEntity<?>) obj).getId());
	}

	/**
	 * Returns the identifier of the entity. this method should be implemented by
	 * subclasses to provide the
	 *
	 * @return the identifier of the entity, or null if not set
	 */
	public abstract @Nullable ID getId();

	@Override
	public int hashCode() {
		// Hashcode should never change during the lifetime of an object. Because of
		// this we can't use getId() to calculate the hashcode. Unless you have sets
		// with lots of entities in them, returning the same hashcode should not be a
		// problem.
		return ProxyUtils.getUserClass(getClass()).hashCode();
	}

	@Override
	public String toString() {
		return "%s{id=%s}".formatted(getClass().getSimpleName(), getId());
	}

}
