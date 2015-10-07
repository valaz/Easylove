package models;

import org.imgscalr.Scalr;
import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.awt.*;
import java.awt.image.BufferedImage;

@Entity
public class Photo extends Model {

    @Column(columnDefinition="LONGBLOB")
    public byte[] file;

    public String fileName;
    public String contentType;

    public static BufferedImage createThumbnail(BufferedImage img) {
        // Target width of 500x500 is used
//        img = Scalr.resize(img, 500,500);
        img = Scalr.resize(img,Scalr.Mode.FIT_TO_WIDTH,  100,50);

        return Scalr.pad(img, 2, Color.WHITE);
    }

}
