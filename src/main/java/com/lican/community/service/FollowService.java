package com.lican.community.service;

public interface FollowService {
    void follow(int userId, int entityType, int entityId);
    void unFollow(int userId, int entityType, int entityId);
    long findFolloweeCount(int userId, int entityType);
    long findFollowerCount(int entityType, int entityId);
    boolean hasFollowed(int userId, int entityType, int entityId);
}