package the.atomicity.hexagonal.application.media;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import the.atomicity.hexagonal.domain.AbstractVideo;
import the.atomicity.hexagonal.domain.GenericMedia;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.stream.StreamSupport;

public class MovImpl extends AbstractVideo {

    public MovImpl(final String source, final Path path, final BasicFileAttributes attributes) {
        super(source, path, attributes);
        this.setMetaData(new Metadata());
    }

    public static GenericMedia build(final String source, final Path path, final BasicFileAttributes attrs) {
        final MovImpl mov = new MovImpl(source, path, attrs);
        try {
            final Metadata metadata = ImageMetadataReader.readMetadata(path.toFile());
            mov.setMetaData(metadata);
            mov.setHashCode(mov.customHashCode());
        } catch (final Exception e) {
            logger.info("Error reading {} {}", path, e.getMessage());
        }
        return mov;
    }

    private String getTagValue(final String directoryName, final String tagName) {
        final Iterable<Directory> sourceIterator = ((Metadata) this.metaData).getDirectories();
        return StreamSupport.stream(sourceIterator.spliterator(), false)
                .filter(d -> directoryName.equals(d.getName()))
                .map(t -> t.getTags())
                .flatMap(Collection::stream)
                .filter(t -> tagName.equals(t.getTagName()))
                .map(t -> t.getDescription())
                .findFirst()
                .orElse("");
    }

    @Override
    public String getImageWidth() {
        return this.getTagValue("QuickTime Video", "Width");
    }

    @Override
    public String getDuration() {
        return this.getTagValue("QuickTime", "Duration");
    }

    @Override
    public String getCodec() {
        return this.getTagValue("QuickTime Video", "Compression Type");
    }

    @Override
    public String getFrameRate() {
        return this.getTagValue("QuickTime Video", "Frame Rate");
    }

    @Override
    public String getImageHeight() {
        return this.getTagValue("QuickTime Video", "Height");
    }

}
