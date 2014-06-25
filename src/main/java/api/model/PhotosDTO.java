package api.model;

import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement(name = "PhotosToVote")
public class PhotosDTO {
    @JsonProperty("individualVoteLength")
    Integer individualVoteLength;

    @JsonProperty("totalVoteLength")
    Integer totalVoteLength;

    @JsonProperty("n")
    BigInteger n;

    @JsonProperty("g")
    BigInteger g;

    @JsonProperty("listPhotosToVote")
    List<PhotoDTO> listPhotosToVote;

    @JsonProperty("votes")
    Integer votes;

    public PhotosDTO() {
	super();
    }

    public PhotosDTO(List<PhotoDTO> listPhotosToVote,
	    Integer individualVoteLength, Integer totalVoteLength, Integer votes) {
	this.listPhotosToVote = listPhotosToVote;
	this.individualVoteLength = individualVoteLength;
	this.totalVoteLength = totalVoteLength;
	this.votes = votes;
    }

    public PhotosDTO(List<PhotoDTO> listPhotosToVote,
	    Integer individualVoteLength, Integer totalVoteLength,
	    BigInteger n, BigInteger g, Integer votes) {
	this.listPhotosToVote = listPhotosToVote;
	this.individualVoteLength = individualVoteLength;
	this.totalVoteLength = totalVoteLength;
	this.n = n;
	this.g = g;
	this.votes = votes;
    }

    public Integer getIndividualVoteLength() {
	return individualVoteLength;
    }

    public void setIndividualVoteLength(Integer individualVoteLength) {
	this.individualVoteLength = individualVoteLength;
    }

    public Integer getTotalVoteLength() {
	return totalVoteLength;
    }

    public void setTotalVoteLength(Integer totalVoteLength) {
	this.totalVoteLength = totalVoteLength;
    }

    public List<PhotoDTO> getListPhotosToVote() {
	return listPhotosToVote;
    }

    public void setListPhotosToVote(List<PhotoDTO> listPhotosToVote) {
	this.listPhotosToVote = listPhotosToVote;
    }

    public BigInteger getN() {
	return n;
    }

    public void setN(BigInteger n) {
	this.n = n;
    }

    public BigInteger getG() {
	return g;
    }

    public void setG(BigInteger g) {
	this.g = g;
    }

    public Integer getVotes() {
	return votes;
    }

    public void setVotes(Integer votes) {
	this.votes = votes;
    }

}
