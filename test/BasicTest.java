import models.Comment;
import models.Post;
import models.User;
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

                assertThat("Hello world").isEqualTo(firstPost.getContent());

            }
        });
    }

    @Test
    public void postComments() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            @Override
            public void run() {
                User bob = new User("bob@gmail.com", "secret", "Bob");
                bob.save();
                Post bobPost = new Post(bob, "My first post", "Hello world");
                bobPost.save();

                new Comment(bobPost, "Jeff", "Nice post").save();
                new Comment(bobPost, "Tom", "I knew that !").save();

                List<Comment> bobPostComments = Comment.find.where().eq("post", bobPost).findList();

                assertThat(bobPostComments).hasSize(2);

                Comment firstComment = bobPostComments.get(0);
                assertThat(firstComment).isNotNull();
                assertThat("Jeff").isEqualTo(firstComment.author);
                assertThat("Nice post").isEqualTo(firstComment.getContent());
                assertThat(firstComment.postedAt).isNotNull();

                Comment secondComment = bobPostComments.get(1);
                assertThat(secondComment).isNotNull();
                assertThat("Tom").isEqualTo(secondComment.author);
                assertThat("I knew that !").isEqualTo(secondComment.getContent());
                assertThat(secondComment.postedAt).isNotNull();
            }
        });
    }

    @Test
    public void useTheCommentsRelation() {
        running(fakeApplication(inMemoryDatabase()),new Runnable() {
            @Override
            public void run() {
                User bob = new User("bob@gmail.com","secret","Bob");
                bob.save();

                Post bobPost = new Post(bob, "My first post","Hello world");
                bobPost.addComment("Jeff", "Nice post");
                bobPost.addComment("Tom", "I knew that!");


                assertThat(User.find.all().size()).isEqualTo(1);
                assertThat(Post.find.all().size()).isEqualTo(1);
                assertThat(Comment.find.all().size()).isEqualTo(2);


                Post getBobPost = Post.find.where().eq("author",bob).findUnique();
                assertThat(getBobPost).isNotNull();
                System.out.println(getBobPost.getComments().size());
                for (Comment comment : getBobPost.getComments()) {
                    System.out.println("Comment message: " + comment.getContent());
                }
//                assertThat(getBobPost.comments.get(0).author).isEqualTo("Jeff");


//                assertThat(bobPost.comments.size()).isEqualTo(2);

                /*bobPost.delete();
                System.out.println("Users: " + User.find.all().size());
                System.out.println("Posts: " + Post.find.all().size());
                System.out.println("Comments: " + Comment.find.all().size());
                assertThat(User.find.all().size()).isEqualTo(1);
                assertThat(Post.find.all().size()).isEqualTo(0);
                assertThat(Comment.find.all().size()).isEqualTo(0);*/
            }
        } );
    }
}
