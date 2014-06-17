package hibernate.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "VOTE", catalog = "kaos")
public class Vote implements Serializable {
    private Integer id;
    private String encryptedVote;

    public Vote() {
    }

    public Vote(String encryptedVote) {
        this.setEncryptedVote(encryptedVote);
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

    @Column(name = "VOTE", length = 511)
    public String getEncryptedVote() {
        return encryptedVote;
    }

    public void setEncryptedVote(String encryptedVote) {
        this.encryptedVote = encryptedVote;
    }
}
