package com.vendi.photo.dto;

import jakarta.validation.constraints.NotNull;

public record CreatePhotoRequestDTO(
        Boolean isMainPhoto,
        @NotNull String data,
        @NotNull String contentType,
        @NotNull String filename
) {}
