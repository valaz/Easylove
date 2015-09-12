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
    public boolean isMan;
    public Date birthday;
    public String city;
    public String password;
    public long[]  pictures;


    public boolean isAdmin;

    public User(String email,String nickname, String firstname, String secondname,boolean isMan, Date birthday, String city, String password) {
        this.email = email;
        this.nickname = nickname;
        this.firstname = firstname;
        this.secondname = secondname;
        this.isMan  = isMan;
        this.birthday = birthday;
        this.city = city;
        this.password = password;
        this.isAdmin = true;
        pictures = new long[5];
    }

    public static boolean connect(String username, String password){
        User user =  User.find("nickname", username).first();
        return user != null;

    }

    public long[] validPictures(){
        int len = 0;
        int i = 0;
        while(i < pictures.length && pictures[i] != 0 ){
            len++;
            i++;
        }
        long res[] = new long[len];
        for(int j = 0; j < len; j++){
            res[j] = pictures[j];
        }
        return res;
    }
    @Override
    public String toString() {
        return  firstname + " " + secondname;
    }
}
