/*
 * PEARSON PROPRIETARY AND CONFIDENTIAL INFORMATION SUBJECT TO NDA 
 * Copyright (c) 2018-2021 Pearson Education, Inc.
 * All Rights Reserved. 
 * 
 * NOTICE: All information contained herein is, and remains the property of 
 * Pearson Education, Inc. The intellectual and technical concepts contained 
 * herein are proprietary to Pearson Education, Inc. and may be covered by U.S. 
 * and Foreign Patents, patent applications, and are protected by trade secret 
 * or copyright law. Dissemination of this information, reproduction of this  
 * material, and copying or distribution of this software is strictly forbidden   
 * unless prior written permission is obtained from Pearson Education, Inc.
 */
package com.dockerDemo.dockerDemo;


import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;

/**
 * The class DynamoDBConfig.
 */
@Configuration
public class DynamoDBConfig {

//  @Value("${amazon.aws.region}")
//  private Region region;
//
//  @Value("${amazon.aws.accesskey}")
//  private String accessKeyId;
//
//  @Value("${amazon.aws.secretkey}")
//  private String secretAccessKey;
//
//  @Value("${amazon.dynamodb.endpoint}")
//  private String endpointOverride;

  @Bean
  public DynamoDbEnhancedClient getDynamoDBClient() throws Exception {
    try {
      return DynamoDbEnhancedClient.builder().dynamoDbClient(createDynamoDbClient()).build();
    } catch (URISyntaxException exception) {
      throw new Exception("Error While establishing connection with DB!!");
    }
  }

  private DynamoDbClient createDynamoDbClient() throws URISyntaxException {
System.out.println("haha-region-");

    DynamoDbClientBuilder clientBuilder = DynamoDbClient.builder().region(Region.US_EAST_1);
    clientBuilder = configureDynamoDbCredentials(clientBuilder);
    clientBuilder = configureDynamoDbEndpoint(clientBuilder);

    return clientBuilder.build();
  }

  private DynamoDbClientBuilder configureDynamoDbEndpoint(DynamoDbClientBuilder clientBuilder)
      throws URISyntaxException {
	 
	  String endpointOverride = "http://dynamodb-local:8000";
	  System.out.println("haha-endpointOverride-"+endpointOverride);
    if (endpointOverride != null) {
      return clientBuilder.endpointOverride(new URI(endpointOverride));
    }
    return clientBuilder;
  }

  private DynamoDbClientBuilder configureDynamoDbCredentials(DynamoDbClientBuilder clientBuilder) {
	  String secretAccessKey = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY";
	  String accessKeyId = "AKIAIOSFODNN7EXAMPLE";
	  //amazon.aws.accesskey=AKIAIOSFODNN7EXAMPLE
	  //amazon.aws.secretkey=wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
	  System.out.println("haha-accessKeyId-"+accessKeyId);
	  System.out.println("haha-secretAccessKey-"+secretAccessKey);
	  AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
    return clientBuilder.credentialsProvider(StaticCredentialsProvider.create(awsCreds));
  }
}
