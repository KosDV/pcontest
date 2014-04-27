package hibernate.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "user", catalog = "kaos")
public class User implements Serializable {
	
	private Integer id;
	private String name;
	private String surname;
	private String birth;
	private String email;
	private String password;
	private String nif;
	private Image image;
	private Integer counter; 
	
	public User() {
	}

	public User(String name, String surname, String birth, String email,
			String password, String nif, Integer counter) {
		super();
		this.name = name;
		this.surname = surname;
		this.birth = birth;
		this.email = email;
		this.password = password;
		this.nif = nif;
		this.counter = counter;
	}

	public User(String name, String surname, String birth, String email,
			String password, String nif, Integer counter, Image image) {
		super();
		this.name = name;
		this.surname = surname;
		this.birth = birth;
		this.email = email;
		this.password = password;
		this.nif = nif;
		this.counter = counter;
		this.image = image;
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

	@Column(name = "NAME", unique = true, nullable = false)
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
	
	@Column(name = "COUNTER", unique = false)
	public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	
}
