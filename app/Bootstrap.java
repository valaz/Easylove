import models.Location;
import models.User;
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

import java.util.List;


@OnApplicationStart
public class Bootstrap  extends Job {
    public void doJob() {
        // Check if the database is empty
        if(Location.count() == 0){
            Logger.info("init Locations");
            Fixtures.loadModels("initial-locations.yml");

            List<Location> all = Location.findAll();
            for (Location location : all) {
                location.correctPlaces();
            }
        }
        if(User.count() == 0) {
            Logger.info("init Users");
            Fixtures.loadModels("initial-data.yml");
        }
    }
}
