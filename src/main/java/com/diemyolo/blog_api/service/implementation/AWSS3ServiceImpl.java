package com.diemyolo.blog_api.service.implementation;

import com.diemyolo.blog_api.exception.CustomException;
import com.diemyolo.blog_api.service.AWSS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class AWSS3ServiceImpl implements AWSS3Service {
    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket.name}")
    private  String bucketName;


    @Override
    public String uploadFile(MultipartFile file){
        try {
            String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // Tự xác định MIME type từ tên file
            String mimeType = Files.probeContentType(Paths.get(file.getOriginalFilename()));

            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(uniqueFileName)
                    .contentType(mimeType)
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return "https://" + bucketName + ".s3.amazonaws.com/" + uniqueFileName;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public byte[] downloadFile(String key) {
        ResponseBytes<GetObjectResponse> objectAsBytes = s3Client.getObjectAsBytes(GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build());
        return objectAsBytes.asByteArray();
    }

    @Override
    public void testS3Connection() {
        try {
            s3Client.listBuckets().buckets().forEach(bucket -> {
                System.out.println("Bucket: " + bucket.name());
            });
        } catch (Exception e) {
            System.err.println("S3 Connection Error: " + e.getMessage());
            throw new RuntimeException("Cannot connect to S3", e);
        }
    }
}
