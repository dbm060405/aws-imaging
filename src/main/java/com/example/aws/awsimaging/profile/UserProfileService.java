package com.example.aws.awsimaging.profile;

import com.example.aws.awsimaging.bucket.BucketName;
import com.example.aws.awsimaging.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserProfileService {
    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(
            UserProfileDataAccessService userProfileDataAccessService,
            FileStore fileStore
    ) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    List<UserProfile> getUserProfiles() {
        return userProfileDataAccessService.getUserProfiles();
    }

    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        userProfileDataAccessService.getUserProfile(userProfileId).ifPresentOrElse(
                userProfile -> {
                    if (!file.isEmpty() &&
                            Arrays.asList(
                                    MediaType.IMAGE_JPEG_VALUE,
                                    MediaType.IMAGE_PNG_VALUE,
                                    MediaType.IMAGE_GIF_VALUE).contains(file.getContentType())
                    ) {
                        Map<String, String> metaData = extractMetadata(file);
                        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.bucketName, userProfile.getUserProfileId());
                        String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
                        try {
                            fileStore.save(
                                    path,
                                    fileName,
                                    Optional.of(metaData),
                                    file.getInputStream()
                            );
                            userProfile.setUserProfileImageLink(String.format("%s", fileName));
                        } catch (IOException e) {
                            throw new IllegalStateException(e);
                        }
                    } else {
                        throw new IllegalStateException(String.format("File is empty or file type is not %s, %s, or %s", MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE));
                    }
                }, () -> {
                    throw new IllegalStateException(String.format("User profile %s not found", userProfileId));
                }
        );
    }

    public byte[] downloadUserProfileImage(UUID userProfileID) {
        UserProfile user = userProfileDataAccessService.getUserProfile(userProfileID).orElseThrow(() -> {
            throw new IllegalStateException(String.format("user profile not found %s", userProfileID));
        });
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.bucketName, user.getUserProfileId());
        if(user.getUserProfileImageLink() == null || user.getUserProfileImageLink().isEmpty()){
            return new byte[0];
        }
        return fileStore.download(path, user.getUserProfileImageLink());
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metaData = new HashMap<>();
        metaData.put("contentType", file.getContentType());
        metaData.put("contentLength", String.valueOf(file.getSize()));
        return metaData;
    }
}
