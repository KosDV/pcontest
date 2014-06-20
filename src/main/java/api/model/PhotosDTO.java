package api.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement(name = "PhotosToVote")
public class PhotosDTO {
    @JsonProperty("individual-vote-length")
    Integer individualVoteLength;

    @JsonProperty("total-vote-length")
    Integer totalVoteLength;

    @JsonProperty("listPhotosToVote")
    List<PhotoDTO> listPhotosToVote;

    public PhotosDTO() {
	super();
    }

    public PhotosDTO(List<PhotoDTO> listPhotosToVote,
	    Integer individualVoteLength, Integer totalVoteLength) {
	this.listPhotosToVote = listPhotosToVote;
	this.individualVoteLength = individualVoteLength;
	this.totalVoteLength = totalVoteLength;
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
}
