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
    @JsonProperty("latLng")
    String coordinates;
    @JsonProperty("url")
    String url;

    public VotedPhotosDTO() {
	super();
    }

    public VotedPhotosDTO(Photo photo) {
	super();
	this.title = photo.getTitle();
	this.description = photo.getDescription();
	this.coordinates = photo.getCoordinates();
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

    public String getCoordinates() {
	return coordinates;
    }

    public void setCoordinates(String coordinates) {
	this.coordinates = coordinates;
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
