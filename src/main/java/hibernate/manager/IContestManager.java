package hibernate.manager;

import hibernate.model.Contest;

import java.util.List;

public interface IContestManager {

	public Contest loadUrn();

	public void saveNewUrn(Contest urn);

	public void updateUrn(Contest urn);

}
