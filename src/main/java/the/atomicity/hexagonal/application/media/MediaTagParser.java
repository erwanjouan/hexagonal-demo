package the.atomicity.hexagonal.application.media;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import the.atomicity.hexagonal.domain.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

@Service
public class MediaTagParser {

    protected final static Logger logger = LoggerFactory.getLogger(MediaTagParser.class);

    public GenericMedia parse(final String source, final Path file) {
        try {
            final BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
            if (this.isJPEG(file)) {
                logger.info("[JPG] {}", file);
                return JpgImpl.build(source, file, attrs);
            } else if (this.isCR2(file)) {
                logger.info("[CR2] {}", file);
                return Cr2Impl.build(source, file, attrs);
            } else if (this.isMov(file)) {
                logger.info("[MOV] {}", file);
                return MovImpl.build(source, file, attrs);
            } else if (this.isMp4(file)) {
                logger.info("[MP4] {}", file);
                return Mp4Impl.build(source, file, attrs);
            }
        } catch (final IOException e) {
            return null;
        }
        return null;
    }

    private boolean isMp4(final Path file) {
        boolean isMp4 = file.toString().endsWith("MP4");
        isMp4 |= file.toString().endsWith("mp4");
        return isMp4;
    }


    private boolean isJPEG(final Path file) {
        boolean isJPEG = file.toString().endsWith("jpg");
        isJPEG |= file.toString().endsWith("jpeg");
        isJPEG |= file.toString().endsWith("JPG");
        isJPEG |= file.toString().endsWith("JPEG");
        return isJPEG;
    }

    private boolean isCR2(final Path file) {
        boolean isCR2 = file.toString().endsWith("CR2");
        isCR2 |= file.toString().endsWith("cr2");
        return isCR2;
    }

    private boolean isMov(final Path file) {
        boolean isMov = file.toString().endsWith("MOV");
        isMov |= file.toString().endsWith("mov");
        return isMov;
    }

}
