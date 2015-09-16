package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.sql.Time;
import java.util.Date;


@Entity
public class Relation extends Model {

    @ManyToOne
    public User user1;

    @ManyToOne
    public User user2;

    Date date;
    String place;
    String time;
    String status1;
    String status2;
    int lastSend;

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
        return "[" + user1.toString() + "] ~ [" + user2.toString() + "]"     ;
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
}
