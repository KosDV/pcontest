package hibernate.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "picture", catalog = "kaos")
public class Picture implements Serializable {

	private Integer id;
	private Integer width;
	private Integer height;
	private String title;
	private String description;
	private String coordinates;
	private User user;

	public Picture() {
	}

	public Picture(Integer width, Integer height, String title,
			String description, User user) {
		super();
		this.width = width;
		this.height = height;
		this.title = title;
		this.description = description;
		this.user = user;
	}
	
	public Picture(Integer width, Integer height, String title,
			String description) {
		super();
		this.width = width;
		this.height = height;
		this.title = title;
		this.description = description;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "PICTURE_ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "WIDTH")
	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	@Column(name = "HEIGHT")
	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@Column(name = "TITLE", unique = true, nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "DESCRIPTION", nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "COORDINATES")
	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
