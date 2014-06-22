package hibernate.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "PHOTO", catalog = "kaos")
public class Photo implements Serializable {

    private Integer id;
    private String title;
    private String description;
    private String filename;
    private Integer salt;
    private String url;
    private String date;
    private String coordinates;
    private String dimensions;
    private String brand;
    private String model;
    private String flash;
    private String ISO;
    private String focalLength;
    private String fNum;
    private String exposureTime;
    private User user;

    public Photo() {
	super();
    }

    public Photo(String title, String description, String filename,
	    String date, Integer salt, String url) {
	super();
	this.title = title;
	this.description = description;
	this.filename = filename;
	this.date = date;
	this.salt = salt;
	this.url = url;
    }

    public Photo(String title, String description, String filename,
	    String date, Integer salt, String url, String coordinates,
	    String dimensions, String brand, String model, String flash,
	    String iSO, String focalLength, String fNum, String exposureTime) {
	super();
	this.title = title;
	this.description = description;
	this.filename = filename;
	this.date = date;
	this.salt = salt;
	this.url = url;
	this.coordinates = coordinates;
	this.dimensions = dimensions;
	this.brand = brand;
	this.model = model;
	this.flash = flash;
	this.ISO = iSO;
	this.focalLength = focalLength;
	this.fNum = fNum;
	this.exposureTime = exposureTime;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "PHOTO_ID", unique = true, nullable = false)
    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    @Column(name = "TITLE", nullable = false)
    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    @Column(name = "DESCRIPTION", nullable = false)
    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    @Column(name = "FILENAME", nullable = false)
    public String getFilename() {
	return filename;
    }

    public void setFilename(String filename) {
	this.filename = filename;
    }

    @Column(name = "DATE")
    public String getDate() {
	return date;
    }

    public void setDate(String date) {
	this.date = date;
    }

    @Column(name = "COORDINATES")
    public String getCoordinates() {
	return coordinates;
    }

    public void setCoordinates(String coordinates) {
	this.coordinates = coordinates;
    }

    @Column(name = "DIMENSIONS")
    public String getDimensions() {
	return dimensions;
    }

    public void setDimensions(String dimensions) {
	this.dimensions = dimensions;
    }

    @Column(name = "BRAND")
    public String getBrand() {
	return brand;
    }

    public void setBrand(String brand) {
	this.brand = brand;
    }

    @Column(name = "MODEL")
    public String getModel() {
	return model;
    }

    public void setModel(String model) {
	this.model = model;
    }

    @Column(name = "FLASH")
    public String getFlash() {
	return flash;
    }

    public void setFlash(String flash) {
	this.flash = flash;
    }

    @Column(name = "ISO")
    public String getISO() {
	return ISO;
    }

    public void setISO(String iSO) {
	ISO = iSO;
    }

    @Column(name = "FOCAL_LENGTH")
    public String getFocalLength() {
	return focalLength;
    }

    public void setFocalLength(String focalLength) {
	this.focalLength = focalLength;
    }

    @Column(name = "FNUM")
    public String getfNum() {
	return fNum;
    }

    public void setfNum(String fNum) {
	this.fNum = fNum;
    }

    @Column(name = "EXPOSURE_TIME")
    public String getExposureTime() {
	return exposureTime;
    }

    public void setExposureTime(String exposureTime) {
	this.exposureTime = exposureTime;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    @Column(name = "SALT")
    public Integer getSalt() {
	return salt;
    }

    public void setSalt(Integer salt) {
	this.salt = salt;
    }

    @Column(name = "URL")
    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }
}
