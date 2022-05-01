package the.atomicity.hexagonal.domain;

import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public abstract class AbstractPicture extends AbstractMedia {

    public AbstractPicture(String source, Path path, BasicFileAttributes attributes) {
        super(source, path, attributes);
    }

    @Override
    public MediaType getType() {
        return MediaType.PICTURE;
    }

    public abstract String getISOSpeedRatings();

    public abstract String getModel();

    public abstract String getExposureTime();

    public abstract String getFNumber();

    public abstract String getFocalLength();

    public BigInteger customHashCode() {
        long result = (getFileSize() != null ? getFileSize().hashCode() : 0);
        result = 31 * result + (getImageHeight() != null ? getImageHeight().hashCode() : 0);
        result = 31 * result + (getImageWidth() != null ? getImageWidth().hashCode() : 0);
        result = 31 * result + (getModel() != null ? getModel().hashCode() : 0);
        result = 31 * result + (getExposureTime() != null ? getExposureTime().hashCode() : 0);
        result = 31 * result + (getISOSpeedRatings() != null ? getISOSpeedRatings().hashCode() : 0);
        result = 31 * result + (getFNumber() != null ? getFNumber().hashCode() : 0);
        result = 31 * result + (getFocalLength() != null ? getFocalLength().hashCode() : 0);
        return BigInteger.valueOf(result);
    }


}