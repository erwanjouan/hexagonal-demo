package the.atomicity.hexagonal.adapter.out.persistence.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import the.atomicity.hexagonal.adapter.out.persistence.AbstractRepository;
import the.atomicity.hexagonal.domain.GenericMedia;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JsonRepository extends AbstractRepository {

    List<GenericMedia> medias = new ArrayList<>();

    @Override
    public void init(final String source, final boolean clear) {
    }

    @Override
    public BigInteger check(final GenericMedia media) {
        return null;
    }

    @Override
    public BigInteger save(final GenericMedia media) {
        this.medias.add(media);
        return media.customHashCode();
    }

    @Override
    public void delete(final String source, final String absolutePath) {

    }

    @Override
    public List<String[]> findDuplicate(final String source) {
        return null;
    }

    @Override
    public void postSave() {
        final ObjectMapper objectMapper = this.getObjectMapper();
        try {
            final String json = objectMapper.writeValueAsString(this.medias);
            final File file = new File("media.json");
            objectMapper.writeValue(file, this.medias);
            //System.out.println(json);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
