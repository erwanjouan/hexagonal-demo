package the.atomicity.hexagonal.domain;

import java.math.BigInteger;
import java.nio.file.attribute.FileTime;

public interface GenericMedia {

    BigInteger customHashCode();

    String getSource();

    String getPath();

    FileTime getCreationTime();

    FileTime getLastAccessTime();

    FileTime getLastModifiedTime();

    BigInteger getFileSize();

    Object getMetaData();

    MediaType getType();

}
