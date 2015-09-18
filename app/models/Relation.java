package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
public class Relation extends Model {

    @ManyToOne
    public User user1;

    @ManyToOne
    public User user2;

    public String date;
    public String place;
    public String time;
    public String status1;
    public String status2;
    public int lastSend;

    public Relation(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;

        date = null;
        place = null;
        time = null;
        status1 = "ZERO";
        status2 = "ZERO";
        lastSend = 0;
    }

    @Override
    public String toString() {
        return this.id + ": [" + user1.toString() + "] ~ [" + user2.toString() + "]"     ;
    }

    @Override
    public boolean equals(Object o) {
        Relation relation = (Relation)o;
        if(user1.id == relation.user1.id && user2.id == (relation.user2.id)){
            return true;
        }
        if(user2.id == relation.user1.id && user1.id == (relation.user2.id)){
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + user1.hashCode();
        result = 31 * result + user2.hashCode();
        return result;
    }

    public User getAnother(Long userID){
        if(user1.id == userID){
            return user2;
        }else{
            if(user2.id == userID){
                return user1;
            }
            return null;
        }
    }
    public int getCurNum(Long userID){
        if(userID == user1.id){
            return 1;
        }else{
            return 2;
        }
    }
    public int getAnotherNum(Long userID){
        return 3 - getCurNum(userID);
    }
    public String getStringDate(){
        if(date == null){
            return null;
        }else {
            return date.split(" ")[0];
        }
    }

    public void change(Long actionID, String place, String time, String date) {
        this.place = place;
        this.time = time;
        this.date = date.split(" ")[0];

        int curNum = getCurNum(actionID);
        if(curNum == 1 ){
            status1 = "WAIT";
            status2 = "ANSWER";
        }else{
            status2 = "WAIT";
            status1 = "ANSWER";
        }
    }

    public String inputType(Long userID){
        int curNum = getCurNum(userID);
        if(curNum == 1){
            if(status1.equals("WAIT")){
                return "disabled";
            }else{
                return "";
            }
        }else{
            if(status2.equals("WAIT")){
                return "disabled";
            }else{
                return "";
            }
        }
    }

    public String btnMessage(Long userID){
        int curNum = getCurNum(userID);
        if(curNum == 1){
            if(status1.equals("WAIT")){
                return "Ждите ответа";
            }else{
                if(status1.equals("ANSWER") ) {
                    return "Отправить ответ";
                }else{
                    return "Предложить условия";
                }
            }
        }else{
            if(status2.equals("WAIT")){
                return "Ждите ответа";
            }else{
                if(status2.equals("ANSWER") ) {
                    return "Отправить ответ";
                }else{
                    return "Предложить условия";
                }
            }
        }
    }
    public String btnClass(Long userID){
        int curNum = getCurNum(userID);
        if(curNum == 1){
            if(status1.equals("WAIT")){
                return "btn-info";
            }else{
                if(status1.equals("ANSWER") ) {
                    return "btn-warning";
                }else{
                    return "btn-default";
                }
            }
        }else{
            if(status2.equals("WAIT")){
                return "btn-info";
            }else{
                if(status2.equals("ANSWER") ) {
                    return "btn-warning";
                }else{
                    return "btn-default";
                }
            }
        }
    }

    public String btnAcceptType(Long userID){
        int curNum = getCurNum(userID);
        if(curNum == 1){
            if(status1.equals("ANSWER")){
                return "submit";
            }else{
                return "hidden";
            }
        }else{
            if(status2.equals("ANSWER")){
                return "submit";
            }else{
                return "hidden";
            }
        }
    }
}
