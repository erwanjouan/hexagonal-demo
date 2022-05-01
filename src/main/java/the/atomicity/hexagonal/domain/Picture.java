package the.atomicity.hexagonal.domain;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

public interface Picture {

    String getISOSpeedRatings();

    String getModel();

    String getImageWidth();

    String getImageHeight();

    String getExposureTime();

    String getFNumber();

    String getFocalLength();

    Path getPath();

    long getFileSize();

    FileTime getCreationTime();

    FileTime getLastAccessTime();

    FileTime getLastModifiedTime();

}

