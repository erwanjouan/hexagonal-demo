package the.atomicity.hexagonal.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.nio.file.attribute.FileTime;

public class CustomFileTimeSerializer extends StdSerializer<FileTime> {
    public CustomFileTimeSerializer() {
        this(null);
    }

    public CustomFileTimeSerializer(Class<FileTime> t) {
        super(t);
    }

    @Override
    public void serialize(FileTime fileTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(fileTime.toString());
    }
}
