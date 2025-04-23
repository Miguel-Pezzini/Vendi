package com.vendi.dto.photo;

import com.vendi.model.photo.Photo;

public record PhotoData(byte[] data) {
    public PhotoData(Photo photo) {
        this(photo.getData());
    }
}
