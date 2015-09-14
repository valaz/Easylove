package controllers;

import models.Picture;
import models.Timerange;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;


@With(Secure.class)
public class Admin extends Controller {
    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            System.out.println("current User: " + Security.connected());
            User user = User.find("nickname", Security.connected()).first();
            if(user != null) {
                renderArgs.put("user", user.nickname);
            }
        }
    }

    public static void index() {
        render();
    }

    public static void profile() {
        if(Security.isConnected()) {
            User user = User.find("nickname", Security.connected()).first();
            render(user);
        }
    }

    public static void schedule() {
        if(Security.isConnected()) {
            User user = User.find("nickname", Security.connected()).first();
            render(user);
        }
        render();
    }

    public static void uploadPicture(Picture picture) {
        if(picture.image != null) {
            picture.save();
            if (Security.isConnected()) {
//            System.out.println("current User: " + Security.connected());
                User user = User.find("nickname", Security.connected()).first();
                long picID = picture.id;
//                for (int i = 0; i < user.pictures.length; i++) {
//                    if (user.pictures[i] == 0) {
//                        user.pictures[i] = picID;
//                        break;
//                    }
//                }
                user.addPicture(picture);
                user.save();
                profile();
            }
        }else{
            profile();
        }
    }

    public static void addTimeRange(String day){
        System.out.println("111111111111111111111111111111111111111111111111111111111111111111111111111111");
        System.out.println(day);
        Timerange timerange = new Timerange(day);
        System.out.println("222222222222222222222222222222222222222222222222222222222222222222222222222222");
        System.out.println("TIMERANGE: " + timerange.toString());

        timerange.save();
        User user = User.find("nickname", Security.connected()).first();
        user.addRange(timerange);
        user.save();
        schedule();

    }
    public static void deleteTimeRange(long dayID){
        Timerange timerange = Timerange.findById(dayID);
        if (Security.isConnected()) {
            User user = User.find("nickname", Security.connected()).first();
            user.deleteRange(timerange);
            user.save();
            timerange.delete();
        }
        schedule();
    }
    public static void deletePicture(long picID){
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
}
