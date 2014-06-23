package api.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement(name = "results")
public class ClosureDTO {
    @JsonProperty("census")
    Integer census;
    @JsonProperty("photos")
    Integer numPhotos;
    @JsonProperty("votes")
    Integer numVotes;
    @JsonProperty("results")
    List<ResultsDTO> results;

    public ClosureDTO() {
	super();
    }

    public ClosureDTO(Integer census, Integer numPhotos, Integer numVotes,
	    List<ResultsDTO> results) {
	super();
	this.census = census;
	this.numPhotos = numPhotos;
	this.numVotes = numVotes;
	this.results = results;
    }

}
