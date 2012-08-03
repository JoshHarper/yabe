package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Josh
 * Date: 5/19/12
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "blog_user")
public class User extends Model {
    @Id
    public Long id;

    public String email;
    public String password;
    public String fullName;
    public boolean isAdmin;

    public User(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static Finder<Long,User> find = new Finder<Long, User>(Long.class,User.class);


}
