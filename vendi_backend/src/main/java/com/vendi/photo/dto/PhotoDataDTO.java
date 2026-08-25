package com.vendi.photo.dto;

import java.util.Base64;

public record PhotoDataDTO(String dataURI) {
    public static PhotoDataDTO from(String contentType, byte[] data) {
        return new PhotoDataDTO("data:" + contentType + ";base64," + Base64.getEncoder().encodeToString(data));
    }
}
