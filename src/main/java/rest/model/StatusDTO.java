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
	String contestStatus;

	public StatusDTO() {
		super();
	}

	public StatusDTO(Integer code, String message, String contestStatus) {
		this.code = code;
		this.message = message;
		this.contestStatus = contestStatus;
	}
}
