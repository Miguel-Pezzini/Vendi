package com.vendi.photo.service;

import com.vendi.photo.dto.CreatePhotoDTO;
import com.vendi.photo.dto.PhotoDataDTO;
import com.vendi.photo.mapper.PhotoMapper;
import com.vendi.photo.model.Photo;
import com.vendi.photo.repository.PhotoRepository;
import com.vendi.photo.storage.PhotoStorage;
import com.vendi.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final PhotoStorage photoStorage;

    @Transactional
    public List<Photo> createPhotos(List<CreatePhotoDTO> createPhotoRequestDTOs) {
        return createPhotoRequestDTOs.stream()
                .map(this::createPhoto)
                .toList();
    }

    @Transactional(readOnly = true)
    public PhotoDataDTO getById(UUID photoId) throws ResourceNotFoundException {
        Photo photo = this.photoRepository.findById(photoId)
                .orElseThrow(() -> new ResourceNotFoundException("Photo not found."));

        return PhotoDataDTO.from(photo.getContentType(), photoStorage.load(photo.getStorageKey()));
    }

    @Transactional
    public void deleteById(UUID photoId) throws ResourceNotFoundException {
        Photo photo = this.photoRepository.findById(photoId)
                .orElseThrow(() -> new ResourceNotFoundException("Photo not found."));
        photoStorage.delete(photo.getStorageKey());
        this.photoRepository.delete(photo);
    }

    public void deleteStoredFiles(List<Photo> photos) {
        if (photos == null) {
            return;
        }
        photos.forEach(photo -> photoStorage.delete(photo.getStorageKey()));
    }

    private Photo createPhoto(CreatePhotoDTO createPhotoRequestDTO) {
        Photo photo = PhotoMapper.mapToPhoto(createPhotoRequestDTO);
        byte[] data = PhotoMapper.decodeBase64ToBytes(createPhotoRequestDTO.data());
        photo.setStorageKey(photoStorage.store(data, createPhotoRequestDTO.contentType(), createPhotoRequestDTO.filename()));
        return photo;
    }
}
