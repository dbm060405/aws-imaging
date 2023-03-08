package com.example.aws.awsimaging.profile;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserProfile {
    private UUID userProfileId;
    private String userName;
    private String userProfileImageLink;

    public UserProfile(UUID userProfileId, String userName, String userProfileImageLink) {
        this.userProfileId = userProfileId;
        this.userName = userName;
        this.userProfileImageLink = userProfileImageLink;
    }

    public UUID getUserProfileId() {
        return userProfileId;
    }
    public String getUserName() {
        return userName;
    }
    public String getUserProfileImageLink() { return userProfileImageLink; }

    public void setUserProfileId(UUID userProfileId) {
        this.userProfileId = userProfileId;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserProfileImageLink(String userProfileImageLink) { this.userProfileImageLink = userProfileImageLink; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(userProfileId, that.userProfileId)
                && Objects.equals(userName, that.userName)
                && Objects.equals(userProfileImageLink, that.userProfileImageLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userProfileId, userName, userProfileImageLink);
    }
}
