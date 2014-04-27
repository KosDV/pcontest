package hibernate.model;

import java.util.HashSet;
import java.util.Set;

public class Vote {
	private Integer id;
	private Set<Image> Pictures = new HashSet<Image>(5);

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Image> getPictures() {
		return Pictures;
	}

	public void setPictures(Set<Image> pictures) {
		Pictures = pictures;
	}

}
