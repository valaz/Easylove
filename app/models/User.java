package models;


import play.db.jpa.Model;

import javax.persistence.Entity;
import java.util.Date;


@Entity
public class User extends Model {
    public String email;
    public String nickname;
    public String firstname;
    public String secondname;
    public Date birthday;
    public String city;
    public String password;


    public boolean isAdmin;

    public User(String email,String nickname, String firstname, String secondname, Date birthday, String city, String password) {
        this.email = email;
        this.nickname = nickname;
        this.firstname = firstname;
        this.secondname = secondname;
        this.birthday = birthday;
        this.city = city;
        this.password = password;
        this.isAdmin = true;
    }

    public static boolean connect(String username, String password){
        User user =  User.find("nickname", username).first();
        return user != null;

    }
    @Override
    public String toString() {
        return  firstname + " " + secondname;
    }
}
