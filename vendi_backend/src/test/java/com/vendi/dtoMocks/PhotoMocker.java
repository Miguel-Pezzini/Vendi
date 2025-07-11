package com.vendi.dtoMocks;

import com.vendi.photo.dto.PhotoToCreateDTO;
import com.vendi.photo.dto.PhotoToKeepDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PhotoMocker {
    static public PhotoToCreateDTO getBasePhotoToCreateDTO(Boolean isMainPhoto, String data, String contentType, String filename) {
        return new PhotoToCreateDTO(isMainPhoto, data, contentType, filename);
    }
    static public PhotoToKeepDTO getBasePhotoToKeepDTO(UUID id, Boolean isMainPhoto) {
        return new PhotoToKeepDTO(id, isMainPhoto);
    }

    static public List<PhotoToCreateDTO> getPhotosToCreateDTO() {
        List<PhotoToCreateDTO> photos = IntStream.range(0, 3)
                .mapToObj(i -> getBasePhotoToCreateDTO(false, "randomData", "image/png", "not_main_photo"))
                .collect(Collectors.toList());
        photos.add(getBasePhotoToCreateDTO(true, "randomData", "image/png", "main_photo"));
        return photos;
    }

//    static public List<PhotoToKeepDTO> getPhotosToKeepDTO(List<UUID> photosIds) {
//        List<PhotoToCreateDTO> photos = IntStream.range(0, 3)
//                .mapToObj(i -> getBasePhotoToCreateDTO(false, "randomData", "image/png", "not_main_photo"))
//                .collect(Collectors.toList());
//        photos.add(getBasePhotoToCreateDTO(true, "randomData", "image/png", "main_photo"));
//        return photos;
//    }
}
