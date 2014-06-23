package api.model;

import hibernate.model.Photo;

import org.codehaus.jackson.annotate.JsonProperty;

public class VotedPhotosDTO {
    @JsonProperty("title")
    String title;
    @JsonProperty("description")
    String description;
    @JsonProperty("author")
    String author;
    @JsonProperty("gpsLatitude")
    String gpsLatitude;
    @JsonProperty("gpsLongitude")
    String gpsLongitude;
    @JsonProperty("url")
    String url;

    public VotedPhotosDTO() {
	super();
    }

    public VotedPhotosDTO(Photo photo) {
	super();
	this.title = photo.getTitle();
	this.description = photo.getDescription();
	this.gpsLatitude = photo.getGpsLatitude();
	this.gpsLongitude = photo.getGpsLongitude();
	this.author = photo.getUser().getName();
	this.url = photo.getUrl();
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

    public String getGpsLatitude() {
	return gpsLatitude;
    }

    public void setGpsLatitude(String gpsLatitude) {
	this.gpsLatitude = gpsLatitude;
    }

    public String getGpsLongitude() {
	return gpsLongitude;
    }

    public void setGpsLongitude(String gpsLongitude) {
	this.gpsLongitude = gpsLongitude;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getAuthor() {
	return author;
    }

    public void setAuthor(String author) {
	this.author = author;
    }
}
