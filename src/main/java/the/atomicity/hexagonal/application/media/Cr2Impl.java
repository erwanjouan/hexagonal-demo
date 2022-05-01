package the.atomicity.hexagonal.application.media;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Descriptor;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDescriptor;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import the.atomicity.hexagonal.domain.AbstractPicture;
import the.atomicity.hexagonal.domain.GenericMedia;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class Cr2Impl extends AbstractPicture {

    public Cr2Impl(String source, Path path, BasicFileAttributes attributes) {
        super(source, path, attributes);
        this.setMetaData(new Metadata());
    }

    public static GenericMedia build(String source, Path file, BasicFileAttributes attrs) {
        Cr2Impl cr2Picture = new Cr2Impl(source, file, attrs);
        try (InputStream in = Files.newInputStream(file)) {
            Metadata metadata = ImageMetadataReader.readMetadata(in);
            cr2Picture.setMetaData(metadata);
        } catch (Exception e) {
            logger.info("Error reading {} {}", file, e.getMessage());
        }
        return cr2Picture;
    }

    public String getISOSpeedRatings() {
        ExifSubIFDDirectory directory = getMetadata().getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        ExifSubIFDDescriptor descriptor = new ExifSubIFDDescriptor(directory);
        return descriptor.getIsoEquivalentDescription();
    }

    public String getModel() {
        ExifIFD0Directory directory = getMetadata().getFirstDirectoryOfType(ExifIFD0Directory.class);
        ExifIFD0Descriptor descriptor = new ExifIFD0Descriptor(directory);
        return descriptor.getDescription(ExifIFD0Directory.TAG_MODEL);
    }

    private Metadata getMetadata() {
        return (Metadata) this.metaData;
    }

    public String getImageWidth() {
        ExifSubIFDDirectory directory = getMetadata().getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        ExifSubIFDDescriptor descriptor = new ExifSubIFDDescriptor(directory);
        return descriptor.getExifImageWidthDescription();
    }

    public String getImageHeight() {
        ExifSubIFDDirectory directory = getMetadata().getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        ExifSubIFDDescriptor descriptor = new ExifSubIFDDescriptor(directory);
        return descriptor.getExifImageHeightDescription();
    }

    public String getExposureTime() {
        ExifSubIFDDirectory directory = getMetadata().getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        ExifSubIFDDescriptor descriptor = new ExifSubIFDDescriptor(directory);
        return descriptor.getExposureTimeDescription();
    }

    public String getFNumber() {
        ExifSubIFDDirectory directory = getMetadata().getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        ExifSubIFDDescriptor descriptor = new ExifSubIFDDescriptor(directory);
        return descriptor.getFNumberDescription();
    }

    public String getFocalLength() {
        ExifSubIFDDirectory directory = getMetadata().getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        ExifSubIFDDescriptor descriptor = new ExifSubIFDDescriptor(directory);
        return descriptor.getFocalLengthDescription();
    }
}
