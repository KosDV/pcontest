package rest.model;

import hibernate.model.Picture;
import hibernate.model.User;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement(name = "user")
public class UserBean {
	@JsonProperty("id")
	Integer id;
	@JsonProperty("name")
	String name;
	@JsonProperty("surname")
	String surname;
	@JsonProperty("birth")
	String birth;
	@JsonProperty("email")
	String email;
	@JsonProperty("nif")
	String nif;
	@JsonProperty("image")
	String image;
	@JsonProperty("voted")
	String voted = "NO"; // YES NO

	public UserBean() {
		super();
	}
	
	public UserBean(User user){
		this.id = user.getId();
		this.name = user.getName();
		this.birth = user.getBirth();
		this.email = user.getEmail();
		this.image = user.getImage().getTitle();
		this.nif = user.getNif();
		this.surname = user.getSurname();
		this.voted = user.getVoted();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getVoted() {
		return voted;
	}

	public void setVoted(String voted) {
		this.voted = voted;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
