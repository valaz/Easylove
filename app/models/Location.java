package models;


import play.db.jpa.Model;

import javax.persistence.Entity;

@Entity
public class Location extends Model {
    public String name;
    public String[] places;

    @Override
    public String toString() {
        return this.id + ": " + name;
    }
}
