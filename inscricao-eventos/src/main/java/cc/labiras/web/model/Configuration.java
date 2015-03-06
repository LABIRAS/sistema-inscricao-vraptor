package cc.labiras.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Configuration {
	@Id @GeneratedValue
	private Long id;
	
	@Column(nullable = false, unique = true, length = 128)
	private String config;
	
	@Column(length = 2048)
	private String value;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(final String config) {
		this.config = config;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}
}
