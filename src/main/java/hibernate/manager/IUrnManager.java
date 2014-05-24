package hibernate.manager;

import hibernate.model.Urn;

import java.util.List;

public interface IUrnManager {
	public List<Urn> loadAllUrns();

	public Urn findUrnById(Integer id);

	public void saveNewUrn(Urn urn);

	public void updateUrn(Urn urn);

}
