package com.vendi.service;

import com.vendi.dto.photo.CreatePhotoRequestDTO;
import com.vendi.dto.photo.PhotoResponseDTO;
import com.vendi.dto.photo.PhotoWithDataDTO;
import com.vendi.exceptions.ResourceNotFoundException;
import com.vendi.model.photo.Photo;
import com.vendi.model.product.Product;
import com.vendi.repository.PhotoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PhotoService {

    @Autowired
    PhotoRepository photoRepository;

    public byte[] decodeBase64ToBytes(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }

    @Transactional
    public void createPhotos(List<CreatePhotoRequestDTO> createPhotoRequestDTO, Product product) {
        createPhotoRequestDTO.forEach(photoDTO -> {
            Photo photo = new Photo();
            photo.setFilename(photoDTO.filename());
            photo.setContentType(photoDTO.contentType());
            photo.setData(this.decodeBase64ToBytes(photoDTO.data()));
            photo.setProduct(product);
            photoRepository.save(photo);
        });
    }
    @Transactional
    public Photo createMainPhoto(CreatePhotoRequestDTO createPhotoRequestDTO, Product product) {
        Photo photo = new Photo();
        photo.setFilename(createPhotoRequestDTO.filename());
        photo.setContentType(createPhotoRequestDTO.contentType());
        photo.setData(this.decodeBase64ToBytes(createPhotoRequestDTO.data()));
        photo.setProduct(product);
        photo = photoRepository.save(photo);
        return photo;
    }

    @Transactional(readOnly = true)
    public PhotoWithDataDTO getById(UUID photoId) {
        Photo photo = this.photoRepository.findById(photoId).orElseThrow();

        return new PhotoWithDataDTO(photo);
    }
}
