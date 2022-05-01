package the.atomicity.hexagonal.adapter.out.persistence.postgres;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.postgresql.util.PGobject;
import the.atomicity.hexagonal.adapter.out.persistence.AbstractRepository;
import the.atomicity.hexagonal.domain.GenericMedia;
import the.atomicity.hexagonal.domain.MediaType;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class JdbcMediaRepository extends AbstractRepository {

    public abstract void init(final String source, final boolean clear);

    protected PGobject getJson(final GenericMedia genericMedia) {
        final PGobject jsonObject = new PGobject();
        try {
            jsonObject.setType("json");
            final String jsonString = getObjectMapper().writeValueAsString(genericMedia);
            jsonObject.setValue(jsonString);
            return jsonObject;
        } catch (final JsonProcessingException | SQLException e) {
            e.printStackTrace();
            return jsonObject;
        }
    }

    void executeUpdate(final String sql) {
        try (final Connection con = HikariCPDataSource.getConnection();
             final PreparedStatement pst = con.prepareStatement(sql);) {
            final int affectedRows = pst.executeUpdate();
            System.out.println(affectedRows + " rows affected");
        } catch (final SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    public void create(final String source) {
        this.executeUpdate(this.getCreationSql(source));
    }

    public void delete(final String source, final String absolutePath) {
        this.executeUpdate("DELETE FROM " + source + "." + this.getMediaType().getTable() + " WHERE path='" + absolutePath + "'");
    }

    public void truncate(final String source, final MediaType mediaType) {
        this.executeUpdate("TRUNCATE " + source + "." + mediaType.getTable());
    }

    abstract String getCreationSql(String source);

    abstract MediaType getMediaType();

    public List<String[]> findDuplicate(final String source) {
        final List<String[]> toReturn = new ArrayList<>();
        final String FIND_DUPLICATE_SQL_QUERY = "SELECT max(path) as path1, min(path) as path2, count(id) from " + source +
                "." + this.getMediaType().getTable() + " group by id having count(id) > 1";
        try (final Connection con = HikariCPDataSource.getConnection();
             final PreparedStatement pst = con.prepareStatement(FIND_DUPLICATE_SQL_QUERY);
             final ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                final String[] duplicates = new String[2];
                duplicates[0] = rs.getString(1);
                duplicates[1] = rs.getString(2);
                toReturn.add(duplicates);
            }
        } catch (final SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        return toReturn;
    }

    public BigInteger check(final GenericMedia media) {
        final String logLine = String.format("checking presence of %s [%d] in Thread %s", media.getPath().toString(), media.customHashCode(), Thread.currentThread().getName());
        System.out.println(logLine);
        final String SELECT_SQL_QUERY = "SELECT id FROM " + media.getSource() + "." + media.getType().getTable() + " WHERE id=" + media.customHashCode() + " and path='" + media.getPath() + "'";
        try (final Connection con = HikariCPDataSource.getConnection();
             final PreparedStatement pst = con.prepareStatement(SELECT_SQL_QUERY);
             final ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                final BigInteger bigInteger = rs.getObject(1, BigInteger.class);
                return bigInteger;
            }
        } catch (final SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public void postSave(){
        // no op on JDBC
    }
}
