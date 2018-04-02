package models;

import io.ebean.*;
import org.mindrot.jbcrypt.BCrypt;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class User extends Model {
    @Id
    private long id;
    private String firstName;
    private String lastName;
    @Constraints.Required
    private String email;
    @Constraints.Required
    private String passwordHash;
    private int age;
    @Formats.DateTime(pattern="dd/MM/yyyy")
    private Date joinDate;
    private int type;
    //private String campus;
    //private String standing;
    //private String department;
    //private String scores;

    //Static finder that will return a user based on the extend methods of Model
    public static final Finder<Long, User> find = new Finder<>(User.class);

    public static User authenticate(String email, String pass){
        User user = findByEmail(email);
        if(user != null && BCrypt.checkpw(pass, user.getPasswordHash())){
            return user;
        }else{
            return null;
        }
    }

    public static User findByEmail(String email){
        return find.query().where().eq("email", email).findUnique();
    }

    public User(String fName, String lName, String email, String pass, int t, int age){
        this.firstName = fName;
        this.lastName = lName;
        this.email = email;
        this.passwordHash = BCrypt.hashpw(pass, BCrypt.gensalt());
        this.age = age;
        this.joinDate = new Date();
        this.type = t;
        //TODO: Implement these other features of user
        //this.campus = campus;
        //this.standing = standing;
        //this.department = department;
        //this.scores = scores;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
/*
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
*/
}
