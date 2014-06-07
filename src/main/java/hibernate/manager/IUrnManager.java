package hibernate.manager;

import hibernate.model.Urn;

import java.util.List;

public interface IUrnManager {

	public Urn loadUrn();

	public void saveNewUrn(Urn urn);

	public void updateUrn(Urn urn);

}
