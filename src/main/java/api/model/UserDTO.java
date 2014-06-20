package api.model;

import hibernate.model.User;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement(name = "user")
public class UserDTO {
	@JsonProperty("name")
	String name;
	@JsonProperty("surname")
	String surname;

	public UserDTO() {
		super();
	}

	public UserDTO(User user) {
		this.name = user.getName();
		this.surname = user.getSurname();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
}
