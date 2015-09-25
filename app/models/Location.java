package models;


import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Location extends Model {
    public String name;

    @OneToMany
    public List<Place> places;

    public void correctPlaces(){
        for (Place place : places) {
            place.location = this;
            place.save();
        }
    }

    @Override
    public String toString() {
        return this.id + ": " + name;
    }
}
