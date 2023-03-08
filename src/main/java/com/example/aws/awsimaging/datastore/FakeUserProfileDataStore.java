package com.example.aws.awsimaging.datastore;

import com.example.aws.awsimaging.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {
    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("e030ec02-b224-4df0-a56f-c658b9b4936e"), "helloThere", null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("fb417bd2-2d32-4eb7-9b5b-26e90cec753a"), "goodBye", null));
    }

    public List<UserProfile> getUserProfile() {
        return USER_PROFILES;
    }

    public Optional<UserProfile> getUserProfileByUserProfileId(UUID uid) {
        return USER_PROFILES
                .stream()
                .filter(userProfile -> uid.equals(userProfile.getUserProfileId()))
                .findFirst();
    }
}
