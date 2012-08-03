package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Josh
 * Date: 6/14/12
 * Time: 11:07 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "blog_post")
public class Post extends Model {
    @Id
    public Long id;

    public String title;
    public Date postedAt;


    @Lob
    private String content;

    @ManyToOne
    public User author;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "post")
    public List<Comment> comments;


    public Post(User author, String title, String content) {
        this.comments = new ArrayList<Comment>();
        this.author = author;
        this.title = title;
        this.content = content;
        this.postedAt = new Date();
    }

    public Post addComment(String author, String content) {
        Comment newComment = new Comment(this, author, content);
        newComment.save();
        this.comments.add(newComment);
        this.save();
        return this;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public static Finder<Long,Post> find = new Finder<Long, Post>(Long.class,Post.class);
}
