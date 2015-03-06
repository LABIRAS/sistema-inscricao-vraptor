package cc.labiras.web.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cc.labiras.web.util.JSONable;

@Entity
public class User implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	
	@Column(nullable = false, length = 128, unique = true)
	private String username;
	
	@Column(nullable = false, length = 128, unique = true)
	private String email;
	
	@Column(nullable = false, length = 128)
	private String password;
	
	@Column(nullable = false, length = 64)
	private String role;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}
	
	public boolean isRole(final String role) {
		return this.role != null ? this.role.equals(role) : false;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(final Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(final Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(final Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Map<String, Object> json() {
		// TODO Auto-generated method stub
		return null;
	}
}
