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
    public String nickname;
    public String firstname;
    public boolean isMan;
    public boolean wantsMan;
    public Date birthday;
    @ManyToOne
    public Location city;

    public String password;


    @OneToMany
    public List<Picture> pics;
    @OneToMany
    public List<Photo> photos;
    @ManyToMany
    public List<User> liked;
    @ManyToMany
    public List<Relation> relations;


    public boolean isAdmin;

    public User( String nickname, String firstname, boolean isMan, boolean wantsMan, Date birthday, Location city, String password) {
        this.nickname = nickname;
        this.firstname = firstname;
        this.isMan = isMan;
        this.wantsMan = wantsMan;
        this.birthday = birthday;
        this.city = city;
        this.password = password;
        this.isAdmin = false;

        pics = new ArrayList<Picture>();
        photos = new ArrayList<Photo>();
        liked = new ArrayList<User>();
        relations = new ArrayList<Relation>();
    }

    public static boolean connect(String username, String password) {
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
        checkPics();
    }


    public void addPhoto(Photo photo) {
        photos.add(photo);
        checkPics();
    }

    public void checkPics(){
        for (Picture pic : pics) {
            if(pic.image == null){
                pics.remove(pic);
            }
        }
    }


    @Override
    public String toString() {
        return this.id + ": "+ nickname + ": " + firstname + ", " + getAge();
    }


    public void deletePicture(Picture picture) {
        int index = pics.indexOf(picture);
        pics.remove(index);
    }


    public void deletePhoto(Photo photo) {
        int index = photos.indexOf(photo);
        photos.remove(index);
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

    public List<Relation> getRawRelations(){
        List<Relation> rawRelations = new ArrayList<Relation>();
        for (Relation relation : relations) {
            if(!relation.ready){
                rawRelations.add(relation);
            }
        }
        return rawRelations;
    }

    public List<Relation> getReadyRelations(){
        List<Relation> readyRelations = new ArrayList<Relation>();
        for (Relation relation : relations) {
            if(relation.ready){
                readyRelations.add(relation);
            }
        }
        return readyRelations;
    }


}
