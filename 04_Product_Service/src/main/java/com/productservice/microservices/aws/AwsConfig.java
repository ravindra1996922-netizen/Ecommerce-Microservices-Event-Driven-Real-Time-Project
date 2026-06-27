package com.productservice.microservices.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfig {

	@Value("${aws.accessKeyId}")
	String accessKeyId;
	@Value("${aws.secretKey}")
	String secretkey;
	
	@Value("${aws.region}")
	String region;

	@Bean
	public  S3Client s3Client() {
		
	AwsCredentials awsCredentials = AwsBasicCredentials.create(accessKeyId, secretkey);
	
	return S3Client.builder().region(Region.of(region))
			.credentialsProvider(StaticCredentialsProvider.create(awsCredentials)).build();
	}

}
