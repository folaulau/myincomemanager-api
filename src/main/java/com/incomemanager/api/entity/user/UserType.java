package com.incomemanager.api.entity.user;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum UserType {

    user,

    engineer,

    admin;

    public static List<String> getAllAuths() {
        return Arrays.asList(UserType.values()).stream().map(auth -> auth.name()).collect(Collectors.toList());
    }

}
