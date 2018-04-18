package models;

import io.ebean.*;
import io.ebean.annotation.DbJson;
import io.ebean.annotation.DbJsonType;
import org.mindrot.jbcrypt.BCrypt;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.mvc.Http;

import javax.persistence.*;
import java.util.*;


/**
 * User model class that extends the Ebean Model class for interaction directly with the database
 * Created by Lowell Buttorff - Updated 20180406
 */
@Entity
public class User extends Model {
    @Id
    private long id;
    private String firstName;
    private String lastName;
    @Constraints.Required
    @Column(unique = true)
    private String email;
    private String authToken;
    @Constraints.Required
    private String passwordHash;
    private int age;
    @Formats.DateTime(pattern="dd/MM/yyyy")
    private Date joinDate;
    private int type;
    private int campus;
    private int standing;
    private int department;
    @DbJson(storage= DbJsonType.VARCHAR)
    private List<Integer> services;
    //private String scores; //Still not sure how we want to implement this 20180406

    //Enumeration of campuses for easier access later
    private final ArrayList<String> campuses = new ArrayList<>(Arrays.asList(
            "Abington","Altoona","Beaver","Behrend","Berks","Brandywine","DuBois","Fayette","Greater Allegheny",
            "Harrisburg","Hazleton","Lehigh Valley","Mont Alto","New Kensington","Schuylkill","Shenango",
            "University Park","Wilkes-Barre","Worthington Scranton","York"
    ));

    //Enumeration of academic status
    private final ArrayList<String> statuses = new ArrayList<>(Arrays.asList(
            "Undergraduate Student","Masters Student","Ph.D. Candidate","Faculty Member","Alumni"
    ));

    //Enumeration of departments
    private final ArrayList<String> departments = new ArrayList<>(Arrays.asList(
            "Agricultural Sciences","Arts and Architecture","Smeal College of Business","College of Communications",
            "Earth and Mineral Sciences","Education","Engineering","Health and Human Development",
            "Information Sciences and Technology","Dickinson Law","Penn State Law","The Liberal Arts",
            "College of Medicine","College of Nursing","Eberly College of Science"
    ));


    /**
     * Finder method that takes advantage of the Ebean Model class features in order to access data in the database
     */
    public static final Finder<Long, User> find = new Finder<>(User.class);

    /**
     * Checks if a User exists and if the password given matches the one in the database
     * @param email The String email address of a given User
     * @param pass The String password of a given User
     * @return null if the User does not exist or has the wrong password. A User object if authenticated
     */
    public static User authenticate(String email, String pass){
        User user = findByEmail(email);
        if(user != null && BCrypt.checkpw(pass, user.getPasswordHash())){
            return user;
        }else{
            return null;
        }
    }

    /**
     * Attempts to find a User in the database using the Finder method and by filtering the results by email
     * @param email The String email address of a given User
     * @return A User object from the database or null if no user exists
     */
    public static User findByEmail(String email){
        return find.query().where().eq("email", email).findUnique();
    }

    public static User getCurrentUser(){
        String token = Http.Context.current().session().get("token");
        List<User> possibles = find.query().where().eq("authToken", token).findList();
        if(possibles.size() != 1){
            return null;
        }
        return possibles.get(0);
    }

    /**
     * Constructor used for building a default User
     * @param fName First name as a String
     * @param lName Last name as a String
     * @param email Email address as a String
     * @param pass Password as a String
     * @param t Type as an int
     * @param age Age as an int
     */
    public User(String fName, String lName, String email, String pass, int t, int age){
        this.firstName = fName;
        this.lastName = lName;
        this.email = email;
        this.passwordHash = BCrypt.hashpw(pass, BCrypt.gensalt());
        this.age = age;
        this.joinDate = new Date();
        this.type = t;
    }

    /**
     * Constructor used for building a User with Mentor features
     * @param fName First name as a String
     * @param lName Last name as a String
     * @param email Email address as a String
     * @param pass Password as a String
     * @param t Type as an int
     * @param age Age as an int
     * @param c Campus as an int
     * @param s Standing as an int
     * @param d Department as an int
     * @param services Services provided as a List of Integers
     */
    public User(String fName, String lName, String email, String pass, int t, int age, int c, int s, int d, List<Integer> services){
        this.firstName = fName;
        this.lastName = lName;
        this.email = email;
        this.passwordHash = BCrypt.hashpw(pass, BCrypt.gensalt());
        this.age = age;
        this.joinDate = new Date();
        this.type = t;
        this.campus = c;
        this.standing = s;
        this.department = d;
        this.services = services;
    }

    //Getters for profile
    public String getCampusAsString(){
        return campuses.get(this.campus);
    }

    public String getStandingAsString(){
        return statuses.get(this.standing);
    }

    public String getDepartmentAsString(){
        return departments.get(this.department);
    }

    //Standard Getters and Setters
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

    public int getCampus() {
        return campus;
    }

    public void setCampus(int campus) {
        this.campus = campus;
    }

    public int getStanding() {
        return standing;
    }

    public void setStanding(int standing) {
        this.standing = standing;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public List<Integer> getServices() {
        return services;
    }

    public void setServices(List<Integer> services) {
        this.services = services;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String createToken() {
        this.authToken = UUID.randomUUID().toString();
        this.save();
        return this.authToken;
    }
}
