package hibernate.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Urn implements Serializable {

	private Integer id;
	private Set<Vote> ListVotes = new HashSet<Vote>();
	private Integer numVotes = ListVotes.size();

	public Urn(Set<Vote> listVotes, Integer numVotes) {
		ListVotes = listVotes;
		this.numVotes = listVotes.size();
	}

	public Set<Vote> getListVotes() {
		return ListVotes;
	}

	public void setListVotes(Set<Vote> listVotes) {
		ListVotes = listVotes;
	}

	public Integer getNumVotes() {
		return numVotes;
	}

	public void setNumVotes(Set<Vote> listVotes) {
		this.numVotes = listVotes.size();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private static final long serialVersionUID = 1L;

}
