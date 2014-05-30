package hibernate.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "URN", catalog = "kaos")
public class Urn implements Serializable {

	private Integer id;
	private String name;
	private Set<Vote> ListVotes = new HashSet<Vote>();

	public Urn() {
	}

	public Urn(String name) {
		this.setName(name);
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "URN_ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "urn")
	public Set<Vote> getListVotes() {
		return ListVotes;
	}

	public void setListVotes(Set<Vote> listVotes) {
		this.ListVotes = listVotes;
	}

	/*public Integer getNumVotes() {
		return this.ListVotes.size();
	}*/

	@Column(name = "URN_NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
