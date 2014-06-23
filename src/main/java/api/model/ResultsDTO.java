package api.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class ResultsDTO {
    @JsonProperty("nif")
    String nif;
    @JsonProperty("votes")
    Integer votes;
    @JsonProperty("photo")
    VotedPhotosDTO photo;

    public ResultsDTO() {
	super();
    }

    public ResultsDTO(String nif, Integer votes, VotedPhotosDTO photo) {
	this.nif = nif;
	this.votes = votes;
	this.photo = photo;
    }

    public String getNif() {
	return nif;
    }

    public void setNif(String nif) {
	this.nif = nif;
    }

    public Integer getVotes() {
	return votes;
    }

    public void setVotes(Integer votes) {
	this.votes = votes;
    }

    public VotedPhotosDTO getPhoto() {
	return photo;
    }

    public void setPhoto(VotedPhotosDTO photo) {
	this.photo = photo;
    }

}
