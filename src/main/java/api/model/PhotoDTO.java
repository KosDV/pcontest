package api.model;

import hibernate.model.Photo;

import org.codehaus.jackson.annotate.JsonProperty;

public class PhotoDTO {
    @JsonProperty("title")
    String title;
    @JsonProperty("description")
    String description;
    @JsonProperty("fileName")
    String filename;
    @JsonProperty("id")
    Integer id;
    @JsonProperty("date")
    String date;
    @JsonProperty("gpsLatitude")
    String gpsLatitude;
    @JsonProperty("gpsLongitude")
    String gpsLongitude;
    @JsonProperty("width")
    String width;
    @JsonProperty("height")
    String height;
    @JsonProperty("make")
    String make;
    @JsonProperty("model")
    String model;
    @JsonProperty("flash")
    String flash;
    @JsonProperty("ISO")
    String ISO;
    @JsonProperty("focalLength")
    String focalLength;
    @JsonProperty("aperture")
    String aperture;
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
	this.id = photo.getId();
	this.date = photo.getDate();
	this.gpsLatitude = photo.getGpsLatitude();
	this.gpsLongitude = photo.getGpsLongitude();
	this.width = photo.getWidth();
	this.height = photo.getHeight();
	this.make = photo.getMake();
	this.model = photo.getModel();
	this.flash = photo.getFlash();
	this.ISO = photo.getISO();
	this.focalLength = photo.getFocalLength();
	this.aperture = photo.getAperture();
	this.exposureTime = photo.getExposureTime();
	this.url = photo.getUrl();
    }
}
