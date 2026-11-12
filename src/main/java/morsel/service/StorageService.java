package morsel.service;

import java.io.File;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.util.function.Supplier;

/**
 * Manages a single auto-managed instance of a
 * JSON file with entries of one type. Exposes
 * methods to read and write to the file.
 * @param <T> StorageService underlying type.
 */
public class StorageService<T> {
    private final File linkedFile;
    private final ObjectWriter linkedFileWriter;
    private final ObjectReader linkedFileReader;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public StorageService(String path, TypeReference<T> typeRef, Supplier<T> defaultValue) throws IOException {
        linkedFile = new File(path);

        if (!linkedFile.exists()) {
            File parent = linkedFile.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            linkedFile.createNewFile();
        }
        ObjectMapper mapper = new ObjectMapper();
        linkedFileWriter = mapper.writerWithDefaultPrettyPrinter();
        linkedFileReader = mapper.readerFor(typeRef);
        if (defaultValue != null && linkedFile.length() == 0) {
            T output = defaultValue.get();
            linkedFileWriter.writeValue(linkedFile, output);
        }
    }

    /**
     * Writes the value to the given file.
     * @param value The value to be written to the file.
     * @throws IOException Thrown when write fails due to an IO error.
     */
    public void writeTo(T value) throws IOException {
        linkedFileWriter.writeValue(linkedFile, value);
    }

    /**
     * Reads from a given file and parses it to a value.
     * @return The value parsed from the reader.
     * @throws IOException Thrown when read fails due to an IO error.
     */
    public T readFrom() throws IOException {
        return linkedFileReader.readValue(linkedFile);
    }
}
