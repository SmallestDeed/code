package com.sandu.user.service;

public interface UserBehaviorService {
    boolean cacheCount(String type);

    boolean syncToDB();
}
