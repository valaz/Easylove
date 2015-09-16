package models;


import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Entity
public class User extends Model {
    public String email;
    public String nickname;
    public String phone;
    public String firstname;
    public boolean isMan;
    public boolean wantsMan;
    public Date birthday;
    public String city;
    public String password;
    public String selfinfo;
//    public long[]  pictures;
//    public long[]  timeranges;


    @OneToMany
    public List<Picture> pics;
    @OneToMany
    public List<Timerange> ranges;
    @ManyToMany
    public List<User> liked;
    @ManyToMany
    public List<Relation> relations;


    public boolean isAdmin;

    public User(String email, String nickname, String phone, String firstname, boolean isMan, boolean wantsMan, Date birthday, String city, String password) {
        this.email = email;
        this.nickname = nickname;
        this.phone = phone;
        this.firstname = firstname;
        this.isMan = isMan;
        this.wantsMan = wantsMan;
        this.birthday = birthday;
        this.city = city;
        this.password = password;
        this.isAdmin = true;

        pics = new ArrayList<Picture>();
        ranges = new ArrayList<Timerange>();
        liked = new ArrayList<User>();
        relations = new ArrayList<Relation>();
    }

    public static boolean connect(String username, String password) {
        System.out.println("CHECK CONNECT: " + username + " " + password);
        User user = User.find("nickname", username).first();
        return user != null && user.password.equals(password);

    }

    public String getDate() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String reportDate = df.format(birthday);
        return reportDate;
    }


    public void addPicture(Picture picture) {
        pics.add(picture);
    }

    public void addRange(Timerange timerange) {
        ranges.add(timerange);
        ranges = saveUnique(ranges);
        Collections.sort(ranges);

    }

    public static List<Timerange> saveUnique(List<Timerange> ranges) {
        Set<Timerange> hs = new HashSet<Timerange>();
        hs.addAll(ranges);
        System.out.println(hs);
        List<Timerange> newRanges = new ArrayList<Timerange>();
        newRanges.addAll(hs);
        Collections.sort(newRanges);
        return newRanges;
    }

    @Override
    public String toString() {
        return nickname + ": " + firstname + ", " + getAge();
    }

    public void deleteRange(Timerange timerange) {
        int index = ranges.indexOf(timerange);
        ranges.remove(index);
        Collections.sort(ranges);

    }

    public void deletePicture(Picture picture) {
        int index = pics.indexOf(picture);
        pics.remove(index);
    }

    public int getAge() {
        Calendar dob = Calendar.getInstance();
        dob.setTime(birthday);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR))
            age--;
        return age;
    }

    public boolean doLike(User user) {
        if (!liked.contains(user)) {
            liked.add(user);
            return true;
        }
        return false;
    }

    public static Relation CheckRelation(User user1, User user2) {
        if (user1.liked.contains(user2) && user2.liked.contains(user1)) {
            Relation relation = new Relation(user1, user2);
            if( !user1.relations.contains(relation) ) {
                user1.relations.add(relation);
                user2.relations.add(relation);
                return relation;
            }
        }
        return null;
    }
}
