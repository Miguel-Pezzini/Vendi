package com.vendi.photo.dto;

import com.vendi.photo.model.Photo;

import java.util.UUID;

public record PhotoDTO(UUID id, String contentType, String filename, Boolean isMainPhoto) {
    public PhotoDTO(Photo photo) {
        this(photo.getId(), photo.getContentType(), photo.getFilename(), photo.getIsMain());
    }
}
