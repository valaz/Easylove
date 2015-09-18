package controllers;

import models.Picture;
import models.Relation;
import models.Timerange;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@With(Secure.class)
public class Admin extends Controller {
    @Before
    static void setConnectedUser() {
        if (Security.isConnected()) {
//            System.out.println("current User: " + Security.connected());
            User user = User.find("nickname", Security.connected()).first();
            if (user != null) {
                renderArgs.put("user", user.nickname);
            }else{
                session.clear();
                Application.index();
            }
        }
    }

    public static void index() {
        User partnerUser = getPartner();
        render(partnerUser);
    }

    private static User getPartner() {
        if (Security.isConnected()) {
            User curUser = User.find("nickname", Security.connected()).first();

            List<User> users = User.findAll();
            List<User> goodUsers = new ArrayList<User>();
            for (User user : users) {
                if (user.isMan == curUser.wantsMan && user.wantsMan == curUser.isMan && curUser.id != user.id){
                    goodUsers.add(user);
                }
            }
            if(goodUsers.size()>0) {
                Random randomGenerator = new Random();
                int index = randomGenerator.nextInt(goodUsers.size());
                User randomGoodUser = goodUsers.get(index);
                return randomGoodUser;
            }
        }
        return null;
    }

    public static void profile() {
        if (Security.isConnected()) {
            User user = User.find("nickname", Security.connected()).first();
            render(user);
        }
    }

    public static void schedule() {
        if (Security.isConnected()) {
            User user = User.find("nickname", Security.connected()).first();
            render(user);
        }
        render();
    }

    public static void uploadPicture(Picture picture) {
        if (picture.image != null) {
            if (Security.isConnected()) {
                User user = User.find("nickname", Security.connected()).first();
                picture.save();
                user.addPicture(picture);
                user.save();
                profile();
            }
        } else {
            profile();
        }
    }

    public static void addTimeRange(String day) {
        Timerange timerange = new Timerange(day);
        timerange.save();
        User user = User.find("nickname", Security.connected()).first();
        user.addRange(timerange);
        user.save();
        schedule();

    }

    public static void deleteTimeRange(long dayID) {
        Timerange timerange = Timerange.findById(dayID);
        if (Security.isConnected()) {
            User user = User.find("nickname", Security.connected()).first();
            user.deleteRange(timerange);
            user.save();
            timerange.delete();
        }
        schedule();
    }

    public static void deletePicture(long picID) {
        Picture picture = Picture.findById(picID);
        if (Security.isConnected()) {
            User user = User.find("nickname", Security.connected()).first();
            user.deletePicture(picture);
            user.save();
//            picture._delete();
        }
        profile();
    }

    public static void getPicture(long id) {
        Picture picture = Picture.findById(id);
        response.setContentTypeIfNotSet(picture.image.type());
        renderBinary(picture.image.get());
    }

    public static void likeUser(Long userID){
        if (Security.isConnected()) {
            User user = User.find("nickname", Security.connected()).first();
            User likedUser = User.findById(userID);
            if( user.doLike(likedUser)) {
                user.save();
            }
            Relation relation = User.CheckRelation(likedUser, user);
            if(relation != null){
                relation.save();
                likedUser.save();
                user.save();
            }
            index();
        }
    }

    public static void changeRelation(long relationID, String place, String time, String date){
        if (Security.isConnected()) {
            User user = User.find("nickname", Security.connected()).first();
//            System.out.println(relationID +" | " + place + " | " + time + " | " + date);
            Relation relation = Relation.findById(relationID);
            relation.change(user.id, place,time,date);

            relation.save();
            schedule();
        }
    }
}
