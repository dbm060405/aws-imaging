package com.example.aws.awsimaging.profile;

import com.example.aws.awsimaging.datastore.FakeUserProfileDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserProfileDataAccessService {
    private final FakeUserProfileDataStore fakeUserProfileDataStore;

    @Autowired
    public UserProfileDataAccessService(FakeUserProfileDataStore fakeUserProfileDataStore) {
        this.fakeUserProfileDataStore = fakeUserProfileDataStore;
    }

    List<UserProfile> getUserProfiles() {
        return fakeUserProfileDataStore.getUserProfile();
    }
    Optional<UserProfile> getUserProfile(UUID userProfileId) { return fakeUserProfileDataStore.getUserProfileByUserProfileId(userProfileId); }
}
