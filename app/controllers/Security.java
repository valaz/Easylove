package controllers;

import models.User;

/**
 * Created by Azat on 11.09.2015.
 */
public class Security extends Secure.Security {
    static boolean authenticate(String username, String password) {
        return User.connect(username, password);
    }

    static void onAuthenticated() {
        Admin.index();
    }

    static void onDisconnected() {
        Application.index();
    }

    static boolean check(String profile) {
        if("admin".equals(profile)) {
            return User.find("nickname", connected()).<User>first().isAdmin;
        }
        return false;
    }
}
