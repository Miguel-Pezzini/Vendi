package com.vendi.unit.photo;

import com.vendi.photo.dto.CreatePhotoDTO;
import com.vendi.photo.mapper.PhotoMapper;
import com.vendi.photo.model.Photo;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PhotoMapperTest {

    @Test
    void decodeBase64ToBytesReturnsOriginalBytes() {
        byte[] expected = "image-bytes".getBytes(StandardCharsets.UTF_8);
        String base64 = Base64.getEncoder().encodeToString(expected);

        byte[] decoded = PhotoMapper.decodeBase64ToBytes(base64);

        assertArrayEquals(expected, decoded);
    }

    @Test
    void mapToPhotoCopiesCreatePhotoFields() {
        String base64 = Base64.getEncoder().encodeToString("main-photo".getBytes(StandardCharsets.UTF_8));
        CreatePhotoDTO createPhotoDTO = new CreatePhotoDTO(true, base64, "image/png", "main.png");

        Photo photo = PhotoMapper.mapToPhoto(createPhotoDTO);

        assertEquals("main.png", photo.getFilename());
        assertEquals("image/png", photo.getContentType());
        assertEquals(true, photo.getIsMain());
        assertArrayEquals("main-photo".getBytes(StandardCharsets.UTF_8), photo.getData());
    }

    @Test
    void mapToPhotosMapsEveryPhotoInTheCollection() {
        String base64 = Base64.getEncoder().encodeToString("gallery-photo".getBytes(StandardCharsets.UTF_8));
        List<CreatePhotoDTO> photos = List.of(
                new CreatePhotoDTO(true, base64, "image/png", "main.png"),
                new CreatePhotoDTO(false, base64, "image/png", "gallery.png")
        );

        List<Photo> mappedPhotos = PhotoMapper.mapToPhotos(photos);

        assertEquals(2, mappedPhotos.size());
        assertEquals("main.png", mappedPhotos.get(0).getFilename());
        assertEquals("gallery.png", mappedPhotos.get(1).getFilename());
    }
}
