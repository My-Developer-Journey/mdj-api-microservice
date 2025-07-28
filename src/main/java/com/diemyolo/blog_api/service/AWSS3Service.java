package com.diemyolo.blog_api.service;

import org.springframework.web.multipart.MultipartFile;

public interface AWSS3Service {
    String uploadFile(MultipartFile file);

    void testS3Connection();

}