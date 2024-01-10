package com.medical.api.mapper;

import com.medical.api.domain.AppUser;
import com.medical.api.entities.User;

public class UserMapper {
    public static AppUser toAppUser(User user) {
        return AppUser.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
