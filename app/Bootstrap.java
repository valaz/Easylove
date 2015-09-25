import models.Location;
import models.User;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

import java.util.List;


@OnApplicationStart
public class Bootstrap  extends Job {
    public void doJob() {
        // Check if the database is empty
        if(User.count() == 0) {
            System.out.println("init Users");
            Fixtures.loadModels("initial-data.yml");
        }
        if(Location.count() == 0){
            System.out.println("init Locations");
            Fixtures.loadModels("initial-locations.yml");

            List<Location> all = Location.findAll();
            for (Location location : all) {
                location.correctPlaces();
            }
        }
    }
}
