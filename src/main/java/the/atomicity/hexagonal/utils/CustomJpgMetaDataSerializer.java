package the.atomicity.hexagonal.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.tika.metadata.Metadata;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomJpgMetaDataSerializer extends StdSerializer<Metadata> {
    public CustomJpgMetaDataSerializer() {
        this(null);
    }

    public CustomJpgMetaDataSerializer(Class<Metadata> t) {
        super(t);
    }

    @Override
    public void serialize(Metadata metadata, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Map<String, String> backingMap = new HashMap<>();
        for (String propertyName:metadata.names()){
            backingMap.put(propertyName, metadata.get(propertyName));
        }
        jsonGenerator.writeObject(backingMap);
    }
}
