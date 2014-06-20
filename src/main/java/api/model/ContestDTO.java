package api.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement(name = "contest")
public class ContestDTO {
    @JsonProperty("status")
    Integer status;

    public ContestDTO() {
	super();
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }
}
