package the.atomicity.hexagonal.adapter.out.persistence.postgres;

import org.postgresql.util.PGobject;
import the.atomicity.hexagonal.domain.AbstractVideo;
import the.atomicity.hexagonal.domain.GenericMedia;
import the.atomicity.hexagonal.domain.MediaType;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;

import static the.atomicity.hexagonal.domain.MediaType.VIDEO;

public class JdbcVideosRepository extends JdbcMediaRepository {

    public BigInteger save(final GenericMedia media) {
        final AbstractVideo video = (AbstractVideo) media;
        final String logLine = String.format("inserting %s [%d] in %s", video.getPath().toString(), video.customHashCode(), Thread.currentThread().getName());
        System.out.println(logLine);
        final String INSERT_SQL_QUERY = "insert into " + video.getSource() + "." + media.getType().getTable() +
                "(id, path, file_size, width, height, duration, codec, frame_rate, info) " +
                " VALUES(?,?,?,?,?,?,?,?,?)";
        try (final Connection con2 = HikariCPDataSource.getConnection();
             final PreparedStatement pst2 = con2.prepareStatement(INSERT_SQL_QUERY)) {
            pst2.setObject(1, video.customHashCode(), Types.BIGINT);
            pst2.setString(2, video.getPath().toString());
            pst2.setObject(3, video.getFileSize(), Types.BIGINT);
            pst2.setString(4, video.getImageWidth());
            pst2.setString(5, video.getImageHeight());
            pst2.setString(6, video.getDuration());
            pst2.setString(7, video.getCodec());
            pst2.setString(8, video.getFrameRate());
            final PGobject jsonObject = this.getJson(video);
            pst2.setObject(9, jsonObject);
            final int affectedRows = pst2.executeUpdate();
            return video.customHashCode();
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getCreationSql(final String source) {
        return "CREATE TABLE IF NOT EXISTS " + source + "." + VIDEO.getTable() + " \n" +
                "(\n" +
                "    id bigint NOT NULL,\n" +
                "    path varchar NOT NULL,\n" +
                "    file_size bigint,\n" +
                "    width varchar,\n" +
                "    height varchar,\n" +
                "    duration varchar,\n" +
                "    codec varchar,\n" +
                "    frame_rate varchar,\n" +
                "    info json,\n" +
                "    CONSTRAINT my_videos_pkey PRIMARY KEY (id, path)\n" +
                ");\n" +
                "ALTER TABLE " + source + ".my_videos OWNER to postgres;";
    }

    @Override
    public void init(final String source, final boolean clear) {
        this.create(source);
        if (clear) {
            this.truncate(source, VIDEO);
        }
    }

    MediaType getMediaType() {
        return VIDEO;
    }
}
