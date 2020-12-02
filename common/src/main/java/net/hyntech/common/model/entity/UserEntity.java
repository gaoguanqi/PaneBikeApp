package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

public class UserEntity extends BaseResponse<UserEntity> {
    private String accessToken;
    private String userId;
    private Long expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "accessToken='" + accessToken + '\'' +
                ", userId='" + userId + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}
