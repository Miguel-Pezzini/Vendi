package com.vendi.photo.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.UUID;

@Component
public class LocalFilePhotoStorage implements PhotoStorage {

    private final Path rootDirectory;

    public LocalFilePhotoStorage(@Value("${app.photo-storage.path:${user.home}/.vendi/photos}") String rootDirectory) {
        this.rootDirectory = Path.of(rootDirectory).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.rootDirectory);
        } catch (IOException e) {
            throw new IllegalStateException("Could not initialize photo storage directory.", e);
        }
    }

    @Override
    public String store(byte[] data, String contentType, String filename) {
        String storageKey = UUID.randomUUID() + "_" + sanitize(filename);
        Path target = resolve(storageKey);
        try {
            Files.write(target, data);
            return storageKey;
        } catch (IOException e) {
            throw new IllegalStateException("Could not store photo.", e);
        }
    }

    @Override
    public byte[] load(String storageKey) {
        Path target = resolve(storageKey);
        if (!Files.exists(target)) {
            throw new IllegalStateException("Photo file not found.");
        }
        try {
            return Files.readAllBytes(target);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read photo.", e);
        }
    }

    @Override
    public void delete(String storageKey) {
        if (storageKey == null || storageKey.isBlank()) {
            return;
        }
        try {
            Files.deleteIfExists(resolve(storageKey));
        } catch (IOException e) {
            throw new IllegalStateException("Could not delete photo.", e);
        }
    }

    private Path resolve(String storageKey) {
        Path target = rootDirectory.resolve(sanitize(storageKey)).normalize();
        if (!target.startsWith(rootDirectory)) {
            throw new IllegalArgumentException("Invalid photo storage key.");
        }
        return target;
    }

    private String sanitize(String filename) {
        String value = filename == null ? "photo" : filename;
        String sanitized = value.replaceAll("[^a-zA-Z0-9._-]", "_");
        return sanitized.isBlank() ? "photo" : sanitized.toLowerCase(Locale.ROOT);
    }
}
