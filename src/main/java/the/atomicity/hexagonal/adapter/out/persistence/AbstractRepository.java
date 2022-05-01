package the.atomicity.hexagonal.adapter.out.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.tika.metadata.Metadata;
import the.atomicity.hexagonal.utils.CustomFileTimeSerializer;
import the.atomicity.hexagonal.utils.CustomJpgMetaDataSerializer;

import java.nio.file.attribute.FileTime;

public abstract class AbstractRepository implements Repository {

    private final ObjectMapper objectMapper;

    protected AbstractRepository() {
        objectMapper = buildObjectMapper();
    }

    public ObjectMapper buildObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        final SimpleModule module = new SimpleModule();
        module.addSerializer(FileTime.class, new CustomFileTimeSerializer());
        module.addSerializer(Metadata.class, new CustomJpgMetaDataSerializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }

    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }
}
