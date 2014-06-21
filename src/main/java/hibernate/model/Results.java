package hibernate.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RESULTS", catalog = "kaos")
public class Results implements Serializable {

    private static final long serialVersionUID = -8392355803801782160L;
    private Integer id;
    private Integer photoId;
    private Integer votes;
    private Contest contest;

    public Results() {
    }

    public Results(Integer photoId, Integer votes, Contest contest) {
	this.photoId = photoId;
	this.votes = votes;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "RESULT_ID", unique = true, nullable = false)
    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    @Column(name = "PHOTO_ID", unique = true)
    public Integer getPhotoId() {
	return photoId;
    }

    public void setPhotoId(Integer photoId) {
	this.photoId = photoId;
    }

    @Column(name = "VOTES")
    public Integer getVotes() {
	return votes;
    }

    public void setVotes(Integer votes) {
	this.votes = votes;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CONTEST_ID", nullable = false)
    public Contest getContest() {
	return contest;
    }

    public void setContest(Contest contest) {
	this.contest = contest;
    }

}
