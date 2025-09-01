package com.vendi.photo.dto;

import com.vendi.photo.model.Photo;

import java.util.Base64;

public record PhotoDataDTO(String dataURI) {
    public PhotoDataDTO(Photo photo) {
        this("data:" + photo.getContentType() + ";base64," + Base64.getEncoder().encodeToString(photo.getData()));
    }
}
