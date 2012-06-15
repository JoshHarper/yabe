package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Josh
 * Date: 6/14/12
 * Time: 11:48 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "blog_comments")
public class Comment extends Model {
    @Id
    public Long id;

    public String author;
    public Date postedAt;

    public String content;

    @ManyToOne
    public Post post;


    public Comment(Post post, String author, String content) {
        this.post = post;
        this.author = author;
        this.content = content;
        this.postedAt = new Date();
    }

    public static Finder<Long,Comment> find = new Finder<Long, Comment>(Long.class,Comment.class);
}
