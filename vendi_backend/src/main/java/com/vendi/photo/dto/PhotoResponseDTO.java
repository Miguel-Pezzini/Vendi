package com.vendi.photo.dto;

import com.vendi.photo.model.Photo;

import java.util.UUID;

public record PhotoResponseDTO(UUID id, String contentType, String filename) {
    public PhotoResponseDTO(Photo photo) {
        this(photo.getId(), photo.getContentType(), photo.getFilename());
    }
}
