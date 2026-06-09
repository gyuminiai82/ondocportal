package kr.ondoc.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystemStorage {

    private final Path path;

    public FileSystemStorage(Path path) {
        this.path = path;
    }

    public String store(byte[] bytes, String name) {
        try {
            Files.copy(new ByteArrayInputStream(bytes), this.path.resolve(name));
            return this.path.resolve(name).toAbsolutePath().toString();
        } catch (IOException e) {
            
        }
		return null;
    }
}