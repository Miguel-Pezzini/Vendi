package com.vendi.photo.mapper;

import com.vendi.photo.dto.PhotoToCreateDTO;
import com.vendi.photo.model.Photo;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class PhotoMapper {

    public static byte[] decodeBase64ToBytes(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }

    public static Photo mapToPhoto(PhotoToCreateDTO createPhotoRequestDTO) {
        Photo photo = new Photo();
        photo.setFilename(createPhotoRequestDTO.filename());
        photo.setData(decodeBase64ToBytes(createPhotoRequestDTO.data()));
        photo.setContentType(createPhotoRequestDTO.contentType());
        photo.setIsMain(createPhotoRequestDTO.isMainPhoto());
        return photo;
    }

    public static List<Photo> mapToPhotos(List<PhotoToCreateDTO> createPhotoRequestDTOs) {
        return createPhotoRequestDTOs.stream()
                .map(PhotoMapper::mapToPhoto)
                .collect(Collectors.toList());
    }
}
