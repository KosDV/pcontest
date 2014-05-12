package hibernate.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "votes", catalog = "kaos")
public class Vote implements Serializable {
	private Integer id;
	private Set<Image> pictures = new HashSet<Image>(0);
	private Urn urn;

	public Vote() {
	}

	public Vote(Set<Image> pictures) {
		this.pictures = pictures;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "VOTE_ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "vote_pictures", catalog = "kaos",
			joinColumns = { @JoinColumn(name = "VOTE_ID", nullable = false,
					updatable = false) }, inverseJoinColumns = { @JoinColumn(
					name = "PICTURE_ID", nullable = false, updatable = false) })
	public Set<Image> getPictures() {
		return this.pictures;
	}

	public void setPictures(Set<Image> pictures) {
		this.pictures = pictures;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "URN_ID", nullable = false)
	public Urn getUrn() {
		return urn;
	}

	public void setUrn(Urn urn) {
		this.urn = urn;
	}

}
