package com.changddao.auth_service.service;

import com.changddao.auth_service.exception.FileUploadException;
import com.changddao.auth_service.exception.SearchFileException;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final MinioClient minioClient;

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.bucket}")
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
            return String.format("%s/%s/%s", minioUrl, bucketName, fileName);
        } catch (Exception e) {
            throw new SearchFileException("URL에 따른 파일 가져오기 실패");
        }

    }


}
