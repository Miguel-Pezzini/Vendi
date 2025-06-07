package com.vendi.photo.dto;

import com.vendi.photo.model.Photo;

public record PhotoData(byte[] data) {
    public PhotoData(Photo photo) {
        this(photo.getData());
    }
}
