package tech.lova.users.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import tech.lova.base.domain.CObject;

/**
 * CUser represents a user entity in the application. It extends CObject to
 * inherit common properties and methods for all objects, such as ID and name.
 * This class includes additional properties specific to users, such as email
 * and password.
 */
@Entity
public class CUser extends CObject {

	@Column(nullable = false, length = DESCRIPTION_MAX_LENGTH)
	@Size(max = DESCRIPTION_MAX_LENGTH)
	private String email;
	private String password;

	public CUser() {
		// Default constructor
		super();
	}

	public CUser(final String name, final String email, final String password) {
		setName(name);
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

}
