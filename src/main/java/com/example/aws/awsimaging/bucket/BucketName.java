package com.example.aws.awsimaging.bucket;

/**
 * This is the name of the folder to drop images in AWS {@link com.amazonaws.services.s3.AmazonS3}
 */
public enum BucketName {
    PROFILE_IMAGE("image-upload-132");

    public final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

}
