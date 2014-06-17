package rest.model;

import hibernate.model.Vote;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement(name = "vote")
public class VoteDTO {
    @JsonProperty("encrypted-vote")
    String voteEncrypted;

    public VoteDTO() {
	super();
    }

    public VoteDTO(Vote vote) {
	this.voteEncrypted = vote.getEncryptedVote();
    }

    public String getVoteEncrypted() {
	return voteEncrypted;
    }

    public void setVoteEncrypted(String voteEncrypted) {
	this.voteEncrypted = voteEncrypted;
    }

}
