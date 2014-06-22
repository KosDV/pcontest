package hibernate.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "USER", catalog = "kaos")
public class User implements Serializable {

    private Integer id;
    private String name;
    private String surname;
    private String birth;
    private String email;
    private String password;
    private Integer salt;
    private String nif;
    private List<Photo> photos = new ArrayList<Photo>();
    private Boolean voted;
    private Boolean isAdmin;

    public User() {

    }

    public User(String name, String surname, String birth, String email,
	    String password, String nif) {
	super();
	this.name = name;
	this.surname = surname;
	this.birth = birth;
	this.email = email;
	this.password = password;
	this.nif = nif;
	this.voted = false;
	this.isAdmin = false;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "USER_ID", unique = true, nullable = false)
    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    @Column(name = "NAME", nullable = false)
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Column(name = "SURNAME", nullable = false)
    public String getSurname() {
	return surname;
    }

    public void setSurname(String surname) {
	this.surname = surname;
    }

    @Column(name = "BIRTH", nullable = false)
    public String getBirth() {
	return birth;
    }

    public void setBirth(String birth) {
	this.birth = birth;
    }

    @Column(name = "EMAIL", unique = true, nullable = false)
    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    @Column(name = "PASSWORD", nullable = false)
    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    @Column(name = "NIF", unique = true, nullable = false)
    public String getNif() {
	return nif;
    }

    public void setNif(String nif) {
	this.nif = nif;
    }

    @Column(name = "VOTED", nullable = false)
    public Boolean getVoted() {
	return voted;
    }

    public void setVoted(Boolean voted) {
	this.voted = voted;
    }

    @Column(name = "isADMIN", nullable = false)
    public Boolean getIsAdmin() {
	return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
	this.isAdmin = isAdmin;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    public List<Photo> getPhotos() {
	return photos;
    }

    public void setPhotos(List<Photo> photos) {
	this.photos = photos;
    }

    @Column(name = "SALT")
    public Integer getSalt() {
	return salt;
    }

    public void setSalt(Integer randInt) {
	this.salt = randInt;
    }

}
