package models;


import play.db.jpa.Blob;
import play.db.jpa.Model;

import javax.persistence.Entity;

@Entity
public class Picture extends Model {

//    @Column(columnDefinition="BLOB")
    public Blob image;

//    @Basic(fetch = FetchType.LAZY)
//    @Lob
//    public byte[] photo;

}
