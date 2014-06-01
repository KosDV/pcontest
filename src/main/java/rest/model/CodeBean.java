package rest.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement(name = "code")
public class CodeBean {
	@JsonProperty("code")
	Integer code;
	@JsonProperty("message")
	String message;

	public CodeBean() {
		super();
	}

	public CodeBean(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
