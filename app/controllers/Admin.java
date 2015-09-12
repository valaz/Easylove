package controllers;

import models.Picture;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;


@With(Secure.class)
public class Admin extends Controller {
    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
//            System.out.println("current User: " + Security.connected());
            User user = User.find("nickname", Security.connected()).first();

            renderArgs.put("user", user.nickname);
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
        render();
    }

    public static void uploadPicture(Picture picture) {
        picture.save();
        if(Security.isConnected()) {
//            System.out.println("current User: " + Security.connected());
            User user = User.find("nickname", Security.connected()).first();
            long picID =  picture.id;
            for(int i =0; i < user.pictures.length; i++){
                if(user.pictures[i] == 0){
                    user.pictures[i] = picID;
                    break;
                }
            }
            user.save();
            profile();
        }
    }

    public static void getPicture(long id) {
        Picture picture = Picture.findById(id);
        response.setContentTypeIfNotSet(picture.image.type());
        renderBinary(picture.image.get());
    }
}
