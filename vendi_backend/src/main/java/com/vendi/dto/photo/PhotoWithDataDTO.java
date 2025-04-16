package com.vendi.dto.photo;

import com.vendi.model.photo.Photo;

import java.util.UUID;

public record PhotoWithDataDTO(UUID id, String contentType, String filename, byte[] data) {
    public PhotoWithDataDTO(Photo photo) {
        this(photo.getId(), photo.getContentType(), photo.getFilename(), photo.getData());
    }
}
