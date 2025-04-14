package com.vendi.dto.photo;

import com.vendi.model.photo.Photo;

import java.util.UUID;

public record PhotoResponseDTO(UUID id, String contentType, String filename) {
    public PhotoResponseDTO(Photo photo) {
        this(photo.getId(), photo.getContentType(), photo.getFilename());
    }
}
