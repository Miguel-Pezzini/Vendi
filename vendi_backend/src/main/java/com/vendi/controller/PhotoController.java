package com.vendi.controller;


import com.vendi.dto.photo.PhotoResponseDTO;
import com.vendi.dto.photo.PhotoWithDataDTO;
import com.vendi.dto.product.ProductResponseDTO;
import com.vendi.exceptions.ResourceNotFoundException;
import com.vendi.service.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("photo")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/{photoId}")
    public ResponseEntity<PhotoWithDataDTO> getPhotoById(@PathVariable UUID photoId) throws ResourceNotFoundException {
        PhotoWithDataDTO photo = photoService.getById(photoId);

        return ResponseEntity.status(HttpStatus.OK).body(photo);
    }
}
