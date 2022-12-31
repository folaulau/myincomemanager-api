package com.incomemanager.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile({"local"})
@Configuration
public class LocalAwsConfig {

    @Value("${aws.deploy.region:us-west-2}")
    private String targetRegion;

    private Regions getTargetRegion() {
        if (targetRegion == null) {
            targetRegion = "us-west-2";
        }
        return Regions.fromName(targetRegion);
    }

    @Bean
    public AWSCredentialsProvider amazonAWSCredentialsProvider() {
        return new ProfileCredentialsProvider("pooch");
    }

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard().withCredentials(amazonAWSCredentialsProvider()).withRegion(getTargetRegion()).build();
    }

    @Bean
    public AWSSecretsManager awsSecretsManager(AWSCredentialsProvider aWSCredentialsProvider) {
        String endpoint = "secretsmanager." + getTargetRegion().getName() + ".amazonaws.com";
        AwsClientBuilder.EndpointConfiguration config = new AwsClientBuilder.EndpointConfiguration(endpoint, getTargetRegion().getName());
        AWSSecretsManagerClientBuilder clientBuilder = AWSSecretsManagerClientBuilder.standard();
        clientBuilder.setEndpointConfiguration(config);
        clientBuilder.setCredentials(aWSCredentialsProvider);
        return clientBuilder.build();
    }

}