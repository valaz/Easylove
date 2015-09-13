package controllers;

import models.User;
import play.mvc.Controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Application extends Controller {

    public static void index() {
        String username = Security.connected();
        User user =  User.find("nickname", username).first();
        if( user != null){
            System.out.println("NOW CONNECTED: "+Security.connected());
            Admin.index();
        }else{
            session.clear();
            render();
        }
    }

    public static void signup() {
        render();
    }
    public static void signin() {
        render();
    }

    public static void addUser(String email, String nname, String fname, String sname, String gender,String secondGender, String bdate, String city, String password1, String password2 ){
        System.out.println("User there");

        boolean isMan = Boolean.valueOf(gender);
        boolean wantsMan = Boolean.valueOf(secondGender);

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date birthday = new Date();
        try {
            birthday = format.parse(bdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        User newUser = new User(email,nname,fname,sname,isMan,wantsMan,birthday,city,password1);
        newUser.save();
        try {
            Secure.authenticate(nname,password1,true);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        Admin.index();
    }

}