import models.User;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Yaml;
import play.mvc.Result;

import static play.mvc.Results.badRequest;

import com.avaje.ebean.*;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Josh
 * Date: 8/2/12
 * Time: 10:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class Global extends GlobalSettings {

    @Override
    public void onStart(Application application) {
        Logger.info("Inserting initial data");
        InitialData.insert(application);
    }


    @Override
    public void onStop(Application application) {
    }

    @Override
    public Result onBadRequest(String s, String s1) {
        Logger.error("BAD REQUEST WTF!?");
        return badRequest("WTF?");
    }

    static class InitialData {
        public static void insert(Application app) {
            if (Ebean.find(User.class).findRowCount() == 0) {

                Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml.load("initial-data.yml");

                // Insert users first
                Ebean.save(all.get("Users"));

                // Insert projects
                Ebean.save(all.get("Post"));
                for (Object project : all.get("post")) {
                    // Insert the project/user relation
                    Ebean.saveManyToManyAssociations(project, "Comment");
                }
            }
        }
    }
}
