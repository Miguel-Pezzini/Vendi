package com.vendi.photo.service;

import com.vendi.photo.dto.PhotoData;
import com.vendi.photo.dto.PhotoToCreateDTO;
import com.vendi.photo.mapper.PhotoMapper;
import com.vendi.photo.model.Photo;
import com.vendi.photo.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PhotoService {

    @Autowired
    PhotoRepository photoRepository;

    public List<Photo> createPhotos(List<PhotoToCreateDTO> createPhotoRequestDTOs) {
        return PhotoMapper.mapToPhotos(createPhotoRequestDTOs);
    }

    @Transactional(readOnly = true)
    public PhotoData getById(UUID photoId) {
        Photo photo = this.photoRepository.findById(photoId).orElseThrow();

        return new PhotoData(photo);
    }

    public void deleteById(UUID photoId) {
        this.photoRepository.deleteById(photoId);
    }
}
