package com.vendi.photo.controller;


import com.vendi.photo.dto.PhotoData;
import com.vendi.shared.exception.ResourceNotFoundException;
import com.vendi.photo.service.PhotoService;
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
    public ResponseEntity<PhotoData> getPhotoById(@PathVariable UUID photoId) throws ResourceNotFoundException {
        PhotoData photo = photoService.getById(photoId);

        return ResponseEntity.status(HttpStatus.OK).body(photo);
    }
}
