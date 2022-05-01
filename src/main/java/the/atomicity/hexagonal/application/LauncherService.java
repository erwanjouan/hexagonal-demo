package the.atomicity.hexagonal.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import the.atomicity.hexagonal.domain.Mode;
import the.atomicity.hexagonal.adapter.out.persistence.PersistService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static the.atomicity.hexagonal.domain.Mode.isJson;

@Service
public class LauncherService {
    @Value("${media.source.name}")
    private  String source;
    @Value("${db.mode}")
    private  Mode mode;
    @Value("${media.folder}")
    private  List<Path> startDirs;
    @Value("${db.truncate}")
    private Boolean clear;
    @Autowired
    private  PersistService persistService;
    @Autowired
    private CrawlerService crawlerService;


    private static final Logger logger = LoggerFactory.getLogger(LauncherService.class);

    @PostConstruct
    public void init() {
        for (final Path startDir : this.startDirs) {
            logger.info(String.format("Processing folder %s", startDir.toAbsolutePath()));
            try {
                this.crawlerService.load(this.source, startDir, clear, this.mode);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.crawlerService.postLoad();
    }

}
