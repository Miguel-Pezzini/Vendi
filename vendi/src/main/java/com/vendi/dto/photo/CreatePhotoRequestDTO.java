package com.vendi.dto.photo;

import aj.org.objectweb.asm.commons.Remapper;
import jakarta.validation.constraints.NotNull;

public record CreatePhotoRequestDTO(
        @NotNull String data,
        @NotNull String contentType,
        @NotNull String filename
) {}
