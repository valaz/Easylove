package models;


import org.joda.time.DateTime;
import play.db.jpa.Model;

import javax.persistence.Entity;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
public class Timerange extends Model implements Comparable {
    public Date day;

    public Timerange(Date day) {
        System.out.println("TIMERANGE CONSTRUCTOR");
        System.out.println("===================================================================");
        this.day = day;
    }

    public Timerange(String day) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date start2 = new Date();
        try {
            start2 = format.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.day =start2;
        System.out.println("===================================================================");
        System.out.println((new DateTime()).toString());
    }

    @Override
    public boolean equals(Object o) {
        return  day.equals(((Timerange)o).day);
    }

    @Override
    public int hashCode() {
        int result =  day.hashCode();
        return result;
    }

    @Override
    public int compareTo(Object o) {
        Timerange timerange = (Timerange)o;
        return day.compareTo(timerange.day);
    }

    @Override
    public String toString() {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String freeDay = formatter.format(day);
        return freeDay;
    }
}
