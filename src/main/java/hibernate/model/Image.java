package hibernate.model;

import static javax.persistence.GenerationType.IDENTITY;
import hibernate.model.User;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "image", catalog = "kaos")
public class Image implements Serializable {

    private Integer id;
    private Integer width;
    private Integer height;
    private String title;
    private String description;
    private Integer votes; // [0, 5]
    private User user;

    public Image() {
    }

    public Image(Integer width, Integer height, String title,
            String description, Integer votes, User user) {
        super();
        this.width = width;
        this.height = height;
        this.title = title;
        this.description = description;
        this.votes = votes;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "IMAGE_ID", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "WIDTH", nullable = true)
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Column(name = "HEIGHT", nullable = true)
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Column(name = "TITLE", unique = true, nullable = false)
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

    @Column(name = "VOTES", nullable = false)
    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;

    }

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
