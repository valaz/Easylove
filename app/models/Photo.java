package models;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.filters.Canvas;
import net.coobird.thumbnailator.geometry.Positions;
import org.imgscalr.Scalr;
import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Entity
public class Photo extends Model {

    @Column(columnDefinition="LONGBLOB")
    public byte[] file;

    public String fileName;
    public String contentType;

    public static BufferedImage createThumbnail(BufferedImage img) {
        // Target width of 500x500 is used
//        img = Scalr.resize(img, 500,500);
        BufferedImage img2 = Scalr.resize(img , Scalr.Method.AUTOMATIC, 150);

        return Scalr.pad(img2, 2, Color.WHITE);
    }

    public static File createView(File img, int size) {
        File outFile = new File("out.jpg");
        try {
            Thumbnails.of(img)
                    .size(size, size)
                    .addFilter(new Canvas(size,size,Positions.CENTER, Color.WHITE))
                    .toFile(outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outFile;
    }



}
