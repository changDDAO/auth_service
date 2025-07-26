package com.changddao.auth_service.service;

import com.changddao.auth_service.exception.FileUploadException;
import com.changddao.auth_service.exception.SearchFileException;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final MinioClient minioClient;

    @Value("${MINIO_BUCKET}")
    private String bucketName;

    public String uploadFile(MultipartFile file) {
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        try {
            // 버킷이 없으면 생성
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            return fileName;

        } catch (Exception e) {
            throw new FileUploadException("파일 업로드 실패");
        }
    }

    public String uploadFileToSubdirectory(MultipartFile file, String subDir) {
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        String objectName = subDir + "/" + fileName;
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("changhome")
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return objectName;

        } catch (Exception e) {
            throw new FileUploadException("파일 업로드 실패");
        }
    }

    public String getFileUrl(String fileName) {
        try {
            String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .method(Method.GET)
                            .expiry(60 * 60) // 1시간 유효
                            .build()
            );
            return url;
        } catch (Exception e) {
            throw new SearchFileException("URL에 따른 파일 가져오기 실패");
        }

    }


}
