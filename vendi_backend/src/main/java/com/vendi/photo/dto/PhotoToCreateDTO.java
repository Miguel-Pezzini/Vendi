package com.vendi.photo.dto;

import jakarta.validation.constraints.NotNull;

public record PhotoToCreateDTO (
        Boolean isMainPhoto,
        @NotNull String data,
        @NotNull String contentType,
        @NotNull String filename) implements IsMainPhoto {
}
