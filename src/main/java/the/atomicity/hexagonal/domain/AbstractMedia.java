package the.atomicity.hexagonal.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

public abstract class AbstractMedia implements GenericMedia {
    private BigInteger hashCode;
    protected final String path;
    protected final FileTime creationTime;
    protected final FileTime lastAccessTime;
    protected final FileTime lastModifiedTime;
    protected final BigInteger fileSize;
    protected Object metaData;
    private String source;

    protected static final Logger logger = LoggerFactory.getLogger(AbstractMedia.class);

    public AbstractMedia(final String source, final Path path, final BasicFileAttributes attributes) {
        this.path = path.toString();
        this.creationTime = attributes.creationTime();
        this.lastAccessTime = attributes.lastAccessTime();
        this.lastModifiedTime = attributes.lastModifiedTime();
        this.fileSize = BigInteger.valueOf(attributes.size());
        this.source = source;
    }

    public BigInteger getHashCode() {
        return this.hashCode;
    }

    public void setHashCode(final BigInteger hashCode) {
        this.hashCode = hashCode;
    }

    public String getPath() {
        return this.path;
    }

    public String getSource() {
        return this.source;
    }

    @Override
    public BigInteger getFileSize() {
        return this.fileSize;
    }

    @Override
    public Object getMetaData() {
        return this.metaData;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Override
    public FileTime getCreationTime() {
        return this.creationTime;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Override
    public FileTime getLastAccessTime() {
        return this.lastAccessTime;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Override
    public FileTime getLastModifiedTime() {
        return this.lastModifiedTime;
    }

    protected void setMetaData(final Object metaData) {
        this.metaData = metaData;
    }

    public abstract String getImageWidth();

    public abstract String getImageHeight();

}
