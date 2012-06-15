import com.avaje.ebean.FetchConfig;
import models.Post;
import models.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


/**
 * Created with IntelliJ IDEA.
 * User: Josh
 * Date: 5/19/12
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasicTest {
    @Test
    public void createAndRetrieveUser() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                new User("bob@gmail.com", "secret", "Bob").save();
                User bob = User.find.findUnique();
                assertThat(bob).isNotNull();
                assertThat("Bob").isEqualTo(bob.fullName);
                assertThat("secret").isEqualTo(bob.password);
                assertThat("bob@gmail.com").isEqualTo(bob.email);
            }
        });
    }

    @Test
    public void createPost() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            @Override
            public void run() {
                User bob = new User("bob@gmail.com", "secret", "Bob");
                bob.save();
                new Post(bob, "My first post", "Hello world").save();

                assertThat(Post.find.all().size()).isEqualTo(1);

                List<Post> posts = Post.find.where().eq("author", bob).findList();

                assertThat(posts).hasSize(1);
                Post firstPost = posts.get(0);

                assertThat(firstPost).isNotNull();
                assertThat(bob).isEqualTo(firstPost.author);
                assertThat("My first post").isEqualTo(firstPost.title);
                assertThat(firstPost.postedAt).isNotNull();

                assertThat("Hello world").isEqualTo(firstPost.content);

            }
        });
    }
}
