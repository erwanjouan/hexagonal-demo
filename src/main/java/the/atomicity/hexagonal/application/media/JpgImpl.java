package the.atomicity.hexagonal.application.media;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.jpeg.JpegParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;
import the.atomicity.hexagonal.domain.AbstractPicture;
import the.atomicity.hexagonal.domain.GenericMedia;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class JpgImpl extends AbstractPicture {

    public JpgImpl(final String source, final Path path, final BasicFileAttributes attributes) {
        super(source, path, attributes);
        this.setMetaData(new Metadata());
    }

    public static GenericMedia build(final String source, final Path path, final BasicFileAttributes attrs) {
        final JpgImpl jpg = new JpgImpl(source, path, attrs);
        try {
            jpg.parseMetaData(path.toString());
            jpg.setHashCode(jpg.customHashCode());
        } catch (final Exception e) {
            logger.info("Error reading {} {}", path, e.getMessage());
        }
        return jpg;
    }

    private void parseMetaData(final String pathToFile) throws IOException, TikaException, SAXException {
        final BodyContentHandler handler = new BodyContentHandler();
        try (final FileInputStream inputstream = new FileInputStream(new File(pathToFile))) {
            final ParseContext pcontext = new ParseContext();
            final JpegParser jpegParser = new JpegParser();
            jpegParser.parse(inputstream, handler, (Metadata) this.metaData, pcontext);
        }
    }

    public String getISOSpeedRatings() {
        return ((Metadata) this.metaData).get("ISO Speed Ratings");
    }

    public String getModel() {
        return ((Metadata) this.metaData).get("Model");
    }

    public String getImageWidth() {
        return ((Metadata) this.metaData).get("Image Width");
    }

    public String getImageHeight() {
        return ((Metadata) this.metaData).get("Image Height");
    }

    public String getExposureTime() {
        return ((Metadata) this.metaData).get("Exposure Time");
    }

    public String getFNumber() {
        return ((Metadata) this.metaData).get("F-Number");
    }

    public String getFocalLength() {
        return ((Metadata) this.metaData).get("Focal Length");
    }

}
