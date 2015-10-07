package controllers;

import models.Location;
import models.User;
import models.Warning;
import play.cache.Cache;
import play.libs.Codec;
import play.libs.Images;
import play.mvc.Controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Application extends Controller {

    public static void index() {
        List<Warning> warnings = Warning.findAll();
        String username = Security.connected();
        User user =  User.find("nickname", username).first();
        if( user != null){
//            System.out.println("NOW CONNECTED: "+Security.connected());
            Admin.index();
        }else{
            session.clear();
            render(warnings);
        }
    }

    public static void signup() {
        List<Warning> warnings = Warning.findAll();
        String randomID = Codec.UUID();
        List<String> cities = getCities();
        render(randomID, cities,warnings);
    }
    public static void signin() {
        render();
    }
    public static void about() {
        List<Warning> warnings = Warning.findAll();
        String username = Security.connected();
        User user =  User.find("nickname", username).first();
        if( user != null){
//            System.out.println("NOW CONNECTED: "+Security.connected());
            render(user,warnings);
        }else{
            session.clear();
            render(warnings);
        }
    }

    public static void addUser(String nname, String fname,String gender,String secondGender, String bdate, String city, String password1, String password2 ){
        System.out.println("User there");
        System.out.println("nname: " + nname);
        System.out.println("fname: " + fname);
        System.out.println("gender: " + gender);
        System.out.println("secondGender: " + secondGender);
        System.out.println("bdate: " + bdate);
        System.out.println("city: " + city);
        System.out.println("password1: " + password1);
        boolean isMan = Boolean.valueOf(gender);
        boolean wantsMan = Boolean.valueOf(secondGender);

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date birthday = new Date();
        try {
            birthday = format.parse(bdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Location location = Location.find("name", city).first();;
        System.out.println("Location in:" + location.name);
        User newUser = new User(nname,fname,isMan,wantsMan,birthday,location,password1);
        newUser.save();
        try {
            Secure.authenticate(nname,password1,true);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        Admin.index();
    }

    public static List<String>  getCities(){
        List<Location> locations = Location.findAll();
        List<String> cities = new ArrayList<String>();
        for (Location location : locations) {
            cities.add(location.name);
        }
        Collections.sort(cities);
        return cities;
    }

    public static void captcha(String id) {
        Images.Captcha captcha = Images.captcha();
        String color = "black";
        String code = captcha.getText("#FFF");
        Cache.set(id, code, "10mn");
//        System.out.println("id: " + id + " code: " + code);
        renderBinary(captcha);
    }

    public static void checkCaptcha(String randomID, String code) {
        System.out.println("Start AJAX");
        System.out.println("code: " + code);
        System.out.println("randomID id: " + randomID);
        System.out.println("randomID: " + Cache.get(randomID));
        Boolean correct = code.equals(Cache.get(randomID));
        System.out.println(correct + "\n");
        renderJSON(correct);
    }

    public static void newCaptcha() {
        System.out.println("generating new captcha");
        String newRandomID = Codec.UUID();
        System.out.println(newRandomID);
        renderText(newRandomID);
    }

    public static boolean checkLogin(String login) {
        System.out.println("check LOGIN: " + login);
        User user =  User.find("nickname", login).first();
        return user != null;
    }
}