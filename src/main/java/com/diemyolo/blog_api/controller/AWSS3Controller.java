package com.diemyolo.blog_api.controller;

import com.diemyolo.blog_api.model.common.ApiResponse;
import com.diemyolo.blog_api.service.AWSS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequestMapping("/api/awss3")
@RestController
public class AWSS3Controller {
    @Autowired
    private AWSS3Service awsS3Service;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<Map<String, String>>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, String> result = awsS3Service.uploadFile(file);
        return ResponseEntity.ok(ApiResponse.success("File Uploaded Successfully", result));
    }

    @GetMapping("/test")
    public String testAws3() {
        awsS3Service.testS3Connection();
        return "Test thành công!";
    }
}
