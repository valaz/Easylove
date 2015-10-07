package models;


import play.db.jpa.Model;

import javax.persistence.Entity;

@Entity
public class Warning extends Model {
    public String tittle;
    public String text;


    @Override
    public String toString() {
        if( tittle == null){
            return this.id.toString();
        }else{
            return this.id.toString()+ " : " + tittle;
        }
    }
}
