package controllers;

import models.User;
import play.mvc.Controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Application extends Controller {

    public static void index() {
        if(Security.isConnected()){
            Admin.index();
        }
        render();
    }

    public static void signup() {
        render();
    }
    public static void signin() {
        render();
    }

    public static void addUser(String email, String nname, String fname, String sname, String bdate, String city, String password1, String password2 ){
        System.out.println("User there");

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date birthday = new Date();
        try {
            birthday = format.parse(bdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        User newUser = new User(email,nname,fname,sname,birthday,city,password1);
        newUser.save();
        index();
    }

}