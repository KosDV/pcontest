package rest.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement(name = "status")
public class StatusDTO {
	@JsonProperty("code")
	Integer code;
	@JsonProperty("message")
	String message;
	@JsonProperty("contest")
	Integer contestStatus;
	@JsonProperty("photo")
	Boolean photoStatus;

	public StatusDTO() {
		super();
	}

	public StatusDTO(Integer code, String message, Integer contestStatus, Boolean photoStatus) {
		this.code = code;
		this.message = message;
		this.contestStatus = contestStatus;
		this.photoStatus = photoStatus;
	}
}
