package hibernate.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CONTEST", catalog = "kaos")
public class Contest implements Serializable {

    private static final long serialVersionUID = -5645445270624904864L;
    private Integer id;
    private String results; // Probably we move this column into a new class.
    private Integer contestStatus;
    private Integer numVotes;
    private Integer numPhotos;
    private Integer numCensus;
    private Date date;

    public Contest(Integer contestStatus, Date date) {
	this.contestStatus = contestStatus;
	this.date = date;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CONTEST_ID", unique = true, nullable = false)
    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    @Column(name = "CONTEST_STATUS", nullable = false)
    public Integer getContestStatus() {
	return contestStatus;
    }

    public void setContestStatus(Integer contestStatus) {
	this.contestStatus = contestStatus;
    }

    @Column(name = "CONTEST_RESULTS")
    public String getResults() {
	return results;
    }

    public void setResults(String results) {
	this.results = results;
    }

    @Column(name = "NUM_VOTES")
    public Integer getNumVotes() {
	return numVotes;
    }

    public void setNumVotes(Integer numVotes) {
	this.numVotes = numVotes;
    }

    @Column(name = "NUM_PHOTOS")
    public Integer getNumPhotos() {
	return numPhotos;
    }

    public void setNumPhotos(Integer numPhotos) {
	this.numPhotos = numPhotos;
    }

    @Column(name = "CENSUS")
    public Integer getNumCensus() {
	return numCensus;
    }

    public void setNumCensus(Integer numCensus) {
	this.numCensus = numCensus;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE", nullable = false)
    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }
}
