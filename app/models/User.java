package models;


import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


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


    public boolean isAdmin;

    public User(String email,String nickname, String phone,String firstname, boolean isMan, boolean wantsMan, Date birthday, String city, String password) {
        this.email = email;
        this.nickname = nickname;
        this.phone = phone;
        this.firstname = firstname;
        this.isMan  = isMan;
        this.wantsMan  = wantsMan;
        this.birthday = birthday;
        this.city = city;
        this.password = password;
        this.isAdmin = true;
//        pictures = new long[5];
//        timeranges = new long[10];

        pics = new ArrayList<Picture>();
        ranges = new ArrayList<Timerange>();
    }

    public static boolean connect(String username, String password){
        System.out.println("CHECK CONNECT: " + username + " " + password);
        User user =  User.find("nickname", username).first();
        return user != null && user.password.equals(password);

    }
    public String getDate(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String reportDate = df.format(birthday);
        return reportDate;
    }


    public void addPicture(Picture picture){
        pics.add(picture);
    }

    public void addRange(Timerange timerange){
        ranges.add(timerange);
        Collections.sort(ranges);
    }
//    public long[] validPictures(){
//        int len = 0;
//        for(int j = 0; j < pictures.length; j++){
//            if(pictures[j] != 0){
//                len++;
//            }
//        }
//        long res[] = new long[len];
//        int i = 0;
//        for(int j = 0; j < pictures.length; j++){
//            if(pictures[j] != 0){
//                res[i] = pictures[j];
//                i++;
//            }
//        }
//        return res;
//    }
//    public void addTimeRange(long timerangeID){
//        deleteBadTimeranges();
//        Arrays.sort(timeranges);
//        if(timeranges[0] == 0){
//            timeranges[0] = timerangeID;
//            Arrays.sort(timeranges);
//        }else{
//            int curLen = timeranges.length;
//            long[] newTimeranges = new long[curLen*2];
//            System.arraycopy(timeranges,0,newTimeranges,0,curLen);
//            timeranges = newTimeranges;
//            addTimeRange(timerangeID);
//        }
//    }
    public void deleteBadTimeranges(){
//        Arrays.sort(timeranges);
//        int i = timeranges.length - 1;
//        while(i >= 0 || timeranges[i] != 0){
//            Date curDay = ((Timerange)Timerange.findById(timeranges[i])).day;
//            if(curDay.compareTo(new Date()) < 0){
//                timeranges[i] = 0;
//            }
//            i--;
//        }
    }
    @Override
    public String toString() {
        return  nickname;
    }
}
