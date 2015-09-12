package controllers;

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
}
