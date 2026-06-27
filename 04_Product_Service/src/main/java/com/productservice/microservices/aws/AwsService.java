package com.productservice.microservices.aws;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
@RequiredArgsConstructor
public class AwsService {

	private final S3Client s3Client;

	@Value("${aws.s3.bucketName}")
	String bucketName;

	@Value("${aws.region}")
	String region;

	public String saveFileInS3Bucket(MultipartFile file)
			throws S3Exception, AwsServiceException, SdkClientException, IOException {

		String fileName = file.getOriginalFilename();
		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 6);

		String updatedfileName = random + fileName;

		PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(updatedfileName)
				.contentType(file.getContentType()).build();

		PutObjectResponse putObject = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

		String imageUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + updatedfileName;

		return imageUrl;
	}

	public void deleteFileFromS3Bucket(String imageUrl) {

		String key = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

		DeleteObjectRequest request = DeleteObjectRequest.builder().bucket(bucketName).key("images/" + key).build();

		s3Client.deleteObject(request);
	}

}
