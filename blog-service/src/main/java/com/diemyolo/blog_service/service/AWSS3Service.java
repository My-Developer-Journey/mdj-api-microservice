package com.diemyolo.blog_service.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface AWSS3Service {
    Map<String, String> uploadFile(MultipartFile file);

    void testS3Connection();

}