package com.vendi.service;

import com.vendi.dto.photo.CreatePhotoRequestDTO;
import com.vendi.model.photo.Photo;
import com.vendi.model.product.Product;
import com.vendi.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoService {

    @Autowired
    PhotoRepository photoRepository;

    public byte[] decodeBase64ToBytes(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }

    public void createPhotos(List<CreatePhotoRequestDTO> createPhotoRequestDTO, Product product) {
        createPhotoRequestDTO.forEach(photoDTO -> {
            byte[] imageData = this.decodeBase64ToBytes(photoDTO.data());

            Photo photo = new Photo();
            photo.setFilename(photoDTO.filename());
            photo.setContentType(photoDTO.contentType());
            photo.setData(imageData);
            photo.setProduct(product);
            photoRepository.save(photo);
        });
    }
}
