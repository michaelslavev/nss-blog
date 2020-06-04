
package the.nss.boys.blog.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDate;
import java.util.*;
import javax.persistence.*;

/**
 * Model entity of Article
 *
 * Contains title, content, created, topics, comments, likes, author and removed variables
 */
@Entity
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Article extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false,name="title")
    private String title;

    @Basic(optional = false)
    @Column(nullable = false,name="content")
    private String content;

    //@Basic(optional = false)
    @Column(name="created")
    @JsonFormat(pattern="dd-MM-yyyy",timezone="Europe/Prague")
    private LocalDate created;

    @ManyToMany
    @OrderBy("name")
    private List<Topic> topics;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(mappedBy = "article")
    @JsonIgnore
    private List<Like> likes;
    
    @ManyToOne
    private User author;
    
    private Boolean removed = false;
  
    public Article() {
    }

    /**
     * Constructor of object article
     * @param title
     * @param content
     * @param created
     * @param author
     */
    public Article(String title, String content, LocalDate created, User author) {
        this.title = title;
        this.content = content;
        this.created = created;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return created;
    }

    public void setDate(LocalDate date) {
        this.created = date;
    }

    //====TOPIC====
    public List<Topic> getTopics() {
        return topics;
    }
    
    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    /**
     * Add topic to article
     * @param topic
     */
    public void addTopic(Topic topic) {
        Objects.requireNonNull(topic);
        if (topics == null) {
            this.topics = new ArrayList<>();
        }
        topics.add(topic);
    }

    /**
     * Removes topic from article
     * @param topic
     */
    public void removeTopic(Topic topic) {
        Objects.requireNonNull(topic);
        if (topics == null) {
            return;
        }
        topics.removeIf(c -> Objects.equals(c.getId(), topic.getId()));
    }

    public Boolean isRemoved() {
        return removed;
    }

    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }
    
   //====COMMENT=====
    @JsonIgnore
    public List<Comment> getComments() {
        return comments;
    }
    
    @JsonIgnore
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Adds comment to article
     * @param comment
     */
    public void addComment(Comment comment) {
        Objects.requireNonNull(comment);
        if (comments == null) {
            this.comments = new ArrayList<>();
        }
        comments.add(comment);
        comment.setArticle(this);
    }

    /**
     * Removes comment from article
     * @param comment
     */
    public void removeComment(Comment comment) {
        Objects.requireNonNull(comment);
        if (comments == null) {
            return;
        }
        comments.removeIf(c -> Objects.equals(c.getId(), comment.getId()));
        comment.setArticle(null);
    } 
    
    //===AUTHOR==
    public User getUser() {
        return author;
    }
    public void setUser(User author) {
        this.author = author;
    }
    //===LIKES===
    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    /**
     * Adds one like to article
     * @param like
     */
     public void addLike(Like like) {
          Objects.requireNonNull(like);
        if (likes == null) {
            this.likes = new ArrayList<>();
        }
        likes.add(like);
    }

    /**
     * Removes one like from article
     * @param like
     */
    public void removeLike(Like like) {
        Objects.requireNonNull(like);
        if (likes == null) {
            return;
        }
        likes.removeIf(c -> Objects.equals(c.getId(), like.getId()));
    } 
    
    
    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", created=" + created +
                ", author=" + author +
                ", topics=" + topics +
                ", content=" + content +
              //  ", comments=" + comments +
              //  ", likes=" + likes +
                "}";
    }

    
}
