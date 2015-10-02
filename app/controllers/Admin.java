package controllers;

import models.Picture;
import models.Relation;
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
            } else {
                session.clear();
                Application.index();
            }
        }
    }

    public static void index() {
        if (Security.isConnected()) {
            User user = User.find("nickname", Security.connected()).first();
            User partnerUser = getPartner();
            boolean partner = false;
            if (partnerUser != null) {
                partner = true;
            }
            render(partner, partnerUser, user);
        }
    }

    private static User getPartner() {
        if (Security.isConnected()) {
            User curUser = User.find("nickname", Security.connected()).first();

            List<User> users = User.findAll();
            List<User> goodUsers = new ArrayList<User>();
            for (User user : users) {
                if (user.isMan == curUser.wantsMan && user.wantsMan == curUser.isMan && curUser.id != user.id && user.city == curUser.city && !user.pics.isEmpty() && !curUser.liked.contains(user)) {
                    goodUsers.add(user);
                }
            }
            if (goodUsers.size() > 0) {
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

    public static void meetings() {
        if (Security.isConnected()) {
            User user = User.find("nickname", Security.connected()).first();
            render(user);
        }
        render();
    }

    @Check("admin")
    public static void statistics() {
        if (Security.isConnected()) {
            User user = User.find("nickname", Security.connected()).first();
            List<User> users = User.findAll();
            List<Relation> relations = Relation.findAll();
            int userCount = 0;
            int userCountPhoto = 0;
            int relationCount = 0;
            int relationCountReady = 0;

            userCount = users.size();
            relationCount = relations.size();
            for (User user1 : users) {
                if(!user1.pics.isEmpty()){
                    userCountPhoto += 1;
                }
            }
            for (Relation relation : relations) {
                if(relation.ready){
                    relationCountReady += 1;
                }
            }
            render(user, userCount,userCountPhoto,relationCount,relationCountReady);
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

    public static void likeUser(Long userID) {
        if (Security.isConnected()) {
            User user = User.find("nickname", Security.connected()).first();
            User likedUser = User.findById(userID);
            if (user.doLike(likedUser)) {
                user.save();
            }
            Relation relation = User.CheckRelation(likedUser, user);
            if (relation != null) {
                relation.save();
                likedUser.save();
                user.save();
            }
            index();
        }
    }

    public static void changeRelation(long relationID, String place, String time, String date) {
        if (Security.isConnected()) {
            User user = User.find("nickname", Security.connected()).first();
//            System.out.println(relationID +" | " + place + " | " + time + " | " + date);
            Relation relation = Relation.findById(relationID);
            relation.change(user.id, place, time, date);

            relation.save();
            schedule();
        }
    }

    public static void acceptRelation(long relationID) {
        if (Security.isConnected()) {
            User user = User.find("nickname", Security.connected()).first();
//            System.out.println(relationID +" | " + place + " | " + time + " | " + date);
            Relation relation = Relation.findById(relationID);
            relation.accept();
            relation.save();
            schedule();
        }
    }

    public static int getRelationCount() {
        if (Security.isConnected()) {
            User user = User.find("nickname", Security.connected()).first();
            return user.getRawRelations().size();
        }
        return 0;
    }


    public static void deleteRelation(long relationID) {
        System.out.println("cur del relation: " + relationID);
        boolean isReady = true;
        if (Security.isConnected()) {
            Relation relation = Relation.findById(relationID);
            System.out.println("Delete relation: " + relation.toString());
            isReady = relation.ready;
            User user1 = relation.user1;
            User user2 = relation.user2;
            user1.relations.remove(relation);
            user1.liked.remove(user2);
            user2.relations.remove(relation);
            user2.liked.remove(user1);
            user1.save();
            user2.save();
            relation.delete();
        }
        if(isReady){
            meetings();
        }else {
            schedule();
        }
    }
}
