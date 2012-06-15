package models;

import play.db.ebean.Model;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Josh
 * Date: 6/14/12
 * Time: 11:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class Post extends Model {
    public String title;
    public Date postedAt;

    @Lob
    public String content;

    @ManyToOne
    public User author;


    public Post(User author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.postedAt = new Date();
    }
}
