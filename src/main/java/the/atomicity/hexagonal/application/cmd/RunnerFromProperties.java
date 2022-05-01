package the.atomicity.hexagonal.application.cmd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import the.atomicity.hexagonal.domain.Mode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RunnerFromProperties {

    private static final Logger logger = LoggerFactory.getLogger(RunnerFromProperties.class);

    public void launch(){
        try(InputStream input = this.getClass().getClassLoader().getResourceAsStream("application.properties")){
            Properties properties = new Properties();
            if(input==null){
                logger.error("Unable to find application.properties");
                return;
            }
            properties.load(input);
            final String source = properties.getProperty("media.source.name");
            final String[] startingDir = {properties.getProperty("media.folder")};
            final Mode mode = Mode.fromString(properties.getProperty("db.mode"));
            boolean truncate = Boolean.parseBoolean(properties.getProperty("db.truncate"));

            //LauncherService launcherService = new LauncherService(source, startingDir, mode);
            //launcherService.load(truncate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
