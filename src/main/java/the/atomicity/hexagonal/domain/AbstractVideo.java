package the.atomicity.hexagonal.domain;

import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public abstract class AbstractVideo extends AbstractMedia {

    public AbstractVideo(String source, Path path, BasicFileAttributes attributes) {
        super(source, path, attributes);
    }

    @Override
    public MediaType getType() {
        return MediaType.VIDEO;
    }

    public abstract String getDuration();

    public abstract String getCodec();

    public abstract String getFrameRate();

    public BigInteger customHashCode() {
        long result = getFileSize() != null ? getFileSize().hashCode() : 0;
        result = 31 * result + (getImageHeight() != null ? getImageHeight().hashCode() : 0);
        result = 31 * result + (getImageWidth() != null ? getImageWidth().hashCode() : 0);
        result = 31 * result + (getDuration() != null ? getDuration().hashCode() : 0);
        result = 31 * result + (getCodec() != null ? getCodec().hashCode() : 0);
        result = 31 * result + (getFrameRate() != null ? getFrameRate().hashCode() : 0);
        return BigInteger.valueOf(result);
    }

}
