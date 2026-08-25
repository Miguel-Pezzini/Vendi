package com.vendi.unit.photo;

import com.vendi.photo.storage.LocalFilePhotoStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocalFilePhotoStorageTest {

    @TempDir
    Path tempDir;

    @Test
    void storesLoadsAndDeletesBytesOutsideTheDatabase() {
        LocalFilePhotoStorage storage = new LocalFilePhotoStorage(tempDir.toString());
        byte[] payload = "photo-bytes".getBytes(StandardCharsets.UTF_8);

        String storageKey = storage.store(payload, "image/png", "main.png");
        byte[] loaded = storage.load(storageKey);

        assertTrue(storageKey.contains("main.png") || storageKey.endsWith("main.png"));
        assertArrayEquals(payload, loaded);

        storage.delete(storageKey);
        assertFalse(tempDir.resolve(storageKey).toFile().exists());
    }
}
