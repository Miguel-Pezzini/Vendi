package com.vendi.photo.storage;

public interface PhotoStorage {
    String store(byte[] data, String contentType, String filename);

    byte[] load(String storageKey);

    void delete(String storageKey);
}
