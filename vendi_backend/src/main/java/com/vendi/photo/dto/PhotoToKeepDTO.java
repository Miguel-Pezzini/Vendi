package com.vendi.photo.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PhotoToKeepDTO(
        UUID id,
        Boolean isMainPhoto
) implements IsMainPhoto {}
