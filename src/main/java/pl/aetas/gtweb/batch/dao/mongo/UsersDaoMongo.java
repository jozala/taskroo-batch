package pl.aetas.gtweb.batch.dao.mongo;

import com.google.inject.name.Named;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import pl.aetas.gtweb.batch.dao.UsersDao;
import pl.aetas.gtweb.batch.domain.User;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

public class UsersDaoMongo implements UsersDao {

    private DBCollection usersCollection;

    @Inject
    public UsersDaoMongo(@Named("UsersDBCollection") DBCollection usersCollection) {
        this.usersCollection = usersCollection;
    }

    @Override
    public List<User> getEnabledUsersList() {
        DBCursor dbUsers = usersCollection.find(new BasicDBObject("enabled", true));
        List<User> users = new LinkedList<>();
        for (DBObject dbUser : dbUsers) {
            users.add(new User(dbUser.get("_id"), dbUser.get("email").toString()));
        }
        return users;
    }
}
