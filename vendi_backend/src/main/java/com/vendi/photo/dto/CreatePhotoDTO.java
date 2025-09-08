package com.vendi.photo.dto;

import jakarta.validation.constraints.NotNull;

public record CreatePhotoDTO(
        Boolean isMainPhoto,
        @NotNull(message = "The data of the photo cannot be empty") String data,
        @NotNull(message = "The contentType of the photo cannot be empty") String contentType,
        @NotNull(message = "The filename of the photo cannot be empty") String filename) implements IsMainPhoto {
}
