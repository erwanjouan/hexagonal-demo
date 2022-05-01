package the.atomicity.hexagonal.application.media;

import com.drew.imaging.mp4.Mp4MetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import the.atomicity.hexagonal.domain.AbstractVideo;
import the.atomicity.hexagonal.domain.GenericMedia;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.stream.StreamSupport;

public class Mp4Impl extends AbstractVideo {

    public Mp4Impl(final String source, final Path path, final BasicFileAttributes attributes) {
        super(source, path, attributes);
        this.setMetaData(new Metadata());
    }

    public static GenericMedia build(final String source, final Path path, final BasicFileAttributes attrs) {
        final Mp4Impl mp4 = new Mp4Impl(source, path, attrs);
        try {
            final Metadata metadata = Mp4MetadataReader.readMetadata(path.toFile());
            mp4.setMetaData(metadata);
            mp4.setHashCode(mp4.customHashCode());
        } catch (final Exception e) {
            logger.info("Error reading {} {}", path, e.getMessage());
        }
        return mp4;
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
        return this.getTagValue("MP4 Video", "Width");
    }

    @Override
    public String getImageHeight() {
        return this.getTagValue("MP4 Video", "Height");
    }

    @Override
    public String getDuration() {
        return this.getTagValue("MP4", "Duration");
    }

    @Override
    public String getCodec() {
        return this.getTagValue("MP4 Video", "Compression Type");
    }

    @Override
    public String getFrameRate() {
        return this.getTagValue("MP4 Video", "Frame Rate");
    }

}
