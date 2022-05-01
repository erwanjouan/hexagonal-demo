package the.atomicity.hexagonal.adapter.out.persistence.postgres;

import org.postgresql.util.PGobject;
import the.atomicity.hexagonal.domain.AbstractPicture;
import the.atomicity.hexagonal.domain.GenericMedia;
import the.atomicity.hexagonal.domain.MediaType;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;

import static the.atomicity.hexagonal.domain.MediaType.PICTURE;

public class JdbcPicturesRepository extends JdbcMediaRepository {

    public BigInteger save(final GenericMedia media) {
        final AbstractPicture picture = (AbstractPicture) media;
        final String logLine = String.format("inserting %s [%d] in %s", picture.getPath().toString(), picture.customHashCode(), Thread.currentThread().getName());
        System.out.println(logLine);
        final String INSERT_SQL_QUERY = "insert into " + picture.getSource() + "." + media.getType().getTable() +
                " (id, path, file_size, width, height, iso_speed_ratings, model, exposure_time, f_number, focal_length, info) " +
                " VALUES(?,?,?,?,?,?,?,?,?,?,to_json(?::json))";
        try (final Connection con2 = HikariCPDataSource.getConnection();
             final PreparedStatement pst2 = con2.prepareStatement(INSERT_SQL_QUERY)) {
            pst2.setObject(1, picture.customHashCode(), Types.BIGINT);
            pst2.setString(2, picture.getPath().toString());
            pst2.setObject(3, picture.getFileSize(), Types.BIGINT);
            pst2.setString(4, picture.getImageWidth());
            pst2.setString(5, picture.getImageHeight());
            pst2.setString(6, picture.getISOSpeedRatings());
            pst2.setString(7, picture.getModel());
            pst2.setString(8, picture.getExposureTime());
            pst2.setString(9, picture.getFNumber());
            pst2.setString(10, picture.getFocalLength());
            final PGobject jsonObject = this.getJson(picture);
            pst2.setObject(11, jsonObject);
            final int affectedRows = pst2.executeUpdate();
            return picture.customHashCode();
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void init(final String source, final boolean clear) {
        this.create(source);
        if (clear) {
            this.truncate(source, PICTURE);
        }
    }

    public String getCreationSql(final String source) {
        return "CREATE SCHEMA IF NOT EXISTS " + source + ";\n" +
                "CREATE TABLE IF NOT EXISTS " + source + ".my_pictures\n" +
                "(\n" +
                "    id bigint NOT NULL,\n" +
                "    path varchar NOT NULL,\n" +
                "    file_size bigint,\n" +
                "    width varchar,\n" +
                "    height varchar,\n" +
                "    iso_speed_ratings varchar,\n" +
                "    model varchar,\n" +
                "    exposure_time varchar,\n" +
                "    f_number varchar,\n" +
                "    focal_length varchar,\n" +
                "    info json,\n" +
                "    CONSTRAINT my_pictures_pkey PRIMARY KEY (id, path)\n" +
                ");\n" +
                "ALTER TABLE " + source + ".my_pictures OWNER to postgres;";
    }

    MediaType getMediaType() {
        return PICTURE;
    }
}
