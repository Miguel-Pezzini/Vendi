package com.vendi.photo.mapper;

import com.vendi.photo.dto.CreatePhotoDTO;
import com.vendi.photo.model.Photo;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class PhotoMapper {

    public static byte[] decodeBase64ToBytes(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }

    public static Photo mapToPhoto(CreatePhotoDTO createPhotoRequestDTO) {
        Photo photo = new Photo();
        photo.setFilename(createPhotoRequestDTO.filename());
        photo.setData(decodeBase64ToBytes(createPhotoRequestDTO.data()));
        photo.setContentType(createPhotoRequestDTO.contentType());
        photo.setIsMain(createPhotoRequestDTO.isMainPhoto());
        return photo;
    }

    public static List<Photo> mapToPhotos(List<CreatePhotoDTO> createPhotoRequestDTOs) {
        return createPhotoRequestDTOs.stream()
                .map(PhotoMapper::mapToPhoto)
                .collect(Collectors.toList());
    }
}
