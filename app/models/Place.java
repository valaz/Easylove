package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Place extends Model {
    public String name;

    @ManyToOne
    public Location location;


    @Override
    public String toString() {
        if(location != null) {
            return this.id + " : " + name + " | " + location.name;
        }else{
            return this.id + " : " + name + " | ";
        }
    }
}
