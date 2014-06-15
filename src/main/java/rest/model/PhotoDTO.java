package rest.model;

import hibernate.model.Photo;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement(name = "photo")
public class PhotoDTO {
	@JsonProperty("title")
	String title;
	@JsonProperty("description")
	String description;
	@JsonProperty("fileName")
	String filename;
	@JsonProperty("date")
	String date;
	@JsonProperty("latLng")
	String coordinates;
	@JsonProperty("dimensions")
	String dimensions;
	@JsonProperty("brand")
	String brand;
	@JsonProperty("model")
	String model;
	@JsonProperty("flash")
	String flash;
	@JsonProperty("ISO")
	String ISO;
	@JsonProperty("focalLength")
	String focalLength;
	@JsonProperty("fNum")
	String fNum;
	@JsonProperty("exposureTime")
	String exposureTime;
	@JsonProperty("url")
	String url;

	public PhotoDTO() {
		super();
	}

	public PhotoDTO(Photo photo) {
		super();
		this.title = photo.getTitle();
		this.description = photo.getDescription();
		this.filename = photo.getFilename();
		this.date = photo.getDate();
		this.coordinates = photo.getCoordinates();
		this.dimensions = photo.getDimensions();
		this.brand = photo.getBrand();
		this.model = photo.getModel();
		this.flash = photo.getFlash();
		this.ISO = photo.getISO();
		this.focalLength = photo.getFocalLength();
		this.fNum = photo.getfNum();
		this.exposureTime = photo.getExposureTime();
		this.url = photo.getUrl();
	}

	public PhotoDTO(String filename) {
		this.filename = filename;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getFlash() {
		return flash;
	}

	public void setFlash(String flash) {
		this.flash = flash;
	}

	public String getISO() {
		return ISO;
	}

	public void setISO(String iSO) {
		ISO = iSO;
	}

	public String getFocalLength() {
		return focalLength;
	}

	public void setFocalLength(String focalLength) {
		this.focalLength = focalLength;
	}

	public String getfNum() {
		return fNum;
	}

	public void setfNum(String fNum) {
		this.fNum = fNum;
	}

	public String getExposureTime() {
		return exposureTime;
	}

	public void setExposureTime(String exposureTime) {
		this.exposureTime = exposureTime;
	}
}
