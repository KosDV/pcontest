package api.model;

import hibernate.model.User;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement(name = "user")
public class RegisterDTO {
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
    Boolean voted = false; // YES NO
    @JsonProperty("password")
    String password;

    public RegisterDTO() {
	super();
    }

    public RegisterDTO(User user) {
	this.id = user.getId();
	this.name = user.getName();
	this.birth = user.getBirth();
	this.email = user.getEmail();
	this.image = user.getPhotos().get(0).getTitle();
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

    public Boolean getVoted() {
	return voted;
    }

    public void setVoted(Boolean voted) {
	this.voted = voted;
    }

    public String getImage() {
	return image;
    }

    public void setImage(String image) {
	this.image = image;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }
}
