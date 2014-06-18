package hibernate.manager;

import hibernate.model.Contest;

public interface IContestManager {

    public Contest loadContest();

    public void saveNewContest(Contest contest);

    public void updateContest(Contest contest);

}
